package net.mcreator.boh.compat.block;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public final class BlockModels {
    private static final Map<Block, BlockModels.Info> INFO = new HashMap<>();
    public static int elementRenderId = -1;

    private BlockModels() {
    }

    static JsonObject read(String path) {
        try {
            JsonObject var2;
            try (InputStream in = BlockModels.class.getResourceAsStream(path)) {
                if (in == null) {
                    return null;
                }

                var2 = new JsonParser().parse(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
            }

            return var2;
        } catch (Exception var6) {
            return null;
        }
    }

    private static BlockModels.Model loadModel(String loc) {
        BlockModels.Model m = new BlockModels.Model();
        Map<String, String> tex = new LinkedHashMap<>();
        String cur = loc;
        String renderType = null;

        for (int depth = 0; depth < 10 && cur != null; depth++) {
            String ns = cur.contains(":") ? cur.substring(0, cur.indexOf(58)) : "minecraft";
            String path = cur.contains(":") ? cur.substring(cur.indexOf(58) + 1) : cur;
            if (!path.contains("/")) {
                path = "block/" + path;
            }

            if (ns.equals("minecraft")) {
                m.vanillaParent = path;
                break;
            }

            JsonObject obj = read("/assets/" + ns + "/models/" + path + ".json");
            if (obj == null) {
                break;
            }

            if (obj.has("textures")) {
                for (Entry<String, JsonElement> e : obj.getAsJsonObject("textures").entrySet()) {
                    tex.putIfAbsent(e.getKey(), e.getValue().getAsString());
                }
            }

            if (obj.has("texture_size")) {
                JsonArray ts = obj.getAsJsonArray("texture_size");
                m.textureWidth = ts.get(0).getAsFloat();
                m.textureHeight = ts.get(1).getAsFloat();
            }

            if (m.elements.isEmpty() && obj.has("elements")) {
                for (JsonElement el : obj.getAsJsonArray("elements")) {
                    m.elements.add(el.getAsJsonObject());
                }
            }

            if (renderType == null && obj.has("render_type")) {
                renderType = obj.get("render_type").getAsString();
            }

            String parent = obj.has("parent") ? obj.get("parent").getAsString() : null;
            if (parent != null && !parent.contains(":") && !parent.contains("/")) {
                parent = ns + ":custom/" + parent;
            }

            cur = parent;
        }

        m.textures.putAll(tex);
        if (!m.elements.isEmpty()) {
            m.kind = BlockModels.Kind.ELEMENTS;
        } else if (m.vanillaParent != null && m.vanillaParent.contains("cross")) {
            m.kind = BlockModels.Kind.CROSS;
        } else {
            m.kind = BlockModels.Kind.CUBE;
        }

        m.textures.put("__render_type", renderType == null ? "solid" : renderType);
        return m;
    }

    public static void configure(Block block, ResourceLocation id) {
        BlockModels.Info info = new BlockModels.Info();
        JsonObject bs = read("/assets/" + id.getResourceDomain() + "/blockstates/" + id.getResourcePath() + ".json");
        if (bs != null && bs.has("variants")) {
            for (Entry<String, JsonElement> v : bs.getAsJsonObject("variants").entrySet()) {
                JsonElement ve = v.getValue();
                JsonObject vo = ve.isJsonArray() ? ve.getAsJsonArray().get(0).getAsJsonObject() : ve.getAsJsonObject();
                BlockModels.Model base = loadModel(vo.get("model").getAsString());
                BlockModels.Model m = base.copyWithRotation(
                    vo.has("x") ? vo.get("x").getAsInt() : 0, vo.has("y") ? vo.get("y").getAsInt() : 0, vo.has("uvlock") && vo.get("uvlock").getAsBoolean()
                );
                Map<String, String> cond = new HashMap<>();
                if (!v.getKey().isEmpty()) {
                    for (String part : v.getKey().split(",")) {
                        String[] kv = part.split("=");
                        if (kv.length == 2) {
                            cond.put(kv[0], kv[1]);
                        }
                    }
                }

                info.variants.add(new Object[]{cond, m});
                if (info.fallback == null) {
                    info.fallback = m;
                }
            }
        } else if (bs != null && bs.has("multipart")) {
            for (JsonElement p : bs.getAsJsonArray("multipart")) {
                JsonObject apply = p.getAsJsonObject().get("apply").isJsonArray()
                    ? p.getAsJsonObject().getAsJsonArray("apply").get(0).getAsJsonObject()
                    : p.getAsJsonObject().getAsJsonObject("apply");
                BlockModels.Model mx = loadModel(apply.get("model").getAsString());
                info.variants.add(new Object[]{new HashMap(), mx});
                if (info.fallback == null) {
                    info.fallback = mx;
                }
            }
        }

        if (info.fallback == null) {
            info.fallback = loadModel(id.getResourceDomain() + ":block/" + id.getResourcePath());
            info.variants.add(new Object[]{new HashMap(), info.fallback});
        }

        String rt = info.fallback.textures.get("__render_type");
        info.pass = "translucent".equals(rt) ? 1 : 0;
        INFO.put(block, info);
        if (block instanceof BohBlock b) {
            if (info.fallback.kind == BlockModels.Kind.CROSS) {
                b.renderType = 1;
            } else if (info.fallback.kind == BlockModels.Kind.ELEMENTS) {
                b.renderType = -2;
            }
        }
    }

    public static void assignRenderIds() {
        for (Entry<Block, BlockModels.Info> e : INFO.entrySet()) {
            if (e.getKey() instanceof BohBlock && ((BohBlock)e.getKey()).renderType == -2) {
                ((BohBlock)e.getKey()).renderType = elementRenderId;
            }
        }
    }

    public static int renderPass(Block block) {
        BlockModels.Info i = INFO.get(block);
        return i == null ? 0 : i.pass;
    }

    public static boolean isCutout(Block block) {
        BlockModels.Info i = INFO.get(block);
        if (i == null) {
            return false;
        } else {
            String rt = i.fallback.textures.get("__render_type");
            return rt != null && rt.startsWith("cutout");
        }
    }

    public static BlockModels.Model modelFor(BlockState state) {
        BlockModels.Info info = INFO.get(state.getBlock());
        if (info == null) {
            return null;
        } else {
            for (Object[] v : info.variants) {
                Map<String, String> cond = (Map<String, String>)v[0];
                boolean ok = true;

                for (Entry<String, String> c : cond.entrySet()) {
                    Property p = state.definition().getProperty(c.getKey());
                    if (p != null && !p.getName(state.getValue(p)).equals(c.getValue())) {
                        ok = false;
                        break;
                    }
                }

                if (ok) {
                    return (BlockModels.Model)v[1];
                }
            }

            return info.fallback;
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerIcons(Block block, IIconRegister reg) {
        BlockModels.Info info = INFO.get(block);
        if (info != null) {
            info.icons = new HashMap<>();

            for (Object[] v : info.variants) {
                BlockModels.Model m = (BlockModels.Model)v[1];

                for (Entry<String, String> t : m.textures.entrySet()) {
                    if (!t.getKey().startsWith("__")) {
                        String loc = m.texture(t.getValue());
                        if (loc != null && !loc.startsWith("#") && !info.icons.containsKey(loc)) {
                            info.icons.put(loc, reg.registerIcon(loc.contains(":") ? loc : "minecraft:" + loc));
                        }
                    }
                }
            }

            if (block instanceof BohBlock) {
                String p = info.fallback.texture("#particle");
                if (p == null) {
                    p = info.fallback.texture("#all");
                }

                IIcon icon = p == null ? null : info.icons.get(p);
                if (icon == null && !info.icons.isEmpty()) {
                    icon = info.icons.values().iterator().next();
                }

                if (icon != null) {
                    ((BohBlock)block).setBlockIcon(icon);
                }
            }
        }
    }

    public static BlockModels.Model fallbackModel(Block block) {
        BlockModels.Info info = INFO.get(block);
        return info == null ? null : info.fallback;
    }

    public static String textureOf(Block block, String key) {
        BlockModels.Info info = INFO.get(block);
        return info != null && info.fallback != null ? info.fallback.texture("#" + key) : null;
    }

    @SideOnly(Side.CLIENT)
    public static IIcon iconFor(Block block, String textureLoc) {
        BlockModels.Info info = INFO.get(block);
        return info != null && info.icons != null && textureLoc != null ? info.icons.get(textureLoc) : null;
    }

    @SideOnly(Side.CLIENT)
    public static IIcon icon(Block block, int side, int meta) {
        BlockModels.Info info = INFO.get(block);
        if (info != null && info.icons != null) {
            BlockModels.Model m = block instanceof BohBlock ? modelFor(BlockState.of(block, meta)) : info.fallback;
            if (m == null) {
                return null;
            } else {
                String[] faceKeys = new String[]{"down", "up", "north", "south", "west", "east"};
                int face = side;
                if (side >= 2 && m.rotY != 0) {
                    face = rotateSide(side, -m.rotY);
                }

                String key = faceKeys[face];
                String vp = m.vanillaParent == null ? "" : m.vanillaParent;
                String ref = null;
                if (m.textures.containsKey(key)) {
                    ref = "#" + key;
                } else if (vp.contains("cube_all") || m.textures.containsKey("all")) {
                    ref = "#all";
                } else if (vp.contains("cube_column") || vp.contains("log")) {
                    ref = side < 2 ? "#end" : "#side";
                } else if (vp.contains("cube_bottom_top")) {
                    ref = side == 0 ? "#bottom" : (side == 1 ? "#top" : "#side");
                } else if (vp.contains("orientable")) {
                    ref = face == 2 ? "#front" : (side < 2 ? "#top" : "#side");
                } else if (vp.contains("cross")) {
                    ref = "#cross";
                } else if (vp.contains("leaves")) {
                    ref = "#all";
                }

                if (ref == null) {
                    ref = "#particle";
                }

                String loc = m.texture(ref);
                return loc == null ? null : info.icons.get(loc);
            }
        } else {
            return null;
        }
    }

    private static int rotateSide(int side, int degrees) {
        int[] ring = new int[]{2, 5, 3, 4};
        int idx = -1;

        for (int i = 0; i < 4; i++) {
            if (ring[i] == side) {
                idx = i;
            }
        }

        if (idx < 0) {
            return side;
        } else {
            int steps = (degrees / 90 % 4 + 4) % 4;
            return ring[(idx + steps) % 4];
        }
    }

    static final class Info {
        final List<Object[]> variants = new ArrayList<>();
        BlockModels.Model fallback;
        int pass;
        @SideOnly(Side.CLIENT)
        Map<String, IIcon> icons;
    }

    public static enum Kind {
        CUBE,
        CROSS,
        ELEMENTS;
    }

    public static final class Model {
        public final Map<String, String> textures = new HashMap<>();
        public final List<JsonObject> elements = new ArrayList<>();
        public BlockModels.Kind kind = BlockModels.Kind.CUBE;
        public String vanillaParent;
        public int rotX;
        public int rotY;
        public boolean uvlock;
        public float textureWidth = 16.0F;
        public float textureHeight = 16.0F;

        BlockModels.Model copyWithRotation(int x, int y, boolean uvlock) {
            BlockModels.Model m = new BlockModels.Model();
            m.textures.putAll(this.textures);
            m.elements.addAll(this.elements);
            m.kind = this.kind;
            m.vanillaParent = this.vanillaParent;
            m.rotX = x;
            m.rotY = y;
            m.uvlock = uvlock;
            m.textureWidth = this.textureWidth;
            m.textureHeight = this.textureHeight;
            return m;
        }

        public String texture(String ref) {
            String t = ref;

            for (int i = 0; i < 8 && t != null && t.startsWith("#"); i++) {
                t = this.textures.get(t.substring(1));
            }

            return t;
        }
    }
}
