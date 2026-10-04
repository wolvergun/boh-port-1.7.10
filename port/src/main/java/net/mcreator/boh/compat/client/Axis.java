package net.mcreator.boh.compat.client;

/** Stand-in for com.mojang.math.Axis. */
public final class Axis {

    public static final Axis XN = new Axis(-1, 0, 0);
    public static final Axis XP = new Axis(1, 0, 0);
    public static final Axis YN = new Axis(0, -1, 0);
    public static final Axis YP = new Axis(0, 1, 0);
    public static final Axis ZN = new Axis(0, 0, -1);
    public static final Axis ZP = new Axis(0, 0, 1);

    private final float x, y, z;

    private Axis(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Quaternionf rotation(float radians) {
        return Quaternionf.axisAngle(x, y, z, radians);
    }

    public Quaternionf rotationDegrees(float degrees) {
        return rotation((float) Math.toRadians(degrees));
    }
}
