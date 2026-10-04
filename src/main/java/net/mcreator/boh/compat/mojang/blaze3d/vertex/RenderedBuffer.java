package net.mcreator.boh.compat.mojang.blaze3d.vertex;

public final class RenderedBuffer {
    final Mode mode;
    final float[] data;
    final int count;
    final boolean hasUv;
    final boolean hasColor;

    RenderedBuffer(Mode mode, float[] data, int count, boolean hasUv, boolean hasColor) {
        this.mode = mode;
        this.data = data;
        this.count = count;
        this.hasUv = hasUv;
        this.hasColor = hasColor;
    }

    public void release() {
    }
}
