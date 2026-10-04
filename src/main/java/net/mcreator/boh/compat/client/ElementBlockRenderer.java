package net.mcreator.boh.compat.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import java.util.Map;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.block.BlockModels;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public final class ElementBlockRenderer implements ISimpleBlockRenderingHandler {
    private static final String[] FACES = new String[]{"down", "up", "north", "south", "west", "east"};
    private static final int[] SIDE = new int[]{0, 1, 2, 3, 4, 5};
    private static final float[] SHADE = new float[]{0.5F, 1.0F, 0.8F, 0.8F, 0.6F, 0.6F};
    private final int id;
    private static final int[][] OFFSET = new int[][]{{0, -1, 0}, {0, 1, 0}, {0, 0, -1}, {0, 0, 1}, {-1, 0, 0}, {1, 0, 0}};
    private static final float[][] NORMALS = new float[][]{
        {0.0F, -1.0F, 0.0F}, {0.0F, 1.0F, 0.0F}, {0.0F, 0.0F, -1.0F}, {0.0F, 0.0F, 1.0F}, {-1.0F, 0.0F, 0.0F}, {1.0F, 0.0F, 0.0F}
    };

    private ElementBlockRenderer(int id) {
        this.id = id;
    }

    public static void register() {
        int id = RenderingRegistry.getNextAvailableRenderId();
        BlockModels.elementRenderId = id;
        RenderingRegistry.registerBlockHandler(new ElementBlockRenderer(id));
    }

    public int getRenderId() {
        return this.id;
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks rb) {
        BlockState state = M.getBlockState(world, new BlockPos(x, y, z));
        BlockModels.Model m = BlockModels.modelFor(state);
        if (m == null) {
            m = BlockModels.fallbackModel(block);
        }

        if (m != null && !m.elements.isEmpty()) {
            Tessellator t = Tessellator.instance;
            int light = block.getMixedBrightnessForBlock(world, x, y, z);
            int color = block.colorMultiplier(world, x, y, z);
            float cr = (color >> 16 & 0xFF) / 255.0F;
            float cg = (color >> 8 & 0xFF) / 255.0F;
            float cb = (color & 0xFF) / 255.0F;
            IIcon override = rb.hasOverrideBlockTexture() ? rb.overrideBlockTexture : null;
            boolean drew = false;

            for (JsonObject el : m.elements) {
                drew |= renderElement(t, el, m, block, world, x, y, z, light, cr, cg, cb, override, false);
            }

            return drew;
        } else {
            return false;
        }
    }

    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks rb) {
        BlockModels.Model m = BlockModels.modelFor(BlockState.of(block, metadata));
        if (m == null) {
            m = BlockModels.fallbackModel(block);
        }

        if (m != null) {
            Tessellator t = Tessellator.instance;
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            t.startDrawingQuads();

            for (JsonObject el : m.elements) {
                renderElement(t, el, m, block, null, 0, 0, 0, -1, 1.0F, 1.0F, 1.0F, null, true);
            }

            t.draw();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        }
    }

    private static boolean renderElement(
        Tessellator t,
        JsonObject el,
        BlockModels.Model m,
        Block block,
        IBlockAccess world,
        int x,
        int y,
        int z,
        int light,
        float cr,
        float cg,
        float cb,
        IIcon override,
        boolean inventory
    ) {
        float[] from = vec(el.getAsJsonArray("from"));
        float[] to = vec(el.getAsJsonArray("to"));
        JsonObject faces = el.has("faces") ? el.getAsJsonObject("faces") : null;
        if (faces == null) {
            return false;
        } else {
            boolean shade = !el.has("shade") || el.get("shade").getAsBoolean();
            boolean drew = false;

            for (int f = 0; f < 6; f++) {
                if (faces.has(FACES[f])) {
                    JsonObject face = faces.getAsJsonObject(FACES[f]);
                    if (!inventory && face.has("cullface")) {
                        int cull = sideRotated(sideIndex(face.get("cullface").getAsString()), m);
                        if (cull >= 0) {
                            int[] o = OFFSET[cull];
                            if (!block.shouldSideBeRendered(world, x + o[0], y + o[1], z + o[2], cull)) {
                                continue;
                            }
                        }
                    }

                    IIcon icon = override;
                    if (override == null) {
                        String tex = face.has("texture") ? m.texture(face.get("texture").getAsString()) : null;
                        icon = tex == null ? null : BlockModels.iconFor(block, tex);
                        if (icon == null) {
                            icon = block.getIcon(SIDE[f], 0);
                        }
                    }

                    if (icon != null) {
                        float[] uv = face.has("uv") ? vec4(face.getAsJsonArray("uv")) : defaultUv(f, from, to);
                        int uvRot = face.has("rotation") ? face.get("rotation").getAsInt() : 0;
                        float[][] verts = corners(f, from, to);

                        for (float[] v : verts) {
                            if (el.has("rotation")) {
                                rotateElement(v, el.getAsJsonObject("rotation"));
                            }

                            rotateVariant(v, m.rotX, m.rotY);
                        }

                        float s = shade ? SHADE[shadeFaceAfterRotation(f, m)] : 1.0F;
                        if (inventory) {
                            float[] n = NORMALS[f];
                            t.setNormal(n[0], n[1], n[2]);
                            t.setColorOpaque_F(1.0F, 1.0F, 1.0F);
                        } else {
                            t.setBrightness(light);
                            t.setColorOpaque_F(cr * s, cg * s, cb * s);
                        }

                        for (int i = 0; i < 4; i++) {
                            int k = (i + uvRot / 90) % 4;
                            float u = k != 0 && k != 1 ? uv[2] : uv[0];
                            float vv = k != 0 && k != 3 ? uv[3] : uv[1];
                            float[] v = verts[i];
                            t.addVertexWithUV(x + v[0] / 16.0, y + v[1] / 16.0, z + v[2] / 16.0, icon.getInterpolatedU(u), icon.getInterpolatedV(vv));
                        }

                        drew = true;
                    }
                }
            }

            return drew;
        }
    }

    private static int sideIndex(String name) {
        for (int i = 0; i < 6; i++) {
            if (FACES[i].equals(name)) {
                return i;
            }
        }

        return name.equals("bottom") ? 0 : -1;
    }

    private static int sideRotated(int side, BlockModels.Model m) {
        if (side < 0) {
            return side;
        } else {
            float[] n = (float[])NORMALS[side].clone();
            float[] p = new float[]{n[0] * 8.0F + 8.0F, n[1] * 8.0F + 8.0F, n[2] * 8.0F + 8.0F};
            rotateVariant(p, m.rotX, m.rotY);
            float dx = p[0] - 8.0F;
            float dy = p[1] - 8.0F;
            float dz = p[2] - 8.0F;

            for (int i = 0; i < 6; i++) {
                float[] q = NORMALS[i];
                if (Math.abs(q[0] * 8.0F - dx) < 0.5 && Math.abs(q[1] * 8.0F - dy) < 0.5 && Math.abs(q[2] * 8.0F - dz) < 0.5) {
                    return i;
                }
            }

            return side;
        }
    }

    private static int shadeFaceAfterRotation(int side, BlockModels.Model m) {
        return sideRotated(side, m);
    }

    private static float[] vec(JsonArray a) {
        return new float[]{a.get(0).getAsFloat(), a.get(1).getAsFloat(), a.get(2).getAsFloat()};
    }

    private static float[] vec4(JsonArray a) {
        return new float[]{a.get(0).getAsFloat(), a.get(1).getAsFloat(), a.get(2).getAsFloat(), a.get(3).getAsFloat()};
    }

    private static float[] defaultUv(int f, float[] from, float[] to) {
        switch (f) {
            case 0:
                return new float[]{from[0], 16.0F - to[2], to[0], 16.0F - from[2]};
            case 1:
                return new float[]{from[0], from[2], to[0], to[2]};
            case 2:
                return new float[]{16.0F - to[0], 16.0F - to[1], 16.0F - from[0], 16.0F - from[1]};
            case 3:
                return new float[]{from[0], 16.0F - to[1], to[0], 16.0F - from[1]};
            case 4:
                return new float[]{from[2], 16.0F - to[1], to[2], 16.0F - from[1]};
            default:
                return new float[]{16.0F - to[2], 16.0F - to[1], 16.0F - from[2], 16.0F - from[1]};
        }
    }

    private static float[][] corners(int f, float[] a, float[] b) {
        float x0 = a[0];
        float y0 = a[1];
        float z0 = a[2];
        float x1 = b[0];
        float y1 = b[1];
        float z1 = b[2];
        switch (f) {
            case 0:
                return new float[][]{{x0, y0, z1}, {x0, y0, z0}, {x1, y0, z0}, {x1, y0, z1}};
            case 1:
                return new float[][]{{x0, y1, z0}, {x0, y1, z1}, {x1, y1, z1}, {x1, y1, z0}};
            case 2:
                return new float[][]{{x1, y1, z0}, {x1, y0, z0}, {x0, y0, z0}, {x0, y1, z0}};
            case 3:
                return new float[][]{{x0, y1, z1}, {x0, y0, z1}, {x1, y0, z1}, {x1, y1, z1}};
            case 4:
                return new float[][]{{x0, y1, z0}, {x0, y0, z0}, {x0, y0, z1}, {x0, y1, z1}};
            default:
                return new float[][]{{x1, y1, z1}, {x1, y0, z1}, {x1, y0, z0}, {x1, y1, z0}};
        }
    }

    private static void rotateElement(float[] v, JsonObject rot) {
        float[] o = vec(rot.getAsJsonArray("origin"));
        String axis = rot.get("axis").getAsString();
        double ang = Math.toRadians(rot.get("angle").getAsFloat());
        boolean rescale = rot.has("rescale") && rot.get("rescale").getAsBoolean();
        float px = v[0] - o[0];
        float py = v[1] - o[1];
        float pz = v[2] - o[2];
        float c = (float)Math.cos(ang);
        float s = (float)Math.sin(ang);
        float nx = px;
        float ny = py;
        float nz = pz;
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

        if (rescale && Math.abs(c) > 1.0E-4) {
            float k = 1.0F / Math.abs(c);
            if (!axis.equals("x")) {
                nx *= k;
            }

            if (!axis.equals("y")) {
                ny *= k;
            }

            if (!axis.equals("z")) {
                nz *= k;
            }
        }

        v[0] = nx + o[0];
        v[1] = ny + o[1];
        v[2] = nz + o[2];
    }

    private static void rotateVariant(float[] v, int rx, int ry) {
        if (rx != 0) {
            double a = Math.toRadians(-rx);
            float c = (float)Math.cos(a);
            float s = (float)Math.sin(a);
            float py = v[1] - 8.0F;
            float pz = v[2] - 8.0F;
            v[1] = py * c - pz * s + 8.0F;
            v[2] = py * s + pz * c + 8.0F;
        }

        if (ry != 0) {
            double a = Math.toRadians(-ry);
            float c = (float)Math.cos(a);
            float s = (float)Math.sin(a);
            float px = v[0] - 8.0F;
            float pz = v[2] - 8.0F;
            v[0] = px * c + pz * s + 8.0F;
            v[2] = -px * s + pz * c + 8.0F;
        }
    }

    static boolean unused(Map<?, ?> m) {
        return m == null;
    }
}
