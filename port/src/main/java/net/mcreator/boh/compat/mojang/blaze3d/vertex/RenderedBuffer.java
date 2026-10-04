package net.mcreator.boh.compat.mojang.blaze3d.vertex;

/** Finished vertex data (positions, uvs, colors) for one draw. */
public final class RenderedBuffer {

    final Mode mode;
    final float[] data;
    final int count;
    final boolean hasUv, hasColor;

    RenderedBuffer(Mode mode, float[] data, int count, boolean hasUv, boolean hasColor) {
        this.mode = mode;
        this.data = data;
        this.count = count;
        this.hasUv = hasUv;
        this.hasColor = hasColor;
    }

    public void release() {}
}
