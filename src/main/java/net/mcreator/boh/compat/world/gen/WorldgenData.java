package net.mcreator.boh.compat.world.gen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import net.minecraft.util.ResourceLocation;

public final class WorldgenData {
    private static final Map<String, JsonObject> CACHE = new HashMap<>();

    private WorldgenData() {
    }

    public static synchronized JsonObject get(String kind, ResourceLocation id) {
        String key = kind + "|" + id;
        if (CACHE.containsKey(key)) {
            return CACHE.get(key);
        } else {
            JsonObject o = read("/data/" + id.getResourceDomain() + "/" + kind + "/" + id.getResourcePath() + ".json");
            CACHE.put(key, o);
            return o;
        }
    }

    public static JsonObject read(String path) {
        try {
            JsonObject var2;
            try (InputStream in = WorldgenData.class.getResourceAsStream(path)) {
                var2 = in == null ? null : new JsonParser().parse(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
            }

            return var2;
        } catch (Exception var6) {
            return null;
        }
    }

    public static int intProvider(JsonElement e, Random r, int def) {
        if (e == null) {
            return def;
        } else if (e.isJsonPrimitive()) {
            return e.getAsInt();
        } else {
            JsonObject o = e.getAsJsonObject();
            JsonObject v = o.has("value") && o.get("value").isJsonObject() ? o.getAsJsonObject("value") : o;
            if (o.has("value") && o.get("value").isJsonPrimitive()) {
                return o.get("value").getAsInt();
            } else if (v.has("min_inclusive") && v.has("max_inclusive")) {
                int lo = yValue(v.get("min_inclusive"));
                int hi = yValue(v.get("max_inclusive"));
                return hi <= lo ? lo : lo + r.nextInt(hi - lo + 1);
            } else {
                return def;
            }
        }
    }

    public static int yValue(JsonElement e) {
        if (e.isJsonPrimitive()) {
            return e.getAsInt();
        } else {
            JsonObject o = e.getAsJsonObject();
            if (o.has("absolute")) {
                return o.get("absolute").getAsInt();
            } else if (o.has("above_bottom")) {
                return -64 + o.get("above_bottom").getAsInt();
            } else {
                return o.has("below_top") ? 320 - o.get("below_top").getAsInt() : 0;
            }
        }
    }
}
