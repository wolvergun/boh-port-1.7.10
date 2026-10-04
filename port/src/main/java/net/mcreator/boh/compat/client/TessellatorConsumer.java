package net.mcreator.boh.compat.client;

import net.minecraft.client.renderer.Tessellator;

/** Collects builder-style vertices and forwards them to the 1.7.10 Tessellator. */
public class TessellatorConsumer implements VertexConsumer {

    /** Global tint applied on top of vertex colors (RenderSystem.setShaderColor). */
    public static float shaderR = 1, shaderG = 1, shaderB = 1, shaderA = 1;

    private double x, y, z;
    private float r = 1, g = 1, b = 1, a = 1;
    private float u, v;
    private float nx, ny = 1, nz;
    private boolean hasNormal;
    private int overlay = OverlayTexture.NO_OVERLAY;
    private int light = -1;
    private boolean fullBright;
    private boolean drawing;

    // translucent batches are buffered and sorted back to front like 1.20's sortOnUpload
    private boolean sorting;
    private float[] buf = new float[13 * 256];
    private int count;
    private int glMode;

    public void begin(int glMode, boolean fullBright) {
        begin(glMode, fullBright, false);
    }

    public void begin(int glMode, boolean fullBright, boolean sort) {
        this.fullBright = fullBright;
        this.glMode = glMode;
        this.sorting = sort && glMode == org.lwjgl.opengl.GL11.GL_QUADS;
        count = 0;
        Tessellator.instance.startDrawing(glMode);
        drawing = true;
    }

    public void end() {
        if (!drawing) return;
        drawing = false;
        if (sorting && count >= 4) flushSorted();
        Tessellator.instance.draw();
    }

    private static final java.nio.FloatBuffer MV = org.lwjgl.BufferUtils.createFloatBuffer(16);

    private void flushSorted() {
        MV.clear();
        org.lwjgl.opengl.GL11.glGetFloat(org.lwjgl.opengl.GL11.GL_MODELVIEW_MATRIX, MV);
        float[] m = new float[16];
        MV.get(m);
        int quads = count / 4;
        Integer[] order = new Integer[quads];
        float[] dist = new float[quads];
        for (int q = 0; q < quads; q++) {
            float cx = 0, cy = 0, cz = 0;
            for (int k = 0; k < 4; k++) {
                int o = (q * 4 + k) * 13;
                cx += buf[o];
                cy += buf[o + 1];
                cz += buf[o + 2];
            }
            cx /= 4;
            cy /= 4;
            cz /= 4;
            float ex = m[0] * cx + m[4] * cy + m[8] * cz + m[12];
            float ey = m[1] * cx + m[5] * cy + m[9] * cz + m[13];
            float ez = m[2] * cx + m[6] * cy + m[10] * cz + m[14];
            dist[q] = ex * ex + ey * ey + ez * ez;
            order[q] = q;
        }
        java.util.Arrays.sort(order, (p, q) -> Float.compare(dist[q], dist[p]));
        Tessellator t = Tessellator.instance;
        for (int q : order) {
            for (int k = 0; k < 4; k++) {
                int o = (q * 4 + k) * 13;
                t.setColorRGBA_F(buf[o + 5], buf[o + 6], buf[o + 7], buf[o + 8]);
                if (buf[o + 12] != 0) t.setNormal(buf[o + 9], buf[o + 10], buf[o + 11]);
                if (fullBright) t.setBrightness(LightTexture.FULL_BRIGHT);
                t.addVertexWithUV(buf[o], buf[o + 1], buf[o + 2], buf[o + 3], buf[o + 4]);
            }
        }
        count = 0;
    }

    public boolean isDrawing() {
        return drawing;
    }

    @Override
    public VertexConsumer vertex(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    @Override
    public VertexConsumer color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        return this;
    }

    @Override
    public VertexConsumer uv(float u, float v) {
        this.u = u;
        this.v = v;
        return this;
    }

    @Override
    public VertexConsumer overlayCoords(int overlay) {
        this.overlay = overlay;
        return this;
    }

    @Override
    public VertexConsumer uv2(int packedLight) {
        this.light = packedLight;
        return this;
    }

    @Override
    public VertexConsumer normal(float x, float y, float z) {
        nx = x;
        ny = y;
        nz = z;
        hasNormal = true;
        return this;
    }

    @Override
    public void endVertex() {
        Tessellator t = Tessellator.instance;
        float cr = r * shaderR, cg = g * shaderG, cb = b * shaderB, ca = a * shaderA;
        if (OverlayTexture.isHurt(overlay)) {
            cg *= 0.6f;
            cb *= 0.6f;
        }
        float white = OverlayTexture.white(overlay);
        if (white > 0) {
            cr += (1 - cr) * white;
            cg += (1 - cg) * white;
            cb += (1 - cb) * white;
        }
        if (sorting) {
            if ((count + 1) * 13 > buf.length) buf = java.util.Arrays.copyOf(buf, buf.length * 2);
            int o = count * 13;
            buf[o] = (float) x;
            buf[o + 1] = (float) y;
            buf[o + 2] = (float) z;
            buf[o + 3] = u;
            buf[o + 4] = v;
            buf[o + 5] = cr;
            buf[o + 6] = cg;
            buf[o + 7] = cb;
            buf[o + 8] = ca;
            buf[o + 9] = nx;
            buf[o + 10] = ny;
            buf[o + 11] = nz;
            buf[o + 12] = hasNormal ? 1 : 0;
            count++;
            overlay = OverlayTexture.NO_OVERLAY;
            light = -1;
            hasNormal = false;
            return;
        }
        t.setColorRGBA_F(cr, cg, cb, ca);
        if (hasNormal) t.setNormal(nx, ny, nz);
        if (fullBright) t.setBrightness(LightTexture.FULL_BRIGHT);
        t.addVertexWithUV(x, y, z, u, v);
        overlay = OverlayTexture.NO_OVERLAY;
        light = -1;
        hasNormal = false;
    }
}
