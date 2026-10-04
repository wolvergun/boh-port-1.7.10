package net.mcreator.boh.compat.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.util.ResourceLocation;

public final class ItemTransforms {
    public static final ItemTransforms.Transform NONE = new ItemTransforms.Transform(new float[3], new float[3], new float[]{1.0F, 1.0F, 1.0F});
    private static final Map<String, Map<String, ItemTransforms.Transform>> CACHE = new HashMap<>();

    private ItemTransforms() {
    }

    public static ItemTransforms.Transform get(ResourceLocation itemId, String context) {
        Map<String, ItemTransforms.Transform> m = CACHE.computeIfAbsent(
            itemId.toString(), k -> load(itemId.getResourceDomain() + ":item/" + itemId.getResourcePath())
        );
        ItemTransforms.Transform t = m.get(context);
        if (t == null && context.endsWith("lefthand")) {
            t = m.get(context.replace("lefthand", "righthand"));
        }

        return t == null ? NONE : t;
    }

    private static Map<String, ItemTransforms.Transform> load(String model) {
        Map<String, ItemTransforms.Transform> out = new HashMap<>();

        for (int depth = 0; model != null && depth < 8; depth++) {
            String ns = model.contains(":") ? model.substring(0, model.indexOf(58)) : "minecraft";
            String path = model.contains(":") ? model.substring(model.indexOf(58) + 1) : model;
            JsonObject o = read("/assets/" + ns + "/models/" + path + ".json");
            if (o == null) {
                break;
            }

            if (o.has("display")) {
                for (Entry<String, JsonElement> e : o.getAsJsonObject("display").entrySet()) {
                    if (!out.containsKey(e.getKey())) {
                        JsonObject d = e.getValue().getAsJsonObject();
                        float[] t = vec(d, "translation", 0.0F);

                        for (int i = 0; i < 3; i++) {
                            t[i] = Math.max(-5.0F, Math.min(5.0F, t[i] * 0.0625F));
                        }

                        float[] s = vec(d, "scale", 1.0F);

                        for (int i = 0; i < 3; i++) {
                            s[i] = Math.max(-4.0F, Math.min(4.0F, s[i]));
                        }

                        out.put(e.getKey(), new ItemTransforms.Transform(vec(d, "rotation", 0.0F), t, s));
                    }
                }
            }

            model = o.has("parent") ? o.get("parent").getAsString() : null;
        }

        return out;
    }

    private static float[] vec(JsonObject o, String key, float def) {
        float[] v = new float[]{def, def, def};
        if (o.has(key)) {
            JsonArray a = o.getAsJsonArray(key);

            for (int i = 0; i < 3 && i < a.size(); i++) {
                v[i] = a.get(i).getAsFloat();
            }
        }

        return v;
    }

    private static JsonObject read(String path) {
        try {
            JsonObject var2;
            try (InputStream in = ItemTransforms.class.getResourceAsStream(path)) {
                var2 = in == null ? null : new JsonParser().parse(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
            }

            return var2;
        } catch (Exception var6) {
            return null;
        }
    }

    public static final class Transform {
        final float[] rot;
        final float[] trans;
        final float[] scale;

        Transform(float[] rot, float[] trans, float[] scale) {
            this.rot = rot;
            this.trans = trans;
            this.scale = scale;
        }

        public void apply(boolean leftHand, PoseStack pose) {
            float rx = this.rot[0];
            float ry = this.rot[1];
            float rz = this.rot[2];
            if (leftHand) {
                ry = -ry;
                rz = -rz;
            }

            pose.translate((leftHand ? -1 : 1) * this.trans[0], this.trans[1], this.trans[2]);
            float d = (float) (Math.PI / 180.0);
            pose.mulPose(new Quaternionf().rotationXYZ(rx * d, ry * d, rz * d));
            pose.scale(this.scale[0], this.scale[1], this.scale[2]);
        }
    }
}
