package net.mcreator.boh.compat.mc.client.model.geom.builders;

/** 1.20 CubeDeformation: per-axis inflation in pixels. */
public final class CubeDeformation {

    public static final CubeDeformation NONE = new CubeDeformation(0);

    final float growX, growY, growZ;

    public CubeDeformation(float g) {
        this(g, g, g);
    }

    public CubeDeformation(float x, float y, float z) {
        growX = x;
        growY = y;
        growZ = z;
    }

    public CubeDeformation extend(float g) {
        return new CubeDeformation(growX + g, growY + g, growZ + g);
    }

    public CubeDeformation extend(float x, float y, float z) {
        return new CubeDeformation(growX + x, growY + y, growZ + z);
    }
}
