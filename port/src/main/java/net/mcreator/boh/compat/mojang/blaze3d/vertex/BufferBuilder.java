package net.mcreator.boh.compat.mojang.blaze3d.vertex;

import java.util.Arrays;

import net.mcreator.boh.compat.client.VertexConsumer;

/** 1.20 BufferBuilder recording position/uv/color vertices for immediate GL drawing. */
public class BufferBuilder implements VertexConsumer {

    static final int STRIDE = 9; // x y z u v r g b a

    private Mode mode = Mode.QUADS;
    private float[] data = new float[STRIDE * 256];
    private int count;
    private boolean building, hasUv, hasColor;
    private final float[] cur = new float[STRIDE];

    public void begin(Mode mode, DefaultVertexFormat format) {
        this.mode = mode;
        count = 0;
        building = true;
        hasUv = false;
        hasColor = false;
        resetCurrent();
    }

    public boolean building() {
        return building;
    }

    private void resetCurrent() {
        Arrays.fill(cur, 0);
        cur[5] = cur[6] = cur[7] = cur[8] = 1;
    }

    @Override
    public VertexConsumer vertex(double x, double y, double z) {
        cur[0] = (float) x;
        cur[1] = (float) y;
        cur[2] = (float) z;
        return this;
    }

    @Override
    public VertexConsumer color(float r, float g, float b, float a) {
        hasColor = true;
        cur[5] = r;
        cur[6] = g;
        cur[7] = b;
        cur[8] = a;
        return this;
    }

    @Override
    public VertexConsumer uv(float u, float v) {
        hasUv = true;
        cur[3] = u;
        cur[4] = v;
        return this;
    }

    @Override
    public VertexConsumer overlayCoords(int overlay) {
        return this;
    }

    @Override
    public VertexConsumer uv2(int light) {
        return this;
    }

    @Override
    public VertexConsumer normal(float x, float y, float z) {
        return this;
    }

    @Override
    public void endVertex() {
        if ((count + 1) * STRIDE > data.length) data = Arrays.copyOf(data, data.length * 2);
        System.arraycopy(cur, 0, data, count * STRIDE, STRIDE);
        count++;
        float u = cur[3], v = cur[4];
        float r = cur[5], g = cur[6], b = cur[7], a = cur[8];
        resetCurrent();
        // uv and color persist only per vertex in 1.20; keep defaults for the next one
        if (!hasColor) {
            cur[5] = r;
            cur[6] = g;
            cur[7] = b;
            cur[8] = a;
        }
        if (!hasUv) {
            cur[3] = u;
            cur[4] = v;
        }
    }

    public RenderedBuffer end() {
        building = false;
        return new RenderedBuffer(mode, Arrays.copyOf(data, count * STRIDE), count, hasUv, hasColor);
    }

    public RenderedBuffer endOrDiscardIfEmpty() {
        return count == 0 ? null : end();
    }
}
