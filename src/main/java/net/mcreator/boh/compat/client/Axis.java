package net.mcreator.boh.compat.client;

public final class Axis {
    public static final Axis XN = new Axis(-1.0F, 0.0F, 0.0F);
    public static final Axis XP = new Axis(1.0F, 0.0F, 0.0F);
    public static final Axis YN = new Axis(0.0F, -1.0F, 0.0F);
    public static final Axis YP = new Axis(0.0F, 1.0F, 0.0F);
    public static final Axis ZN = new Axis(0.0F, 0.0F, -1.0F);
    public static final Axis ZP = new Axis(0.0F, 0.0F, 1.0F);
    private final float x;
    private final float y;
    private final float z;

    private Axis(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Quaternionf rotation(float radians) {
        return Quaternionf.axisAngle(this.x, this.y, this.z, radians);
    }

    public Quaternionf rotationDegrees(float degrees) {
        return this.rotation((float)Math.toRadians(degrees));
    }
}
