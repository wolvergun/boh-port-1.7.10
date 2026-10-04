package net.mcreator.boh.compat.block;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Reads the original blockstates/*.json and models/block/*.json. Cube models render through the standard 1.7.10
 * block renderer with per-face icons; models with elements go to the JSON element renderer (client).
 */
public final class BlockModels {

    public enum Kind {
        CUBE,
        CROSS,
        ELEMENTS
    }

    /** One resolved model: textures by key, optional elements, and the variant rotation. */
    public static final class Model {

        public final Map<String, String> textures = new HashMap<>();
        public final List<JsonObject> elements = new ArrayList<>();
        public Kind kind = Kind.CUBE;
        public String vanillaParent;
        public int rotX, rotY;
        public boolean uvlock;
        public float textureWidth = 16, textureHeight = 16;

        Model copyWithRotation(int x, int y, boolean uvlock) {
            Model m = new Model();
            m.textures.putAll(textures);
            m.elements.addAll(elements);
            m.kind = kind;
            m.vanillaParent = vanillaParent;
            m.rotX = x;
            m.rotY = y;
            m.uvlock = uvlock;
            m.textureWidth = textureWidth;
            m.textureHeight = textureHeight;
            return m;
        }

        /** Resolves "#key" references to a texture location string. */
        public String texture(String ref) {
            String t = ref;
            for (int i = 0; i < 8 && t != null && t.startsWith("#"); i++) t = textures.get(t.substring(1));
            return t;
        }
    }

    static final class Info {

        final List<Object[]> variants = new ArrayList<>(); // {Map<String,String> conditions, Model}
        Model fallback;
        int pass;
        @SideOnly(Side.CLIENT)
        Map<String, IIcon> icons;
    }

    private static final Map<Block, Info> INFO = new HashMap<>();
    public static int elementRenderId = -1;

    private BlockModels() {}

    static JsonObject read(String path) {
        try (InputStream in = BlockModels.class.getResourceAsStream(path)) {
            if (in == null) return null;
            return new JsonParser().parse(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
        } catch (Exception e) {
            return null;
        }
    }

    private static Model loadModel(String loc) {
        Model m = new Model();
        Map<String, String> tex = new LinkedHashMap<>();
        String cur = loc;
        String renderType = null;
        for (int depth = 0; depth < 10 && cur != null; depth++) {
            String ns = cur.contains(":") ? cur.substring(0, cur.indexOf(':')) : "minecraft";
            String path = cur.contains(":") ? cur.substring(cur.indexOf(':') + 1) : cur;
            if (!path.contains("/")) path = "block/" + path;
            if (ns.equals("minecraft")) {
                m.vanillaParent = path;
                break;
            }
            JsonObject obj = read("/assets/" + ns + "/models/" + path + ".json");
            if (obj == null) break;
            if (obj.has("textures")) for (Map.Entry<String, JsonElement> e : obj.getAsJsonObject("textures").entrySet())
                tex.putIfAbsent(e.getKey(), e.getValue().getAsString());
            if (obj.has("texture_size")) {
                JsonArray ts = obj.getAsJsonArray("texture_size");
                m.textureWidth = ts.get(0).getAsFloat();
                m.textureHeight = ts.get(1).getAsFloat();
            }
            if (m.elements.isEmpty() && obj.has("elements"))
                for (JsonElement el : obj.getAsJsonArray("elements")) m.elements.add(el.getAsJsonObject());
            if (renderType == null && obj.has("render_type")) renderType = obj.get("render_type").getAsString();
            String parent = obj.has("parent") ? obj.get("parent").getAsString() : null;
            if (parent != null && !parent.contains(":") && !parent.contains("/")) parent = ns + ":custom/" + parent;
            cur = parent;
        }
        m.textures.putAll(tex);
        if (!m.elements.isEmpty()) m.kind = Kind.ELEMENTS;
        else if (m.vanillaParent != null && m.vanillaParent.contains("cross")) m.kind = Kind.CROSS;
        else m.kind = Kind.CUBE;
        m.textures.put("__render_type", renderType == null ? "solid" : renderType);
        return m;
    }

    /** Called when a mod block is registered: parses its blockstate and models. */
    public static void configure(Block block, ResourceLocation id) {
        Info info = new Info();
        JsonObject bs = read("/assets/" + id.getResourceDomain() + "/blockstates/" + id.getResourcePath() + ".json");
        if (bs != null && bs.has("variants")) {
            for (Map.Entry<String, JsonElement> v : bs.getAsJsonObject("variants").entrySet()) {
                JsonElement ve = v.getValue();
                JsonObject vo = ve.isJsonArray() ? ve.getAsJsonArray().get(0).getAsJsonObject() : ve.getAsJsonObject();
                Model base = loadModel(vo.get("model").getAsString());
                Model m = base.copyWithRotation(vo.has("x") ? vo.get("x").getAsInt() : 0, vo.has("y") ? vo.get("y").getAsInt() : 0,
                    vo.has("uvlock") && vo.get("uvlock").getAsBoolean());
                Map<String, String> cond = new HashMap<>();
                if (!v.getKey().isEmpty()) for (String part : v.getKey().split(",")) {
                    String[] kv = part.split("=");
                    if (kv.length == 2) cond.put(kv[0], kv[1]);
                }
                info.variants.add(new Object[] { cond, m });
                if (info.fallback == null) info.fallback = m;
            }
        } else if (bs != null && bs.has("multipart")) {
            for (JsonElement p : bs.getAsJsonArray("multipart")) {
                JsonObject apply = p.getAsJsonObject().get("apply").isJsonArray()
                    ? p.getAsJsonObject().getAsJsonArray("apply").get(0).getAsJsonObject()
                    : p.getAsJsonObject().getAsJsonObject("apply");
                Model m = loadModel(apply.get("model").getAsString());
                info.variants.add(new Object[] { new HashMap<String, String>(), m });
                if (info.fallback == null) info.fallback = m;
            }
        }
        if (info.fallback == null) {
            info.fallback = loadModel(id.getResourceDomain() + ":block/" + id.getResourcePath());
            info.variants.add(new Object[] { new HashMap<String, String>(), info.fallback });
        }
        String rt = info.fallback.textures.get("__render_type");
        info.pass = "translucent".equals(rt) ? 1 : 0;
        INFO.put(block, info);
        if (block instanceof BohBlock) {
            BohBlock b = (BohBlock) block;
            if (info.fallback.kind == Kind.CROSS) b.renderType = 1;
            else if (info.fallback.kind == Kind.ELEMENTS) b.renderType = -2;
        }
    }

    /** Resolves render ids once the client renderer has registered (server keeps the placeholder). */
    public static void assignRenderIds() {
        for (Map.Entry<Block, Info> e : INFO.entrySet())
            if (e.getKey() instanceof BohBlock && ((BohBlock) e.getKey()).renderType == -2)
                ((BohBlock) e.getKey()).renderType = elementRenderId;
    }

    public static int renderPass(Block block) {
        Info i = INFO.get(block);
        return i == null ? 0 : i.pass;
    }

    public static boolean isCutout(Block block) {
        Info i = INFO.get(block);
        if (i == null) return false;
        String rt = i.fallback.textures.get("__render_type");
        return rt != null && rt.startsWith("cutout");
    }

    /** The model of the variant matching a state (first variant whose conditions all hold). */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Model modelFor(BlockState state) {
        Info info = INFO.get(state.getBlock());
        if (info == null) return null;
        for (Object[] v : info.variants) {
            Map<String, String> cond = (Map<String, String>) v[0];
            boolean ok = true;
            for (Map.Entry<String, String> c : cond.entrySet()) {
                Property p = state.definition().getProperty(c.getKey());
                if (p == null) continue;
                if (!p.getName(state.getValue(p)).equals(c.getValue())) {
                    ok = false;
                    break;
                }
            }
            if (ok) return (Model) v[1];
        }
        return info.fallback;
    }

    // ------------------------------------------------------------------ client icons

    @SideOnly(Side.CLIENT)
    public static void registerIcons(Block block, IIconRegister reg) {
        Info info = INFO.get(block);
        if (info == null) return;
        info.icons = new HashMap<>();
        for (Object[] v : info.variants) {
            Model m = (Model) v[1];
            for (Map.Entry<String, String> t : m.textures.entrySet()) {
                if (t.getKey().startsWith("__")) continue;
                String loc = m.texture(t.getValue());
                if (loc == null || loc.startsWith("#") || info.icons.containsKey(loc)) continue;
                info.icons.put(loc, reg.registerIcon(loc.contains(":") ? loc : "minecraft:" + loc));
            }
        }
        if (block instanceof BohBlock) {
            String p = info.fallback.texture("#particle");
            if (p == null) p = info.fallback.texture("#all");
            IIcon icon = p == null ? null : info.icons.get(p);
            if (icon == null && !info.icons.isEmpty()) icon = info.icons.values().iterator().next();
            if (icon != null) ((BohBlock) block).setBlockIcon(icon);
        }
    }

    public static Model fallbackModel(Block block) {
        Info info = INFO.get(block);
        return info == null ? null : info.fallback;
    }

    /** Texture location of a key ("side", "texture", ...) in the block's first model. */
    public static String textureOf(Block block, String key) {
        Info info = INFO.get(block);
        return info == null || info.fallback == null ? null : info.fallback.texture("#" + key);
    }

    @SideOnly(Side.CLIENT)
    public static IIcon iconFor(Block block, String textureLoc) {
        Info info = INFO.get(block);
        return info == null || info.icons == null || textureLoc == null ? null : info.icons.get(textureLoc);
    }

    /** Standard-renderer icon for a cube face (1.7.10 side numbering), honouring the variant's y rotation. */
    @SideOnly(Side.CLIENT)
    public static IIcon icon(Block block, int side, int meta) {
        Info info = INFO.get(block);
        if (info == null || info.icons == null) return null;
        Model m = block instanceof BohBlock ? modelFor(BlockState.of(block, meta)) : info.fallback;
        if (m == null) return null;
        String[] faceKeys = { "down", "up", "north", "south", "west", "east" };
        int face = side;
        if (side >= 2 && m.rotY != 0) face = rotateSide(side, -m.rotY);
        String key = faceKeys[face];
        String vp = m.vanillaParent == null ? "" : m.vanillaParent;
        String ref = null;
        if (m.textures.containsKey(key)) ref = "#" + key;
        else if (vp.contains("cube_all") || m.textures.containsKey("all")) ref = "#all";
        else if (vp.contains("cube_column") || vp.contains("log")) ref = side < 2 ? "#end" : "#side";
        else if (vp.contains("cube_bottom_top")) ref = side == 0 ? "#bottom" : side == 1 ? "#top" : "#side";
        else if (vp.contains("orientable")) ref = face == 2 ? "#front" : side < 2 ? "#top" : "#side";
        else if (vp.contains("cross")) ref = "#cross";
        else if (vp.contains("leaves")) ref = "#all";
        if (ref == null) ref = "#particle";
        String loc = m.texture(ref);
        IIcon i = loc == null ? null : info.icons.get(loc);
        return i;
    }

    private static int rotateSide(int side, int degrees) {
        int[] ring = { 2, 5, 3, 4 }; // north, east, south, west
        int idx = -1;
        for (int i = 0; i < 4; i++) if (ring[i] == side) idx = i;
        if (idx < 0) return side;
        int steps = ((degrees / 90) % 4 + 4) % 4;
        return ring[(idx + steps) % 4];
    }
}
