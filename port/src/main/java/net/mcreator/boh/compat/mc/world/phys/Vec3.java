package net.mcreator.boh.compat.mc.world.phys;

import net.mcreator.boh.compat.mc.core.Axis;
import net.mcreator.boh.compat.mc.core.Vec3i;

/** Immutable double vector (1.20 Vec3). */
public class Vec3 {

    public static final Vec3 ZERO = new Vec3(0, 0, 0);

    public final double x, y, z;

    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vec3 atCenterOf(Vec3i p) {
        return new Vec3(p.getX() + 0.5, p.getY() + 0.5, p.getZ() + 0.5);
    }

    public static Vec3 atBottomCenterOf(Vec3i p) {
        return new Vec3(p.getX() + 0.5, p.getY(), p.getZ() + 0.5);
    }

    public static Vec3 atLowerCornerOf(Vec3i p) {
        return new Vec3(p.getX(), p.getY(), p.getZ());
    }

    public static Vec3 upFromBottomCenterOf(Vec3i p, double dy) {
        return new Vec3(p.getX() + 0.5, p.getY() + dy, p.getZ() + 0.5);
    }

    public static Vec3 directionFromRotation(float xRot, float yRot) {
        float f = (float) Math.cos(-yRot * 0.017453292F - Math.PI);
        float f1 = (float) Math.sin(-yRot * 0.017453292F - Math.PI);
        float f2 = (float) -Math.cos(-xRot * 0.017453292F);
        float f3 = (float) Math.sin(-xRot * 0.017453292F);
        return new Vec3(f1 * f2, f3, f * f2);
    }

    public static Vec3 of(net.minecraft.util.Vec3 v) {
        return v == null ? null : new Vec3(v.xCoord, v.yCoord, v.zCoord);
    }

    public net.minecraft.util.Vec3 toVanilla() {
        return net.minecraft.util.Vec3.createVectorHelper(x, y, z);
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }

    public Vec3 add(double dx, double dy, double dz) {
        return new Vec3(x + dx, y + dy, z + dz);
    }

    public Vec3 add(Vec3 o) {
        return add(o.x, o.y, o.z);
    }

    public Vec3 subtract(double dx, double dy, double dz) {
        return add(-dx, -dy, -dz);
    }

    public Vec3 subtract(Vec3 o) {
        return add(-o.x, -o.y, -o.z);
    }

    public Vec3 vectorTo(Vec3 o) {
        return new Vec3(o.x - x, o.y - y, o.z - z);
    }

    public Vec3 scale(double f) {
        return new Vec3(x * f, y * f, z * f);
    }

    public Vec3 multiply(double fx, double fy, double fz) {
        return new Vec3(x * fx, y * fy, z * fz);
    }

    public Vec3 multiply(Vec3 o) {
        return multiply(o.x, o.y, o.z);
    }

    public Vec3 reverse() {
        return scale(-1);
    }

    public Vec3 normalize() {
        double l = Math.sqrt(x * x + y * y + z * z);
        return l < 1.0E-4 ? ZERO : new Vec3(x / l, y / l, z / l);
    }

    public double dot(Vec3 o) {
        return x * o.x + y * o.y + z * o.z;
    }

    public Vec3 cross(Vec3 o) {
        return new Vec3(y * o.z - z * o.y, z * o.x - x * o.z, x * o.y - y * o.x);
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public double lengthSqr() {
        return x * x + y * y + z * z;
    }

    public double horizontalDistance() {
        return Math.sqrt(x * x + z * z);
    }

    public double horizontalDistanceSqr() {
        return x * x + z * z;
    }

    public double distanceTo(Vec3 o) {
        return Math.sqrt(distanceToSqr(o));
    }

    public double distanceToSqr(Vec3 o) {
        double dx = o.x - x, dy = o.y - y, dz = o.z - z;
        return dx * dx + dy * dy + dz * dz;
    }

    public double distanceToSqr(double ox, double oy, double oz) {
        double dx = ox - x, dy = oy - y, dz = oz - z;
        return dx * dx + dy * dy + dz * dz;
    }

    public boolean closerThan(Vec3 o, double d) {
        return distanceToSqr(o) < d * d;
    }

    public Vec3 xRot(float a) {
        double c = Math.cos(a), s = Math.sin(a);
        return new Vec3(x, y * c + z * s, z * c - y * s);
    }

    public Vec3 yRot(float a) {
        double c = Math.cos(a), s = Math.sin(a);
        return new Vec3(x * c + z * s, y, z * c - x * s);
    }

    public Vec3 zRot(float a) {
        double c = Math.cos(a), s = Math.sin(a);
        return new Vec3(x * c + y * s, y * c - x * s, z);
    }

    public Vec3 lerp(Vec3 o, double t) {
        return new Vec3(x + (o.x - x) * t, y + (o.y - y) * t, z + (o.z - z) * t);
    }

    public Vec3 with(Axis axis, double v) {
        switch (axis) {
            case X:
                return new Vec3(v, y, z);
            case Y:
                return new Vec3(x, v, z);
            default:
                return new Vec3(x, y, v);
        }
    }

    public double get(Axis axis) {
        return axis.choose(x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vec3)) return false;
        Vec3 v = (Vec3) o;
        return v.x == x && v.y == y && v.z == z;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(x) * 961 + Double.hashCode(y) * 31 + Double.hashCode(z);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
