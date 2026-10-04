package net.mcreator.boh.compat.client;

import java.nio.FloatBuffer;
import java.util.Arrays;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class TessellatorConsumer implements VertexConsumer {
    public static float shaderR = 1.0F;
    public static float shaderG = 1.0F;
    public static float shaderB = 1.0F;
    public static float shaderA = 1.0F;
    private double x;
    private double y;
    private double z;
    private float r = 1.0F;
    private float g = 1.0F;
    private float b = 1.0F;
    private float a = 1.0F;
    private float u;
    private float v;
    private float nx;
    private float ny = 1.0F;
    private float nz;
    private boolean hasNormal;
    private int overlay = OverlayTexture.NO_OVERLAY;
    private int light = -1;
    private boolean fullBright;
    private boolean drawing;
    private boolean sorting;
    private float[] buf = new float[3328];
    private int count;
    private int glMode;
    private static final FloatBuffer MV = BufferUtils.createFloatBuffer(16);

    public void begin(int glMode, boolean fullBright) {
        this.begin(glMode, fullBright, false);
    }

    public void begin(int glMode, boolean fullBright, boolean sort) {
        this.fullBright = fullBright;
        this.glMode = glMode;
        this.sorting = sort && glMode == 7;
        this.count = 0;
        Tessellator.instance.startDrawing(glMode);
        this.drawing = true;
    }

    public void end() {
        if (this.drawing) {
            this.drawing = false;
            if (this.sorting && this.count >= 4) {
                this.flushSorted();
            }

            Tessellator.instance.draw();
        }
    }

    private void flushSorted() {
        MV.clear();
        GL11.glGetFloat(2982, MV);
        float[] m = new float[16];
        MV.get(m);
        int quads = this.count / 4;
        Integer[] order = new Integer[quads];
        float[] dist = new float[quads];

        for (int q = 0; q < quads; q++) {
            float cx = 0.0F;
            float cy = 0.0F;
            float cz = 0.0F;

            for (int k = 0; k < 4; k++) {
                int o = (q * 4 + k) * 13;
                cx += this.buf[o];
                cy += this.buf[o + 1];
                cz += this.buf[o + 2];
            }

            cx /= 4.0F;
            cy /= 4.0F;
            cz /= 4.0F;
            float ex = m[0] * cx + m[4] * cy + m[8] * cz + m[12];
            float ey = m[1] * cx + m[5] * cy + m[9] * cz + m[13];
            float ez = m[2] * cx + m[6] * cy + m[10] * cz + m[14];
            dist[q] = ex * ex + ey * ey + ez * ez;
            order[q] = q;
        }

        Arrays.sort(order, (p, qx) -> Float.compare(dist[qx], dist[p]));
        Tessellator t = Tessellator.instance;
        Integer[] var14 = order;
        int var16 = order.length;

        for (int var18 = 0; var18 < var16; var18++) {
            int q = var14[var18];

            for (int k = 0; k < 4; k++) {
                int o = (q * 4 + k) * 13;
                t.setColorRGBA_F(this.buf[o + 5], this.buf[o + 6], this.buf[o + 7], this.buf[o + 8]);
                if (this.buf[o + 12] != 0.0F) {
                    t.setNormal(this.buf[o + 9], this.buf[o + 10], this.buf[o + 11]);
                }

                if (this.fullBright) {
                    t.setBrightness(15728880);
                }

                t.addVertexWithUV(this.buf[o], this.buf[o + 1], this.buf[o + 2], this.buf[o + 3], this.buf[o + 4]);
            }
        }

        this.count = 0;
    }

    public boolean isDrawing() {
        return this.drawing;
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
        this.nx = x;
        this.ny = y;
        this.nz = z;
        this.hasNormal = true;
        return this;
    }

    @Override
    public void endVertex() {
        Tessellator t = Tessellator.instance;
        float cr = this.r * shaderR;
        float cg = this.g * shaderG;
        float cb = this.b * shaderB;
        float ca = this.a * shaderA;
        if (OverlayTexture.isHurt(this.overlay)) {
            cg *= 0.6F;
            cb *= 0.6F;
        }

        float white = OverlayTexture.white(this.overlay);
        if (white > 0.0F) {
            cr += (1.0F - cr) * white;
            cg += (1.0F - cg) * white;
            cb += (1.0F - cb) * white;
        }

        if (this.sorting) {
            if ((this.count + 1) * 13 > this.buf.length) {
                this.buf = Arrays.copyOf(this.buf, this.buf.length * 2);
            }

            int o = this.count * 13;
            this.buf[o] = (float)this.x;
            this.buf[o + 1] = (float)this.y;
            this.buf[o + 2] = (float)this.z;
            this.buf[o + 3] = this.u;
            this.buf[o + 4] = this.v;
            this.buf[o + 5] = cr;
            this.buf[o + 6] = cg;
            this.buf[o + 7] = cb;
            this.buf[o + 8] = ca;
            this.buf[o + 9] = this.nx;
            this.buf[o + 10] = this.ny;
            this.buf[o + 11] = this.nz;
            this.buf[o + 12] = this.hasNormal ? 1.0F : 0.0F;
            this.count++;
            this.overlay = OverlayTexture.NO_OVERLAY;
            this.light = -1;
            this.hasNormal = false;
        } else {
            t.setColorRGBA_F(cr, cg, cb, ca);
            if (this.hasNormal) {
                t.setNormal(this.nx, this.ny, this.nz);
            }

            if (this.fullBright) {
                t.setBrightness(15728880);
            }

            t.addVertexWithUV(this.x, this.y, this.z, this.u, this.v);
            this.overlay = OverlayTexture.NO_OVERLAY;
            this.light = -1;
            this.hasNormal = false;
        }
    }
}
