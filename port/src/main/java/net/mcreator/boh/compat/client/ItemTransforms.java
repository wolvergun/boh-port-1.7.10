package net.mcreator.boh.compat.client;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.util.ResourceLocation;

/** "display" transforms of 1.20 item models (following the parent chain), applied like ItemTransform.apply. */
public final class ItemTransforms {

    public static final class Transform {

        final float[] rot, trans, scale;

        Transform(float[] rot, float[] trans, float[] scale) {
            this.rot = rot;
            this.trans = trans;
            this.scale = scale;
        }

        public void apply(boolean leftHand, PoseStack pose) {
            float rx = rot[0], ry = rot[1], rz = rot[2];
            if (leftHand) {
                ry = -ry;
                rz = -rz;
            }
            pose.translate((leftHand ? -1 : 1) * trans[0], trans[1], trans[2]);
            float d = (float) (Math.PI / 180);
            pose.mulPose(new Quaternionf().rotationXYZ(rx * d, ry * d, rz * d));
            pose.scale(scale[0], scale[1], scale[2]);
        }
    }

    public static final Transform NONE = new Transform(new float[3], new float[3], new float[] { 1, 1, 1 });
    private static final Map<String, Map<String, Transform>> CACHE = new HashMap<>();

    private ItemTransforms() {}

    public static Transform get(ResourceLocation itemId, String context) {
        Map<String, Transform> m = CACHE.computeIfAbsent(itemId.toString(), k -> load(itemId.getResourceDomain() + ":item/" + itemId.getResourcePath()));
        Transform t = m.get(context);
        if (t == null && context.endsWith("lefthand")) t = m.get(context.replace("lefthand", "righthand"));
        return t == null ? NONE : t;
    }

    private static Map<String, Transform> load(String model) {
        Map<String, Transform> out = new HashMap<>();
        for (int depth = 0; model != null && depth < 8; depth++) {
            String ns = model.contains(":") ? model.substring(0, model.indexOf(':')) : "minecraft";
            String path = model.contains(":") ? model.substring(model.indexOf(':') + 1) : model;
            JsonObject o = read("/assets/" + ns + "/models/" + path + ".json");
            if (o == null) break;
            if (o.has("display")) {
                for (Map.Entry<String, com.google.gson.JsonElement> e : o.getAsJsonObject("display").entrySet()) {
                    if (out.containsKey(e.getKey())) continue;
                    JsonObject d = e.getValue().getAsJsonObject();
                    float[] t = vec(d, "translation", 0);
                    for (int i = 0; i < 3; i++) t[i] = Math.max(-5, Math.min(5, t[i] * 0.0625F));
                    float[] s = vec(d, "scale", 1);
                    for (int i = 0; i < 3; i++) s[i] = Math.max(-4, Math.min(4, s[i]));
                    out.put(e.getKey(), new Transform(vec(d, "rotation", 0), t, s));
                }
            }
            model = o.has("parent") ? o.get("parent").getAsString() : null;
        }
        return out;
    }

    private static float[] vec(JsonObject o, String key, float def) {
        float[] v = { def, def, def };
        if (o.has(key)) {
            JsonArray a = o.getAsJsonArray(key);
            for (int i = 0; i < 3 && i < a.size(); i++) v[i] = a.get(i).getAsFloat();
        }
        return v;
    }

    private static JsonObject read(String path) {
        try (InputStream in = ItemTransforms.class.getResourceAsStream(path)) {
            return in == null ? null : new JsonParser().parse(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
        } catch (Exception e) {
            return null;
        }
    }
}
