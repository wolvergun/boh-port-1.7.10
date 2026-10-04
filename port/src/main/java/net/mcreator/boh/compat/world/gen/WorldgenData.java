package net.mcreator.boh.compat.world.gen;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.util.ResourceLocation;

/** Cached reads of data/NS/worldgen/KIND/PATH.json and other data files. */
public final class WorldgenData {

    private static final Map<String, JsonObject> CACHE = new HashMap<>();

    private WorldgenData() {}

    public static synchronized JsonObject get(String kind, ResourceLocation id) {
        String key = kind + "|" + id;
        if (CACHE.containsKey(key)) return CACHE.get(key);
        JsonObject o = read("/data/" + id.getResourceDomain() + "/" + kind + "/" + id.getResourcePath() + ".json");
        CACHE.put(key, o);
        return o;
    }

    public static JsonObject read(String path) {
        try (InputStream in = WorldgenData.class.getResourceAsStream(path)) {
            return in == null ? null : new JsonParser().parse(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
        } catch (Exception e) {
            return null;
        }
    }

    /** int or {"type":"uniform","value":{min_inclusive,max_inclusive}} / {"type":"uniform","min_inclusive"...} / constant */
    public static int intProvider(JsonElement e, java.util.Random r, int def) {
        if (e == null) return def;
        if (e.isJsonPrimitive()) return e.getAsInt();
        JsonObject o = e.getAsJsonObject();
        JsonObject v = o.has("value") && o.get("value").isJsonObject() ? o.getAsJsonObject("value") : o;
        if (o.has("value") && o.get("value").isJsonPrimitive()) return o.get("value").getAsInt();
        if (v.has("min_inclusive") && v.has("max_inclusive")) {
            int lo = yValue(v.get("min_inclusive")), hi = yValue(v.get("max_inclusive"));
            return hi <= lo ? lo : lo + r.nextInt(hi - lo + 1);
        }
        return def;
    }

    /** VerticalAnchor {"absolute":n} / {"above_bottom":n} / {"below_top":n} as a 1.20 y, or a plain int */
    public static int yValue(JsonElement e) {
        if (e.isJsonPrimitive()) return e.getAsInt();
        JsonObject o = e.getAsJsonObject();
        if (o.has("absolute")) return o.get("absolute").getAsInt();
        if (o.has("above_bottom")) return -64 + o.get("above_bottom").getAsInt();
        if (o.has("below_top")) return 320 - o.get("below_top").getAsInt();
        return 0;
    }
}
