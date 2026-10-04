package net.mcreator.boh.geo;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.mcreator.boh.BohMod;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public final class GeoLoader {
    private static final Map<ResourceLocation, BakedGeoModel> MODELS = new HashMap<>();
    private static final Map<ResourceLocation, Map<String, Animation>> ANIMATIONS = new HashMap<>();
    private static final int[] WEST = new int[]{3, 2, 0, 1};
    private static final int[] EAST = new int[]{4, 5, 7, 6};
    private static final int[] NORTH = new int[]{2, 4, 6, 0};
    private static final int[] SOUTH = new int[]{5, 3, 1, 7};
    private static final int[] UP = new int[]{3, 5, 4, 2};
    private static final int[] DOWN = new int[]{0, 6, 7, 1};

    private GeoLoader() {
    }

    public static void clearCache() {
        MODELS.clear();
        ANIMATIONS.clear();
    }

    static JsonObject readJson(ResourceLocation loc) throws Exception {
        JsonObject var2;
        try (InputStream in = Minecraft.getMinecraft().getResourceManager().getResource(loc).getInputStream()) {
            var2 = new JsonParser().parse(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
        }

        return var2;
    }

    public static BakedGeoModel getModel(ResourceLocation loc) {
        BakedGeoModel model = MODELS.get(loc);
        if (model == null && !MODELS.containsKey(loc)) {
            try {
                model = bakeModel(readJson(loc));
            } catch (Exception var3) {
                BohMod.LOGGER.error("Failed to load geo model {}", new Object[]{loc, var3});
            }

            MODELS.put(loc, model);
        }

        return model;
    }

    public static Map<String, Animation> getAnimations(ResourceLocation loc) {
        Map<String, Animation> anims = ANIMATIONS.get(loc);
        if (anims == null) {
            anims = new HashMap<>();

            try {
                JsonObject root = readJson(loc);
                JsonObject obj = root.getAsJsonObject("animations");
                if (obj != null) {
                    for (Entry<String, JsonElement> e : obj.entrySet()) {
                        try {
                            anims.put(e.getKey(), bakeAnimation(e.getKey(), e.getValue().getAsJsonObject()));
                        } catch (Exception var7) {
                            BohMod.LOGGER.error("Bad animation {} in {}", new Object[]{e.getKey(), loc, var7});
                        }
                    }
                }
            } catch (Exception var8) {
                BohMod.LOGGER.error("Failed to load animations {}", new Object[]{loc, var8});
            }

            ANIMATIONS.put(loc, anims);
        }

        return anims;
    }

    private static double[] arr(JsonObject o, String key) {
        JsonElement e = o.get(key);
        if (e != null && e.isJsonArray()) {
            JsonArray a = e.getAsJsonArray();
            double[] d = new double[a.size()];

            for (int i = 0; i < d.length; i++) {
                d[i] = a.get(i).getAsDouble();
            }

            return d;
        } else {
            return null;
        }
    }

    private static double[] vec(JsonObject o, String key) {
        double[] d = arr(o, key);
        return d != null && d.length >= 3 ? d : new double[3];
    }

    private static Boolean optBool(JsonObject o, String key) {
        return o.has(key) ? o.get(key).getAsBoolean() : null;
    }

    private static Double optDouble(JsonObject o, String key) {
        return o.has(key) ? o.get(key).getAsDouble() : null;
    }

    static BakedGeoModel bakeModel(JsonObject root) {
        JsonObject geo = root.getAsJsonArray("minecraft:geometry").get(0).getAsJsonObject();
        JsonObject desc = geo.getAsJsonObject("description");
        float texW = desc.has("texture_width") ? desc.get("texture_width").getAsFloat() : 64.0F;
        float texH = desc.has("texture_height") ? desc.get("texture_height").getAsFloat() : 64.0F;
        Map<String, JsonObject> bonesByName = new LinkedHashMap<>();
        Map<String, List<String>> children = new HashMap<>();
        List<String> roots = new ArrayList<>();

        for (JsonElement el : geo.has("bones") ? geo.getAsJsonArray("bones") : new JsonArray()) {
            JsonObject b = el.getAsJsonObject();
            bonesByName.put(b.get("name").getAsString(), b);
        }

        for (Entry<String, JsonObject> e : bonesByName.entrySet()) {
            JsonObject b = e.getValue();
            String parent = b.has("parent") ? b.get("parent").getAsString() : null;
            if (parent != null && bonesByName.containsKey(parent)) {
                children.computeIfAbsent(parent, k -> new ArrayList<>()).add(e.getKey());
            } else {
                roots.add(e.getKey());
            }
        }

        List<GeoBone> top = new ArrayList<>();

        for (String r : roots) {
            top.add(bakeBone(r, null, bonesByName, children, texW, texH));
        }

        return new BakedGeoModel(top, texW, texH);
    }

    private static GeoBone bakeBone(String name, GeoBone parent, Map<String, JsonObject> all, Map<String, List<String>> children, float texW, float texH) {
        JsonObject b = all.get(name);
        GeoBone bone = new GeoBone(parent, name, optBool(b, "mirror"), optDouble(b, "inflate"), optBool(b, "neverRender"), optBool(b, "reset"));
        double[] rot = vec(b, "rotation");
        double[] pivot = vec(b, "pivot");
        bone.updateRotation((float)Math.toRadians(-rot[0]), (float)Math.toRadians(-rot[1]), (float)Math.toRadians(rot[2]));
        bone.updatePivot((float)(-pivot[0]), (float)pivot[1], (float)pivot[2]);
        bone.resetStateChanges();
        if (b.has("cubes")) {
            for (JsonElement c : b.getAsJsonArray("cubes")) {
                bone.getCubes().add(bakeCube(c.getAsJsonObject(), bone, texW, texH));
            }
        }

        List<String> kids = children.get(name);
        if (kids != null) {
            for (String k : kids) {
                bone.getChildBones().add(bakeBone(k, bone, all, children, texW, texH));
            }
        }

        return bone;
    }

    private static GeoCube bakeCube(JsonObject c, GeoBone bone, float texW, float texH) {
        boolean mirror = Boolean.TRUE.equals(optBool(c, "mirror"));
        Double cubeInflate = optDouble(c, "inflate");
        double inflate = cubeInflate != null ? cubeInflate / 16.0 : (bone.getInflate() == null ? 0.0 : bone.getInflate() / 16.0);
        double[] size = vec(c, "size");
        double[] origin = vec(c, "origin");
        double[] rotation = vec(c, "rotation");
        double[] pivot = vec(c, "pivot");
        double ox = -(origin[0] + size[0]) / 16.0;
        double oy = origin[1] / 16.0;
        double oz = origin[2] / 16.0;
        double sx = size[0] / 16.0;
        double sy = size[1] / 16.0;
        double sz = size[2] / 16.0;
        double x0 = ox - inflate;
        double y0 = oy - inflate;
        double z0 = oz - inflate;
        double x1 = ox + sx + inflate;
        double y1 = oy + sy + inflate;
        double z1 = oz + sz + inflate;
        double[][] v = new double[][]{{x0, y0, z0}, {x0, y0, z1}, {x0, y1, z0}, {x0, y1, z1}, {x1, y1, z0}, {x1, y1, z1}, {x1, y0, z0}, {x1, y0, z1}};
        JsonElement uvEl = c.get("uv");
        boolean boxUv = uvEl == null || uvEl.isJsonArray();
        double[] boxUvCoords = new double[2];
        if (uvEl != null && uvEl.isJsonArray()) {
            boxUvCoords = arr(c, "uv");
        }

        JsonObject faceUvs = uvEl != null && uvEl.isJsonObject() ? uvEl.getAsJsonObject() : null;
        GeoQuad[] quads = new GeoQuad[6];
        GeoLoader.Dir[] dirs = GeoLoader.Dir.values();

        for (int i = 0; i < 6; i++) {
            GeoLoader.Dir dir = dirs[i];
            int[] verts = verticesForQuad(dir, boxUv, mirror);
            int uvRot = 0;
            double u;
            double vv;
            double us;
            double vs;
            if (!boxUv) {
                if (faceUvs == null || !faceUvs.has(dir.json)) {
                    continue;
                }

                JsonObject f = faceUvs.getAsJsonObject(dir.json);
                double[] fuv = arr(f, "uv");
                double[] fsz = arr(f, "uv_size");
                if (fuv == null) {
                    continue;
                }

                u = fuv[0];
                vv = fuv[1];
                us = fsz == null ? 0.0 : fsz[0];
                vs = fsz == null ? 0.0 : fsz[1];
                if (f.has("uv_rotation")) {
                    uvRot = (f.get("uv_rotation").getAsInt() % 360 + 360) % 360 / 90;
                }
            } else {
                double fx = Math.floor(size[0]);
                double fy = Math.floor(size[1]);
                double fz = Math.floor(size[2]);
                double bu = boxUvCoords.length > 0 ? boxUvCoords[0] : 0.0;
                double bv = boxUvCoords.length > 1 ? boxUvCoords[1] : 0.0;
                switch (dir) {
                    case WEST:
                        u = bu + fz + fx;
                        vv = bv + fz;
                        us = fz;
                        vs = fy;
                        break;
                    case EAST:
                        u = bu;
                        vv = bv + fz;
                        us = fz;
                        vs = fy;
                        break;
                    case NORTH:
                        u = bu + fz;
                        vv = bv + fz;
                        us = fx;
                        vs = fy;
                        break;
                    case SOUTH:
                        u = bu + fz + fx + fz;
                        vv = bv + fz;
                        us = fx;
                        vs = fy;
                        break;
                    case UP:
                        u = bu + fz;
                        vv = bv;
                        us = fx;
                        vs = fz;
                        break;
                    default:
                        u = bu + fz + fx;
                        vv = bv + fz;
                        us = fx;
                        vs = -fz;
                }
            }

            quads[i] = buildQuad(v, verts, (float)u, (float)vv, (float)us, (float)vs, uvRot, texW, texH, mirror, dir);
        }

        return new GeoCube(
            quads,
            (float)(-pivot[0]),
            (float)pivot[1],
            (float)pivot[2],
            (float)Math.toRadians(-rotation[0]),
            (float)Math.toRadians(-rotation[1]),
            (float)Math.toRadians(rotation[2]),
            (float)size[0],
            (float)size[1],
            (float)size[2],
            mirror
        );
    }

    private static int[] verticesForQuad(GeoLoader.Dir dir, boolean boxUv, boolean mirror) {
        switch (dir) {
            case WEST:
                return mirror ? EAST : WEST;
            case EAST:
                return mirror ? WEST : EAST;
            case NORTH:
                return NORTH;
            case SOUTH:
                return SOUTH;
            case UP:
                return mirror && !boxUv ? DOWN : UP;
            default:
                return mirror && !boxUv ? UP : DOWN;
        }
    }

    private static GeoQuad buildQuad(
        double[][] v, int[] idx, float u, float vv, float uSize, float vSize, int uvRot, float texW, float texH, boolean mirror, GeoLoader.Dir dir
    ) {
        float uWidth = (u + uSize) / texW;
        float vHeight = (vv + vSize) / texH;
        u /= texW;
        vv /= texH;
        float nx = dir.nx;
        float ny = dir.ny;
        float nz = dir.nz;
        if (!mirror) {
            float t = uWidth;
            uWidth = u;
            u = t;
        } else {
            nx = -nx;
        }
        float[] var22 = switch (uvRot) {
            case 1 -> new float[]{uWidth, vv, uWidth, vHeight, u, vHeight, u, vv};
            case 2 -> new float[]{uWidth, vHeight, u, vHeight, u, vv, uWidth, vv};
            case 3 -> new float[]{u, vHeight, u, vv, uWidth, vv, uWidth, vHeight};
            default -> new float[]{u, vv, uWidth, vv, uWidth, vHeight, u, vHeight};
        };
        float[] data = new float[20];

        for (int i = 0; i < 4; i++) {
            double[] p = v[idx[i]];
            data[i * 5] = (float)p[0];
            data[i * 5 + 1] = (float)p[1];
            data[i * 5 + 2] = (float)p[2];
            data[i * 5 + 3] = var22[i * 2];
            data[i * 5 + 4] = var22[i * 2 + 1];
        }

        return new GeoQuad(data, nx, ny, nz);
    }

    static Animation bakeAnimation(String name, JsonObject obj) {
        double length = obj.has("animation_length") ? obj.get("animation_length").getAsDouble() * 20.0 : -1.0;
        Animation.LoopType loop = Animation.LoopType.fromJson(obj.get("loop"));
        JsonObject bonesObj = obj.has("bones") ? obj.getAsJsonObject("bones") : new JsonObject();
        List<BoneAnimation> list = new ArrayList<>();

        for (Entry<String, JsonElement> e : bonesObj.entrySet()) {
            JsonObject b = e.getValue().getAsJsonObject();
            list.add(
                new BoneAnimation(
                    e.getKey(),
                    buildStack(getKeyframes(b.get("rotation")), true),
                    buildStack(getKeyframes(b.get("position")), false),
                    buildStack(getKeyframes(b.get("scale")), false)
                )
            );
        }

        BoneAnimation[] arr = list.toArray(new BoneAnimation[0]);
        if (length == -1.0) {
            for (BoneAnimation ba : arr) {
                length = Math.max(length, ba.rotation.getLastKeyframeTime());
                length = Math.max(length, ba.position.getLastKeyframeTime());
                length = Math.max(length, ba.scale.getLastKeyframeTime());
            }

            if (length <= 0.0) {
                length = Double.MAX_VALUE;
            }
        }

        return new Animation(name, length, loop, arr);
    }

    private static List<GeoLoader.TimedValue> getKeyframes(JsonElement element) {
        List<GeoLoader.TimedValue> list = new ArrayList<>();
        if (element == null) {
            return list;
        } else {
            if (element.isJsonPrimitive()) {
                JsonArray a = new JsonArray();
                a.add(element);
                a.add(element);
                a.add(element);
                element = a;
            }

            if (element.isJsonArray()) {
                list.add(new GeoLoader.TimedValue(0.0, element));
                return list;
            } else {
                JsonObject obj = element.getAsJsonObject();
                if (obj.has("vector")) {
                    list.add(new GeoLoader.TimedValue(0.0, obj));
                    return list;
                } else {
                    for (Entry<String, JsonElement> entry : obj.entrySet()) {
                        double t;
                        try {
                            t = Double.parseDouble(entry.getKey());
                        } catch (NumberFormatException var8) {
                            t = 0.0;
                        }

                        JsonElement val = entry.getValue();
                        if (val.isJsonObject() && !val.getAsJsonObject().has("vector")) {
                            addBedrockKeyframes(t, val.getAsJsonObject(), list);
                        } else {
                            list.add(new GeoLoader.TimedValue(t, val));
                        }
                    }

                    return list;
                }
            }
        }
    }

    private static JsonArray extractBedrockKeyframe(JsonElement kf) {
        if (kf.isJsonArray()) {
            return kf.getAsJsonArray();
        } else {
            JsonObject o = kf.getAsJsonObject();
            if (o.has("vector")) {
                return o.getAsJsonArray("vector");
            } else {
                return o.has("pre") ? o.getAsJsonArray("pre") : o.getAsJsonArray("post");
            }
        }
    }

    private static void addBedrockKeyframes(double t, JsonObject kf, List<GeoLoader.TimedValue> out) {
        if (kf.has("pre")) {
            out.add(new GeoLoader.TimedValue(t == 0.0 ? t : t - 0.001, extractBedrockKeyframe(kf.get("pre"))));
        }

        if (kf.has("post")) {
            JsonArray values = extractBedrockKeyframe(kf.get("post"));
            if (kf.has("lerp_mode")) {
                JsonObject o = new JsonObject();
                o.add("vector", values);
                o.add("easing", kf.get("lerp_mode"));
                out.add(new GeoLoader.TimedValue(t, o));
            } else {
                out.add(new GeoLoader.TimedValue(t, values));
            }
        }
    }

    private static double num(JsonElement e) {
        if (e == null) {
            return 0.0;
        } else {
            JsonPrimitive p = e.getAsJsonPrimitive();
            if (p.isNumber()) {
                return p.getAsDouble();
            } else {
                try {
                    return Double.parseDouble(p.getAsString().trim());
                } catch (NumberFormatException var3) {
                    return 0.0;
                }
            }
        }
    }

    private static KeyframeStack buildStack(List<GeoLoader.TimedValue> entries, boolean rotation) {
        if (entries.isEmpty()) {
            return new KeyframeStack();
        } else {
            List<Keyframe> xs = new ArrayList<>();
            List<Keyframe> ys = new ArrayList<>();
            List<Keyframe> zs = new ArrayList<>();
            double xPrev = 0.0;
            double yPrev = 0.0;
            double zPrev = 0.0;
            GeoLoader.TimedValue prev = null;

            for (GeoLoader.TimedValue entry : entries) {
                double prevTime = prev != null ? prev.time : 0.0;
                double delta = entry.time - prevTime;
                JsonObject entryObj = entry.value.isJsonObject() ? entry.value.getAsJsonObject() : null;
                JsonArray vecArr = entryObj == null ? entry.value.getAsJsonArray() : entryObj.getAsJsonArray("vector");
                double x = num(vecArr.get(0));
                double y = num(vecArr.size() > 1 ? vecArr.get(1) : vecArr.get(0));
                double z = num(vecArr.size() > 2 ? vecArr.get(2) : vecArr.get(0));
                if (rotation) {
                    x = Math.toRadians(-x);
                    y = Math.toRadians(-y);
                    z = Math.toRadians(z);
                }

                EasingType easing = entryObj != null && entryObj.has("easing")
                    ? EasingType.fromString(entryObj.get("easing").getAsString())
                    : EasingType.LINEAR;
                double[] args = new double[0];
                if (entryObj != null && entryObj.has("easingArgs")) {
                    JsonArray a = entryObj.getAsJsonArray("easingArgs");
                    args = new double[a.size()];

                    for (int i = 0; i < args.length; i++) {
                        args[i] = a.get(i).getAsDouble();
                    }
                }

                xs.add(new Keyframe(delta * 20.0, prev == null ? x : xPrev, x, easing, args));
                ys.add(new Keyframe(delta * 20.0, prev == null ? y : yPrev, y, easing, args));
                zs.add(new Keyframe(delta * 20.0, prev == null ? z : zPrev, z, easing, args));
                xPrev = x;
                yPrev = y;
                zPrev = z;
                prev = entry;
            }

            return new KeyframeStack(splineArgs(xs), splineArgs(ys), splineArgs(zs));
        }
    }

    private static List<Keyframe> splineArgs(List<Keyframe> frames) {
        if (frames.size() == 1) {
            Keyframe f = frames.get(0);
            if (f.easingType != EasingType.LINEAR) {
                frames.set(0, new Keyframe(f.length, f.startValue, f.endValue, EasingType.LINEAR, new double[0]));
                return frames;
            }
        }

        for (int i = 0; i < frames.size(); i++) {
            Keyframe f = frames.get(i);
            if (f.easingType == EasingType.CATMULLROM) {
                frames.set(
                    i,
                    new Keyframe(
                        f.length,
                        f.startValue,
                        f.endValue,
                        f.easingType,
                        new double[]{i == 0 ? f.startValue : frames.get(i - 1).endValue, i + 1 >= frames.size() ? f.endValue : frames.get(i + 1).endValue}
                    )
                );
            }
        }

        return frames;
    }

    private static enum Dir {
        WEST(-1.0F, 0.0F, 0.0F, "west"),
        EAST(1.0F, 0.0F, 0.0F, "east"),
        NORTH(0.0F, 0.0F, -1.0F, "north"),
        SOUTH(0.0F, 0.0F, 1.0F, "south"),
        UP(0.0F, 1.0F, 0.0F, "up"),
        DOWN(0.0F, -1.0F, 0.0F, "down");

        final float nx;
        final float ny;
        final float nz;
        final String json;

        private Dir(float nx, float ny, float nz, String json) {
            this.nx = nx;
            this.ny = ny;
            this.nz = nz;
            this.json = json;
        }
    }

    private static final class TimedValue {
        final double time;
        final JsonElement value;

        TimedValue(double time, JsonElement value) {
            this.time = time;
            this.value = value;
        }
    }
}
