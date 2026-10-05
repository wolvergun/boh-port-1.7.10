package net.mcreator.boh.compat.client;

import net.mcreator.boh.compat.entity.BohPainting;
import net.mcreator.boh.compat.mc.world.entity.decoration.PaintingVariant;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * Draws a {@link BohPainting} like RenderPainting draws vanilla ones: the variant's own texture on the front, the
 * vanilla painting back and frame edges around it, lit per 16x16 block.
 */
public final class BohPaintingRender extends Render {

    private static final ResourceLocation VANILLA = new ResourceLocation("textures/painting/paintings_kristoffer_zetterstrand.png");

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partial) {
        BohPainting p = (BohPainting) entity;
        PaintingVariant v = p.getVariant();
        if (v == null || v.getId() == null) return;
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glRotatef(yaw, 0.0F, 1.0F, 0.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        float s = 0.0625F;
        GL11.glScalef(s, s, s);
        int w = v.getWidth(), h = v.getHeight();
        float x0 = -w / 2.0F, y0 = -h / 2.0F, d = 0.5F;
        ResourceLocation front = new ResourceLocation(v.getId().getResourceDomain(), "textures/painting/" + v.getId().getResourcePath() + ".png");
        for (int pass = 0; pass < 2; pass++) {
            bindTexture(pass == 0 ? front : VANILLA);
            for (int i = 0; i < w / 16; i++) for (int j = 0; j < h / 16; j++) {
                float ax = x0 + (i + 1) * 16, bx = x0 + i * 16, ay = y0 + (j + 1) * 16, by = y0 + j * 16;
                light(p, (ax + bx) / 2.0F, (ay + by) / 2.0F);
                Tessellator t = Tessellator.instance;
                t.startDrawingQuads();
                if (pass == 0) {
                    // front: the matching 16x16 part of the variant's texture
                    float u0 = (float) (w - i * 16) / w, u1 = (float) (w - (i + 1) * 16) / w;
                    float v0 = (float) (h - j * 16) / h, v1 = (float) (h - (j + 1) * 16) / h;
                    t.setNormal(0.0F, 0.0F, -1.0F);
                    t.addVertexWithUV(ax, by, -d, u1, v0);
                    t.addVertexWithUV(bx, by, -d, u0, v0);
                    t.addVertexWithUV(bx, ay, -d, u0, v1);
                    t.addVertexWithUV(ax, ay, -d, u1, v1);
                } else {
                    // back and edges from the vanilla painting sheet (the wooden back at 12..13, 0..1 tiles)
                    float bu0 = 0.75F, bu1 = 0.8125F, bv0 = 0.0F, bv1 = 0.0625F, eu0 = 0.75F, eu1 = 0.8125F, ev0 = 0.001953125F, ev1 = 0.001953125F;
                    t.setNormal(0.0F, 0.0F, 1.0F);
                    t.addVertexWithUV(ax, ay, d, bu0, bv0);
                    t.addVertexWithUV(bx, ay, d, bu1, bv0);
                    t.addVertexWithUV(bx, by, d, bu1, bv1);
                    t.addVertexWithUV(ax, by, d, bu0, bv1);
                    t.setNormal(0.0F, 1.0F, 0.0F);
                    t.addVertexWithUV(ax, ay, -d, eu0, ev0);
                    t.addVertexWithUV(bx, ay, -d, eu1, ev0);
                    t.addVertexWithUV(bx, ay, d, eu1, ev1);
                    t.addVertexWithUV(ax, ay, d, eu0, ev1);
                    t.setNormal(0.0F, -1.0F, 0.0F);
                    t.addVertexWithUV(ax, by, d, eu0, ev0);
                    t.addVertexWithUV(bx, by, d, eu1, ev0);
                    t.addVertexWithUV(bx, by, -d, eu1, ev1);
                    t.addVertexWithUV(ax, by, -d, eu0, ev1);
                    t.setNormal(-1.0F, 0.0F, 0.0F);
                    t.addVertexWithUV(ax, ay, d, eu1, ev0);
                    t.addVertexWithUV(ax, by, d, eu1, ev1);
                    t.addVertexWithUV(ax, by, -d, eu0, ev1);
                    t.addVertexWithUV(ax, ay, -d, eu0, ev0);
                    t.setNormal(1.0F, 0.0F, 0.0F);
                    t.addVertexWithUV(bx, ay, -d, eu1, ev0);
                    t.addVertexWithUV(bx, by, -d, eu1, ev1);
                    t.addVertexWithUV(bx, by, d, eu0, ev1);
                    t.addVertexWithUV(bx, ay, d, eu0, ev0);
                }
                t.draw();
            }
        }
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    /** RenderPainting.func_77008_a: the lightmap of the block behind this 16x16 part. */
    private void light(BohPainting p, float px, float py) {
        int bx = MathHelper.floor_double(p.posX), by = MathHelper.floor_double(p.posY + py / 16.0F), bz = MathHelper.floor_double(p.posZ);
        if (p.hangingDirection == 2) bx = MathHelper.floor_double(p.posX + px / 16.0F);
        if (p.hangingDirection == 1) bz = MathHelper.floor_double(p.posZ - px / 16.0F);
        if (p.hangingDirection == 0) bx = MathHelper.floor_double(p.posX - px / 16.0F);
        if (p.hangingDirection == 3) bz = MathHelper.floor_double(p.posZ + px / 16.0F);
        int l = renderManager.worldObj.getLightBrightnessForSkyBlocks(bx, by, bz, 0);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, l % 65536, l / 65536);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity e) {
        return VANILLA;
    }
}
