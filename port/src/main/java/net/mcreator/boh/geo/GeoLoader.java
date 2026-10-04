package net.mcreator.boh.geo;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.mcreator.boh.BohMod;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

/** Loads and caches Bedrock .geo.json models and .animation.json files from client resources. */
public final class GeoLoader {

    private static final Map<ResourceLocation, BakedGeoModel> MODELS = new HashMap<>();
    private static final Map<ResourceLocation, Map<String, Animation>> ANIMATIONS = new HashMap<>();

    private GeoLoader() {}

    public static void clearCache() {
        MODELS.clear();
        ANIMATIONS.clear();
    }

    static JsonObject readJson(ResourceLocation loc) throws Exception {
        try (InputStream in = Minecraft.getMinecraft()
            .getResourceManager()
            .getResource(loc)
            .getInputStream()) {
            return new JsonParser().parse(new InputStreamReader(in, StandardCharsets.UTF_8))
                .getAsJsonObject();
        }
    }

    public static BakedGeoModel getModel(ResourceLocation loc) {
        BakedGeoModel model = MODELS.get(loc);
        if (model == null && !MODELS.containsKey(loc)) {
            try {
                model = bakeModel(readJson(loc));
            } catch (Exception e) {
                BohMod.LOGGER.error("Failed to load geo model {}", loc, e);
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
                if (obj != null) for (Map.Entry<String, JsonElement> e : obj.entrySet()) {
                    try {
                        anims.put(e.getKey(), bakeAnimation(e.getKey(), e.getValue().getAsJsonObject()));
                    } catch (Exception ex) {
                        BohMod.LOGGER.error("Bad animation {} in {}", e.getKey(), loc, ex);
                    }
                }
            } catch (Exception e) {
                BohMod.LOGGER.error("Failed to load animations {}", loc, e);
            }
            ANIMATIONS.put(loc, anims);
        }
        return anims;
    }

    // ---------------------------------------------------------------- geometry

    private static double[] arr(JsonObject o, String key) {
        JsonElement e = o.get(key);
        if (e == null || !e.isJsonArray()) return null;
        JsonArray a = e.getAsJsonArray();
        double[] d = new double[a.size()];
        for (int i = 0; i < d.length; i++) d[i] = a.get(i).getAsDouble();
        return d;
    }

    private static double[] vec(JsonObject o, String key) {
        double[] d = arr(o, key);
        return d == null || d.length < 3 ? new double[3] : d;
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
        float texW = desc.has("texture_width") ? desc.get("texture_width").getAsFloat() : 64;
        float texH = desc.has("texture_height") ? desc.get("texture_height").getAsFloat() : 64;

        Map<String, JsonObject> bonesByName = new LinkedHashMap<>();
        Map<String, List<String>> children = new HashMap<>();
        List<String> roots = new ArrayList<>();
        JsonArray bones = geo.has("bones") ? geo.getAsJsonArray("bones") : new JsonArray();
        for (JsonElement el : bones) {
            JsonObject b = el.getAsJsonObject();
            bonesByName.put(b.get("name").getAsString(), b);
        }
        for (Map.Entry<String, JsonObject> e : bonesByName.entrySet()) {
            JsonObject b = e.getValue();
            String parent = b.has("parent") ? b.get("parent").getAsString() : null;
            if (parent == null || !bonesByName.containsKey(parent)) roots.add(e.getKey());
            else children.computeIfAbsent(parent, k -> new ArrayList<>()).add(e.getKey());
        }
        List<GeoBone> top = new ArrayList<>();
        for (String r : roots) top.add(bakeBone(r, null, bonesByName, children, texW, texH));
        return new BakedGeoModel(top, texW, texH);
    }

    private static GeoBone bakeBone(String name, GeoBone parent, Map<String, JsonObject> all,
        Map<String, List<String>> children, float texW, float texH) {
        JsonObject b = all.get(name);
        GeoBone bone = new GeoBone(
            parent,
            name,
            optBool(b, "mirror"),
            optDouble(b, "inflate"),
            optBool(b, "neverRender"),
            optBool(b, "reset"));
        double[] rot = vec(b, "rotation");
        double[] pivot = vec(b, "pivot");
        bone.updateRotation(
            (float) Math.toRadians(-rot[0]),
            (float) Math.toRadians(-rot[1]),
            (float) Math.toRadians(rot[2]));
        bone.updatePivot((float) -pivot[0], (float) pivot[1], (float) pivot[2]);
        bone.resetStateChanges();
        if (b.has("cubes")) for (JsonElement c : b.getAsJsonArray("cubes"))
            bone.getCubes()
                .add(bakeCube(c.getAsJsonObject(), bone, texW, texH));
        List<String> kids = children.get(name);
        if (kids != null) for (String k : kids) bone.getChildBones()
            .add(bakeBone(k, bone, all, children, texW, texH));
        return bone;
    }

    // Vertex indices: 0 bLB, 1 bRB, 2 tLB, 3 tRB, 4 tLF, 5 tRF, 6 bLF, 7 bRF (GeckoLib VertexSet order)
    private static final int[] WEST = { 3, 2, 0, 1 };
    private static final int[] EAST = { 4, 5, 7, 6 };
    private static final int[] NORTH = { 2, 4, 6, 0 };
    private static final int[] SOUTH = { 5, 3, 1, 7 };
    private static final int[] UP = { 3, 5, 4, 2 };
    private static final int[] DOWN = { 0, 6, 7, 1 };

    private enum Dir {

        WEST(-1, 0, 0, "west"),
        EAST(1, 0, 0, "east"),
        NORTH(0, 0, -1, "north"),
        SOUTH(0, 0, 1, "south"),
        UP(0, 1, 0, "up"),
        DOWN(0, -1, 0, "down");

        final float nx, ny, nz;
        final String json;

        Dir(float nx, float ny, float nz, String json) {
            this.nx = nx;
            this.ny = ny;
            this.nz = nz;
            this.json = json;
        }
    }

    private static GeoCube bakeCube(JsonObject c, GeoBone bone, float texW, float texH) {
        boolean mirror = Boolean.TRUE.equals(optBool(c, "mirror"));
        Double cubeInflate = optDouble(c, "inflate");
        double inflate = cubeInflate != null ? cubeInflate / 16f
            : (bone.getInflate() == null ? 0 : bone.getInflate() / 16f);
        double[] size = vec(c, "size");
        double[] origin = vec(c, "origin");
        double[] rotation = vec(c, "rotation");
        double[] pivot = vec(c, "pivot");

        double ox = -(origin[0] + size[0]) / 16d, oy = origin[1] / 16d, oz = origin[2] / 16d;
        double sx = size[0] / 16d, sy = size[1] / 16d, sz = size[2] / 16d;
        double x0 = ox - inflate, y0 = oy - inflate, z0 = oz - inflate;
        double x1 = ox + sx + inflate, y1 = oy + sy + inflate, z1 = oz + sz + inflate;
        double[][] v = {
            { x0, y0, z0 }, { x0, y0, z1 }, { x0, y1, z0 }, { x0, y1, z1 },
            { x1, y1, z0 }, { x1, y1, z1 }, { x1, y0, z0 }, { x1, y0, z1 } };

        JsonElement uvEl = c.get("uv");
        boolean boxUv = uvEl == null || uvEl.isJsonArray();
        double[] boxUvCoords = new double[2];
        if (uvEl != null && uvEl.isJsonArray()) boxUvCoords = arr(c, "uv");
        JsonObject faceUvs = uvEl != null && uvEl.isJsonObject() ? uvEl.getAsJsonObject() : null;

        GeoQuad[] quads = new GeoQuad[6];
        Dir[] dirs = Dir.values();
        for (int i = 0; i < 6; i++) {
            Dir dir = dirs[i];
            int[] verts = verticesForQuad(dir, boxUv, mirror);
            double u, vv, us, vs;
            int uvRot = 0;
            if (!boxUv) {
                if (faceUvs == null || !faceUvs.has(dir.json)) continue;
                JsonObject f = faceUvs.getAsJsonObject(dir.json);
                double[] fuv = arr(f, "uv");
                double[] fsz = arr(f, "uv_size");
                if (fuv == null) continue;
                u = fuv[0];
                vv = fuv[1];
                us = fsz == null ? 0 : fsz[0];
                vs = fsz == null ? 0 : fsz[1];
                if (f.has("uv_rotation")) uvRot = ((f.get("uv_rotation").getAsInt() % 360) + 360) % 360 / 90;
            } else {
                double fx = Math.floor(size[0]), fy = Math.floor(size[1]), fz = Math.floor(size[2]);
                double bu = boxUvCoords.length > 0 ? boxUvCoords[0] : 0, bv = boxUvCoords.length > 1 ? boxUvCoords[1] : 0;
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
                        break;
                }
            }
            quads[i] = buildQuad(v, verts, (float) u, (float) vv, (float) us, (float) vs, uvRot, texW, texH, mirror, dir);
        }

        return new GeoCube(
            quads,
            (float) -pivot[0],
            (float) pivot[1],
            (float) pivot[2],
            (float) Math.toRadians(-rotation[0]),
            (float) Math.toRadians(-rotation[1]),
            (float) Math.toRadians(rotation[2]),
            (float) size[0],
            (float) size[1],
            (float) size[2],
            mirror);
    }

    private static int[] verticesForQuad(Dir dir, boolean boxUv, boolean mirror) {
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

    private static GeoQuad buildQuad(double[][] v, int[] idx, float u, float vv, float uSize, float vSize, int uvRot,
        float texW, float texH, boolean mirror, Dir dir) {
        float uWidth = (u + uSize) / texW;
        float vHeight = (vv + vSize) / texH;
        u /= texW;
        vv /= texH;
        float nx = dir.nx, ny = dir.ny, nz = dir.nz;
        if (!mirror) {
            float t = uWidth;
            uWidth = u;
            u = t;
        } else {
            nx = -nx;
        }
        float[] uvs;
        switch (uvRot) {
            case 1:
                uvs = new float[] { uWidth, vv, uWidth, vHeight, u, vHeight, u, vv };
                break;
            case 2:
                uvs = new float[] { uWidth, vHeight, u, vHeight, u, vv, uWidth, vv };
                break;
            case 3:
                uvs = new float[] { u, vHeight, u, vv, uWidth, vv, uWidth, vHeight };
                break;
            default:
                uvs = new float[] { u, vv, uWidth, vv, uWidth, vHeight, u, vHeight };
                break;
        }
        float[] data = new float[20];
        for (int i = 0; i < 4; i++) {
            double[] p = v[idx[i]];
            data[i * 5] = (float) p[0];
            data[i * 5 + 1] = (float) p[1];
            data[i * 5 + 2] = (float) p[2];
            data[i * 5 + 3] = uvs[i * 2];
            data[i * 5 + 4] = uvs[i * 2 + 1];
        }
        return new GeoQuad(data, nx, ny, nz);
    }

    // ---------------------------------------------------------------- animations

    private static final class TimedValue {

        final double time;
        final JsonElement value;

        TimedValue(double time, JsonElement value) {
            this.time = time;
            this.value = value;
        }
    }

    static Animation bakeAnimation(String name, JsonObject obj) {
        double length = obj.has("animation_length") ? obj.get("animation_length").getAsDouble() * 20d : -1;
        Animation.LoopType loop = Animation.LoopType.fromJson(obj.get("loop"));
        JsonObject bonesObj = obj.has("bones") ? obj.getAsJsonObject("bones") : new JsonObject();
        List<BoneAnimation> list = new ArrayList<>();
        for (Map.Entry<String, JsonElement> e : bonesObj.entrySet()) {
            JsonObject b = e.getValue().getAsJsonObject();
            list.add(new BoneAnimation(
                e.getKey(),
                buildStack(getKeyframes(b.get("rotation")), true),
                buildStack(getKeyframes(b.get("position")), false),
                buildStack(getKeyframes(b.get("scale")), false)));
        }
        BoneAnimation[] arr = list.toArray(new BoneAnimation[0]);
        if (length == -1) {
            for (BoneAnimation ba : arr) {
                length = Math.max(length, ba.rotation.getLastKeyframeTime());
                length = Math.max(length, ba.position.getLastKeyframeTime());
                length = Math.max(length, ba.scale.getLastKeyframeTime());
            }
            if (length <= 0) length = Double.MAX_VALUE;
        }
        return new Animation(name, length, loop, arr);
    }

    private static List<TimedValue> getKeyframes(JsonElement element) {
        List<TimedValue> list = new ArrayList<>();
        if (element == null) return list;
        if (element.isJsonPrimitive()) {
            JsonArray a = new JsonArray();
            a.add(element);
            a.add(element);
            a.add(element);
            element = a;
        }
        if (element.isJsonArray()) {
            list.add(new TimedValue(0, element));
            return list;
        }
        JsonObject obj = element.getAsJsonObject();
        if (obj.has("vector")) {
            list.add(new TimedValue(0, obj));
            return list;
        }
        for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
            double t;
            try {
                t = Double.parseDouble(entry.getKey());
            } catch (NumberFormatException ex) {
                t = 0;
            }
            JsonElement val = entry.getValue();
            if (val.isJsonObject() && !val.getAsJsonObject()
                .has("vector")) {
                addBedrockKeyframes(t, val.getAsJsonObject(), list);
                continue;
            }
            list.add(new TimedValue(t, val));
        }
        return list;
    }

    private static JsonArray extractBedrockKeyframe(JsonElement kf) {
        if (kf.isJsonArray()) return kf.getAsJsonArray();
        JsonObject o = kf.getAsJsonObject();
        if (o.has("vector")) return o.getAsJsonArray("vector");
        if (o.has("pre")) return o.getAsJsonArray("pre");
        return o.getAsJsonArray("post");
    }

    private static void addBedrockKeyframes(double t, JsonObject kf, List<TimedValue> out) {
        if (kf.has("pre")) out.add(new TimedValue(t == 0 ? t : t - 0.001d, extractBedrockKeyframe(kf.get("pre"))));
        if (kf.has("post")) {
            JsonArray values = extractBedrockKeyframe(kf.get("post"));
            if (kf.has("lerp_mode")) {
                JsonObject o = new JsonObject();
                o.add("vector", values);
                o.add("easing", kf.get("lerp_mode"));
                out.add(new TimedValue(t, o));
            } else {
                out.add(new TimedValue(t, values));
            }
        }
    }

    private static double num(JsonElement e) {
        if (e == null) return 0;
        JsonPrimitive p = e.getAsJsonPrimitive();
        if (p.isNumber()) return p.getAsDouble();
        try {
            return Double.parseDouble(p.getAsString().trim());
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private static KeyframeStack buildStack(List<TimedValue> entries, boolean rotation) {
        if (entries.isEmpty()) return new KeyframeStack();
        List<Keyframe> xs = new ArrayList<>(), ys = new ArrayList<>(), zs = new ArrayList<>();
        double xPrev = 0, yPrev = 0, zPrev = 0;
        TimedValue prev = null;
        for (TimedValue entry : entries) {
            double prevTime = prev != null ? prev.time : 0;
            double delta = entry.time - prevTime;
            JsonObject entryObj = entry.value.isJsonObject() ? entry.value.getAsJsonObject() : null;
            JsonArray vecArr = entryObj == null ? entry.value.getAsJsonArray() : entryObj.getAsJsonArray("vector");
            double x = num(vecArr.get(0)), y = num(vecArr.size() > 1 ? vecArr.get(1) : vecArr.get(0)),
                z = num(vecArr.size() > 2 ? vecArr.get(2) : vecArr.get(0));
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
                for (int i = 0; i < args.length; i++) args[i] = a.get(i).getAsDouble();
            }
            xs.add(new Keyframe(delta * 20, prev == null ? x : xPrev, x, easing, args));
            ys.add(new Keyframe(delta * 20, prev == null ? y : yPrev, y, easing, args));
            zs.add(new Keyframe(delta * 20, prev == null ? z : zPrev, z, easing, args));
            xPrev = x;
            yPrev = y;
            zPrev = z;
            prev = entry;
        }
        return new KeyframeStack(splineArgs(xs), splineArgs(ys), splineArgs(zs));
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
                        new double[] { i == 0 ? f.startValue : frames.get(i - 1).endValue,
                            i + 1 >= frames.size() ? f.endValue : frames.get(i + 1).endValue }));
            }
        }
        return frames;
    }
}
