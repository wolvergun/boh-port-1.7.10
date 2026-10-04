package net.mcreator.boh.compat.client;

import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.block.BlockModels;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

import org.lwjgl.opengl.GL11;

/**
 * Renders 1.20 JSON block models made of "elements" (cuboids with per-face uv, element rotation, variant x/y
 * rotation) through the 1.7.10 Tessellator, the way FaceBakery bakes them.
 */
public final class ElementBlockRenderer implements ISimpleBlockRenderingHandler {

    private static final String[] FACES = { "down", "up", "north", "south", "west", "east" };
    /** 1.7.10 side index for each face name above */
    private static final int[] SIDE = { 0, 1, 2, 3, 4, 5 };
    private static final float[] SHADE = { 0.5F, 1.0F, 0.8F, 0.8F, 0.6F, 0.6F };

    private final int id;

    private ElementBlockRenderer(int id) {
        this.id = id;
    }

    public static void register() {
        int id = RenderingRegistry.getNextAvailableRenderId();
        BlockModels.elementRenderId = id;
        RenderingRegistry.registerBlockHandler(new ElementBlockRenderer(id));
    }

    @Override
    public int getRenderId() {
        return id;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks rb) {
        BlockState state = M.getBlockState(world, new BlockPos(x, y, z));
        BlockModels.Model m = BlockModels.modelFor(state);
        if (m == null) m = BlockModels.fallbackModel(block);
        if (m == null || m.elements.isEmpty()) return false;
        Tessellator t = Tessellator.instance;
        int light = block.getMixedBrightnessForBlock(world, x, y, z);
        int color = block.colorMultiplier(world, x, y, z);
        float cr = (color >> 16 & 255) / 255F, cg = (color >> 8 & 255) / 255F, cb = (color & 255) / 255F;
        IIcon override = rb.hasOverrideBlockTexture() ? rb.overrideBlockTexture : null;
        boolean drew = false;
        for (JsonObject el : m.elements) {
            drew |= renderElement(t, el, m, block, world, x, y, z, light, cr, cg, cb, override, false);
        }
        return drew;
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks rb) {
        BlockModels.Model m = BlockModels.modelFor(BlockState.of(block, metadata));
        if (m == null) m = BlockModels.fallbackModel(block);
        if (m == null) return;
        Tessellator t = Tessellator.instance;
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        t.startDrawingQuads();
        for (JsonObject el : m.elements) renderElement(t, el, m, block, null, 0, 0, 0, -1, 1, 1, 1, null, true);
        t.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    private static boolean renderElement(Tessellator t, JsonObject el, BlockModels.Model m, Block block, IBlockAccess world, int x, int y, int z,
        int light, float cr, float cg, float cb, IIcon override, boolean inventory) {
        float[] from = vec(el.getAsJsonArray("from")), to = vec(el.getAsJsonArray("to"));
        JsonObject faces = el.has("faces") ? el.getAsJsonObject("faces") : null;
        if (faces == null) return false;
        boolean shade = !el.has("shade") || el.get("shade").getAsBoolean();
        boolean drew = false;
        for (int f = 0; f < 6; f++) {
            if (!faces.has(FACES[f])) continue;
            JsonObject face = faces.getAsJsonObject(FACES[f]);
            if (!inventory && face.has("cullface")) {
                int cull = sideRotated(sideIndex(face.get("cullface").getAsString()), m);
                if (cull >= 0) {
                    int[] o = OFFSET[cull];
                    if (!block.shouldSideBeRendered(world, x + o[0], y + o[1], z + o[2], cull)) continue;
                }
            }
            IIcon icon = override;
            if (icon == null) {
                String tex = face.has("texture") ? m.texture(face.get("texture").getAsString()) : null;
                icon = tex == null ? null : BlockModels.iconFor(block, tex);
                if (icon == null) icon = block.getIcon(SIDE[f], 0);
            }
            if (icon == null) continue;
            float[] uv = face.has("uv") ? vec4(face.getAsJsonArray("uv")) : defaultUv(f, from, to);
            int uvRot = face.has("rotation") ? face.get("rotation").getAsInt() : 0;
            float[][] verts = corners(f, from, to);
            for (float[] v : verts) {
                if (el.has("rotation")) rotateElement(v, el.getAsJsonObject("rotation"));
                rotateVariant(v, m.rotX, m.rotY);
            }
            float s = shade ? SHADE[shadeFaceAfterRotation(f, m)] : 1.0F;
            if (inventory) {
                float[] n = NORMALS[f];
                t.setNormal(n[0], n[1], n[2]);
                t.setColorOpaque_F(1, 1, 1);
            } else {
                t.setBrightness(light);
                t.setColorOpaque_F(cr * s, cg * s, cb * s);
            }
            for (int i = 0; i < 4; i++) {
                int k = (i + uvRot / 90) % 4;
                float u = (k == 0 || k == 1) ? uv[0] : uv[2];
                float vv = (k == 0 || k == 3) ? uv[1] : uv[3];
                float[] v = verts[i];
                t.addVertexWithUV(x + v[0] / 16.0, y + v[1] / 16.0, z + v[2] / 16.0, icon.getInterpolatedU(u), icon.getInterpolatedV(vv));
            }
            drew = true;
        }
        return drew;
    }

    private static final int[][] OFFSET = { { 0, -1, 0 }, { 0, 1, 0 }, { 0, 0, -1 }, { 0, 0, 1 }, { -1, 0, 0 }, { 1, 0, 0 } };
    private static final float[][] NORMALS = { { 0, -1, 0 }, { 0, 1, 0 }, { 0, 0, -1 }, { 0, 0, 1 }, { -1, 0, 0 }, { 1, 0, 0 } };

    private static int sideIndex(String name) {
        for (int i = 0; i < 6; i++) if (FACES[i].equals(name)) return i;
        if (name.equals("bottom")) return 0;
        return -1;
    }

    /** face direction after the variant rotation (for culling and shading) */
    private static int sideRotated(int side, BlockModels.Model m) {
        if (side < 0) return side;
        float[] n = NORMALS[side].clone();
        float[] p = { n[0] * 8 + 8, n[1] * 8 + 8, n[2] * 8 + 8 };
        rotateVariant(p, m.rotX, m.rotY);
        float dx = p[0] - 8, dy = p[1] - 8, dz = p[2] - 8;
        for (int i = 0; i < 6; i++) {
            float[] q = NORMALS[i];
            if (Math.abs(q[0] * 8 - dx) < 0.5 && Math.abs(q[1] * 8 - dy) < 0.5 && Math.abs(q[2] * 8 - dz) < 0.5) return i;
        }
        return side;
    }

    private static int shadeFaceAfterRotation(int side, BlockModels.Model m) {
        return sideRotated(side, m);
    }

    private static float[] vec(JsonArray a) {
        return new float[] { a.get(0).getAsFloat(), a.get(1).getAsFloat(), a.get(2).getAsFloat() };
    }

    private static float[] vec4(JsonArray a) {
        return new float[] { a.get(0).getAsFloat(), a.get(1).getAsFloat(), a.get(2).getAsFloat(), a.get(3).getAsFloat() };
    }

    private static float[] defaultUv(int f, float[] from, float[] to) {
        switch (f) {
            case 0:
                return new float[] { from[0], 16 - to[2], to[0], 16 - from[2] };
            case 1:
                return new float[] { from[0], from[2], to[0], to[2] };
            case 2:
                return new float[] { 16 - to[0], 16 - to[1], 16 - from[0], 16 - from[1] };
            case 3:
                return new float[] { from[0], 16 - to[1], to[0], 16 - from[1] };
            case 4:
                return new float[] { from[2], 16 - to[1], to[2], 16 - from[1] };
            default:
                return new float[] { 16 - to[2], 16 - to[1], 16 - from[2], 16 - from[1] };
        }
    }

    /** Vertex positions in FaceBakery's FaceInfo order. */
    private static float[][] corners(int f, float[] a, float[] b) {
        float x0 = a[0], y0 = a[1], z0 = a[2], x1 = b[0], y1 = b[1], z1 = b[2];
        switch (f) {
            case 0:
                return new float[][] { { x0, y0, z1 }, { x0, y0, z0 }, { x1, y0, z0 }, { x1, y0, z1 } };
            case 1:
                return new float[][] { { x0, y1, z0 }, { x0, y1, z1 }, { x1, y1, z1 }, { x1, y1, z0 } };
            case 2:
                return new float[][] { { x1, y1, z0 }, { x1, y0, z0 }, { x0, y0, z0 }, { x0, y1, z0 } };
            case 3:
                return new float[][] { { x0, y1, z1 }, { x0, y0, z1 }, { x1, y0, z1 }, { x1, y1, z1 } };
            case 4:
                return new float[][] { { x0, y1, z0 }, { x0, y0, z0 }, { x0, y0, z1 }, { x0, y1, z1 } };
            default:
                return new float[][] { { x1, y1, z1 }, { x1, y0, z1 }, { x1, y0, z0 }, { x1, y1, z0 } };
        }
    }

    private static void rotateElement(float[] v, JsonObject rot) {
        float[] o = vec(rot.getAsJsonArray("origin"));
        String axis = rot.get("axis").getAsString();
        double ang = Math.toRadians(rot.get("angle").getAsFloat());
        boolean rescale = rot.has("rescale") && rot.get("rescale").getAsBoolean();
        float px = v[0] - o[0], py = v[1] - o[1], pz = v[2] - o[2];
        float c = (float) Math.cos(ang), s = (float) Math.sin(ang);
        float nx = px, ny = py, nz = pz;
        switch (axis) {
            case "x":
                ny = py * c - pz * s;
                nz = py * s + pz * c;
                break;
            case "y":
                nx = px * c + pz * s;
                nz = -px * s + pz * c;
                break;
            default:
                nx = px * c - py * s;
                ny = px * s + py * c;
        }
        if (rescale && Math.abs(c) > 1e-4) {
            float k = 1 / Math.abs(c);
            if (!axis.equals("x")) nx *= k;
            if (!axis.equals("y")) ny *= k;
            if (!axis.equals("z")) nz *= k;
        }
        v[0] = nx + o[0];
        v[1] = ny + o[1];
        v[2] = nz + o[2];
    }

    /** Blockstate variant rotation: x then y, both clockwise in degrees, around the block centre. */
    private static void rotateVariant(float[] v, int rx, int ry) {
        if (rx != 0) {
            double a = Math.toRadians(-rx);
            float c = (float) Math.cos(a), s = (float) Math.sin(a);
            float py = v[1] - 8, pz = v[2] - 8;
            v[1] = py * c - pz * s + 8;
            v[2] = py * s + pz * c + 8;
        }
        if (ry != 0) {
            double a = Math.toRadians(-ry);
            float c = (float) Math.cos(a), s = (float) Math.sin(a);
            float px = v[0] - 8, pz = v[2] - 8;
            v[0] = px * c + pz * s + 8;
            v[2] = -px * s + pz * c + 8;
        }
    }

    static boolean unused(Map<?, ?> m) {
        return m == null;
    }
}
