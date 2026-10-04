package net.mcreator.boh.compat.mojang.blaze3d.vertex;

import java.util.Arrays;
import net.mcreator.boh.compat.client.VertexConsumer;

public class BufferBuilder implements VertexConsumer {
    static final int STRIDE = 9;
    private Mode mode = Mode.QUADS;
    private float[] data = new float[2304];
    private int count;
    private boolean building;
    private boolean hasUv;
    private boolean hasColor;
    private final float[] cur = new float[9];

    public void begin(Mode mode, DefaultVertexFormat format) {
        this.mode = mode;
        this.count = 0;
        this.building = true;
        this.hasUv = false;
        this.hasColor = false;
        this.resetCurrent();
    }

    public boolean building() {
        return this.building;
    }

    private void resetCurrent() {
        Arrays.fill(this.cur, 0.0F);
        this.cur[5] = this.cur[6] = this.cur[7] = this.cur[8] = 1.0F;
    }

    @Override
    public VertexConsumer vertex(double x, double y, double z) {
        this.cur[0] = (float)x;
        this.cur[1] = (float)y;
        this.cur[2] = (float)z;
        return this;
    }

    @Override
    public VertexConsumer color(float r, float g, float b, float a) {
        this.hasColor = true;
        this.cur[5] = r;
        this.cur[6] = g;
        this.cur[7] = b;
        this.cur[8] = a;
        return this;
    }

    @Override
    public VertexConsumer uv(float u, float v) {
        this.hasUv = true;
        this.cur[3] = u;
        this.cur[4] = v;
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
        if ((this.count + 1) * 9 > this.data.length) {
            this.data = Arrays.copyOf(this.data, this.data.length * 2);
        }

        System.arraycopy(this.cur, 0, this.data, this.count * 9, 9);
        this.count++;
        float u = this.cur[3];
        float v = this.cur[4];
        float r = this.cur[5];
        float g = this.cur[6];
        float b = this.cur[7];
        float a = this.cur[8];
        this.resetCurrent();
        if (!this.hasColor) {
            this.cur[5] = r;
            this.cur[6] = g;
            this.cur[7] = b;
            this.cur[8] = a;
        }

        if (!this.hasUv) {
            this.cur[3] = u;
            this.cur[4] = v;
        }
    }

    public RenderedBuffer end() {
        this.building = false;
        return new RenderedBuffer(this.mode, Arrays.copyOf(this.data, this.count * 9), this.count, this.hasUv, this.hasColor);
    }

    public RenderedBuffer endOrDiscardIfEmpty() {
        return this.count == 0 ? null : this.end();
    }
}
