import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

import com.google.gson.*;

/**
 * Converts the mod's 1.20 lang (en_us.json) and sounds.json to 1.7.10 formats.
 * args: originalAssetsDir outAssetsDir
 */
public class ConvertAssets {

    public static void main(String[] a) throws Exception {
        Path in = Paths.get(a[0]), out = Paths.get(a[1]);
        lang(in.resolve("lang/en_us.json"), out.resolve("lang/en_US.lang"));
        sounds(in.resolve("sounds.json"), out.resolve("sounds.json"));
    }

    static void lang(Path src, Path dst) throws Exception {
        JsonObject o = JsonParser.parseString(new String(Files.readAllBytes(src), StandardCharsets.UTF_8)).getAsJsonObject();
        Map<String, String> outMap = new LinkedHashMap<>();
        for (Map.Entry<String, JsonElement> e : o.entrySet()) {
            String k = e.getKey(), v = e.getValue().getAsString().replace("\n", "\\n");
            outMap.put(k, v);
            if (k.startsWith("item.boh.") && k.indexOf('.', 9) < 0) outMap.put(k + ".name", v);
            else if (k.startsWith("block.boh.") && k.indexOf('.', 10) < 0) outMap.put("tile.boh." + k.substring(10) + ".name", v);
            else if (k.startsWith("entity.boh.") && k.indexOf('.', 11) < 0) outMap.put(k + ".name", v);
            else if (k.startsWith("enchantment.boh.")) outMap.put(k, v);
            else if (k.startsWith("item_group.boh.")) outMap.put("itemGroup." + k.substring(15), v);
            else if (k.startsWith("advancements.") && k.endsWith(".title")) {
                String n = k.substring(13, k.length() - 6);
                outMap.put("achievement.boh." + n, v);
            } else if (k.startsWith("advancements.") && k.endsWith(".descr")) {
                String n = k.substring(13, k.length() - 6);
                outMap.put("achievement.boh." + n + ".desc", v);
            } else if (k.startsWith("painting.boh.") && k.endsWith(".title")) {
                outMap.put("art." + k.substring(13, k.length() - 6), v);
            }
        }
        String[][] extra = { { "effect.kurolib.bleeding", "Bleeding" }, { "effect.kurolib.radiation", "Radiation" },
            { "effect.kurolib.aggression", "Aggression" }, { "effect.kurolib.paranoia", "Paranoia" }, { "effect.kurolib.sleep", "Drowsiness" },
            { "effect.kurolib.flashbanged", "Flashbanged" }, { "death.attack.boh.bleeding", "%1$s bled out" },
            { "death.attack.boh.radiation", "%1$s died of radiation sickness" }, { "itemGroup.boh", "Box of Horrors" } };
        for (String[] x : extra) outMap.putIfAbsent(x[0], x[1]);
        StringBuilder sb = new StringBuilder("# Box of Horrors (converted from en_us.json)\n");
        for (Map.Entry<String, String> e : outMap.entrySet()) sb.append(e.getKey()).append('=').append(e.getValue()).append('\n');
        Files.createDirectories(dst.getParent());
        Files.write(dst, sb.toString().getBytes(StandardCharsets.UTF_8));
        System.out.println("lang: " + outMap.size() + " keys");
    }

    static void sounds(Path src, Path dst) throws Exception {
        JsonObject o = JsonParser.parseString(new String(Files.readAllBytes(src), StandardCharsets.UTF_8)).getAsJsonObject();
        JsonObject res = new JsonObject();
        for (Map.Entry<String, JsonElement> e : o.entrySet()) {
            JsonObject ev = e.getValue().getAsJsonObject();
            JsonObject n = new JsonObject();
            n.addProperty("category", category(e.getKey()));
            JsonArray sounds = new JsonArray();
            if (ev.has("sounds")) {
                for (JsonElement s : ev.getAsJsonArray("sounds")) {
                    if (s.isJsonPrimitive()) {
                        sounds.add(s.getAsString());
                        continue;
                    }
                    JsonObject so = s.getAsJsonObject().deepCopy();
                    so.remove("attenuation_distance");
                    so.remove("preload");
                    if (so.has("type") && so.get("type").getAsString().equals("event")) so.addProperty("type", "event");
                    sounds.add(so);
                }
            }
            n.add("sounds", sounds);
            res.add(e.getKey(), n);
        }
        Files.write(dst, new GsonBuilder().setPrettyPrinting().create().toJson(res).getBytes(StandardCharsets.UTF_8));
        System.out.println("sounds: " + res.size() + " events");
    }

    static String category(String name) {
        String n = name.toLowerCase();
        if (n.contains("music") || n.contains("disc") || n.contains("record")) return "record";
        if (n.contains("ambient") || n.contains("ambience")) return "ambient";
        if (n.contains("step") || n.contains("block")) return "block";
        return "hostile";
    }
}
