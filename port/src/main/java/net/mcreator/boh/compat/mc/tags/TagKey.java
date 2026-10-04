package net.mcreator.boh.compat.mc.tags;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.minecraft.util.ResourceLocation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/** 1.20 TagKey whose members are read from the original data/<ns>/tags/<kind>/<path>.json files. */
public final class TagKey<T> {

    private static final Map<String, Set<String>> CACHE = new HashMap<>();

    private final String kind;
    private final ResourceLocation location;

    private TagKey(String kind, ResourceLocation location) {
        this.kind = kind;
        this.location = location;
    }

    public static <T> TagKey<T> create(ResourceKey<?> registry, ResourceLocation location) {
        return new TagKey<>(kindDir(registry.location().getResourcePath()), location);
    }

    public static <T> TagKey<T> of(String kind, ResourceLocation location) {
        return new TagKey<>(kind, location);
    }

    private static String kindDir(String registryPath) {
        switch (registryPath) {
            case "block":
                return "blocks";
            case "item":
                return "items";
            case "entity_type":
                return "entity_types";
            case "fluid":
                return "fluids";
            default:
                return registryPath;
        }
    }

    public ResourceLocation location() {
        return location;
    }

    public String kind() {
        return kind;
    }

    /** Member ids ("ns:path"), with nested "#tag" references expanded. */
    public Set<String> members() {
        return load(kind, location.getResourceDomain() + ":" + location.getResourcePath(), new HashSet<>());
    }

    public boolean contains(ResourceLocation id) {
        return id != null && members().contains(id.getResourceDomain() + ":" + id.getResourcePath());
    }

    public boolean contains(String id) {
        return members().contains(id.contains(":") ? id : "minecraft:" + id);
    }

    private static synchronized Set<String> load(String kind, String id, Set<String> visiting) {
        String cacheKey = kind + "|" + id;
        Set<String> cached = CACHE.get(cacheKey);
        if (cached != null) return cached;
        Set<String> out = new HashSet<>();
        if (!visiting.add(cacheKey)) return out;
        String ns = id.substring(0, id.indexOf(':')), path = id.substring(id.indexOf(':') + 1);
        String res = "/data/" + ns + "/tags/" + kind + "/" + path + ".json";
        try (InputStream in = TagKey.class.getResourceAsStream(res)) {
            if (in != null) {
                JsonObject obj = new JsonParser().parse(new InputStreamReader(in, StandardCharsets.UTF_8))
                    .getAsJsonObject();
                for (JsonElement e : obj.getAsJsonArray("values")) {
                    String v = e.isJsonObject() ? e.getAsJsonObject()
                        .get("id")
                        .getAsString() : e.getAsString();
                    if (v.startsWith("#")) out.addAll(load(kind, normalize(v.substring(1)), visiting));
                    else out.add(normalize(v));
                }
            }
        } catch (Exception ignored) {}
        CACHE.put(cacheKey, out);
        return out;
    }

    private static String normalize(String id) {
        return id.contains(":") ? id : "minecraft:" + id;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TagKey && ((TagKey<?>) o).kind.equals(kind) && ((TagKey<?>) o).location.equals(location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kind, location);
    }
}
