package net.mcreator.boh.compat.mc.client.model.geom.builders;

public final class CubeDeformation {
    public static final CubeDeformation NONE = new CubeDeformation(0.0F);
    final float growX;
    final float growY;
    final float growZ;

    public CubeDeformation(float g) {
        this(g, g, g);
    }

    public CubeDeformation(float x, float y, float z) {
        this.growX = x;
        this.growY = y;
        this.growZ = z;
    }

    public CubeDeformation extend(float g) {
        return new CubeDeformation(this.growX + g, this.growY + g, this.growZ + g);
    }

    public CubeDeformation extend(float x, float y, float z) {
        return new CubeDeformation(this.growX + x, this.growY + y, this.growZ + z);
    }
}
