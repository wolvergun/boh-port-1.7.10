package net.mcreator.boh.compat.mc.world.phys;

import net.mcreator.boh.compat.mc.core.Axis;
import net.mcreator.boh.compat.mc.core.Vec3i;

public class Vec3 {
    public static final Vec3 ZERO = new Vec3(0.0, 0.0, 0.0);
    public final double x;
    public final double y;
    public final double z;

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
        float f = (float)Math.cos(-yRot * (float) (Math.PI / 180.0) - Math.PI);
        float f1 = (float)Math.sin(-yRot * (float) (Math.PI / 180.0) - Math.PI);
        float f2 = (float)(-Math.cos(-xRot * (float) (Math.PI / 180.0)));
        float f3 = (float)Math.sin(-xRot * (float) (Math.PI / 180.0));
        return new Vec3(f1 * f2, f3, f * f2);
    }

    public static Vec3 of(net.minecraft.util.Vec3 v) {
        return v == null ? null : new Vec3(v.xCoord, v.yCoord, v.zCoord);
    }

    public net.minecraft.util.Vec3 toVanilla() {
        return net.minecraft.util.Vec3.createVectorHelper(this.x, this.y, this.z);
    }

    public double x() {
        return this.x;
    }

    public double y() {
        return this.y;
    }

    public double z() {
        return this.z;
    }

    public Vec3 add(double dx, double dy, double dz) {
        return new Vec3(this.x + dx, this.y + dy, this.z + dz);
    }

    public Vec3 add(Vec3 o) {
        return this.add(o.x, o.y, o.z);
    }

    public Vec3 subtract(double dx, double dy, double dz) {
        return this.add(-dx, -dy, -dz);
    }

    public Vec3 subtract(Vec3 o) {
        return this.add(-o.x, -o.y, -o.z);
    }

    public Vec3 vectorTo(Vec3 o) {
        return new Vec3(o.x - this.x, o.y - this.y, o.z - this.z);
    }

    public Vec3 scale(double f) {
        return new Vec3(this.x * f, this.y * f, this.z * f);
    }

    public Vec3 multiply(double fx, double fy, double fz) {
        return new Vec3(this.x * fx, this.y * fy, this.z * fz);
    }

    public Vec3 multiply(Vec3 o) {
        return this.multiply(o.x, o.y, o.z);
    }

    public Vec3 reverse() {
        return this.scale(-1.0);
    }

    public Vec3 normalize() {
        double l = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        return l < 1.0E-4 ? ZERO : new Vec3(this.x / l, this.y / l, this.z / l);
    }

    public double dot(Vec3 o) {
        return this.x * o.x + this.y * o.y + this.z * o.z;
    }

    public Vec3 cross(Vec3 o) {
        return new Vec3(this.y * o.z - this.z * o.y, this.z * o.x - this.x * o.z, this.x * o.y - this.y * o.x);
    }

    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public double lengthSqr() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public double horizontalDistance() {
        return Math.sqrt(this.x * this.x + this.z * this.z);
    }

    public double horizontalDistanceSqr() {
        return this.x * this.x + this.z * this.z;
    }

    public double distanceTo(Vec3 o) {
        return Math.sqrt(this.distanceToSqr(o));
    }

    public double distanceToSqr(Vec3 o) {
        double dx = o.x - this.x;
        double dy = o.y - this.y;
        double dz = o.z - this.z;
        return dx * dx + dy * dy + dz * dz;
    }

    public double distanceToSqr(double ox, double oy, double oz) {
        double dx = ox - this.x;
        double dy = oy - this.y;
        double dz = oz - this.z;
        return dx * dx + dy * dy + dz * dz;
    }

    public boolean closerThan(Vec3 o, double d) {
        return this.distanceToSqr(o) < d * d;
    }

    public Vec3 xRot(float a) {
        double c = Math.cos(a);
        double s = Math.sin(a);
        return new Vec3(this.x, this.y * c + this.z * s, this.z * c - this.y * s);
    }

    public Vec3 yRot(float a) {
        double c = Math.cos(a);
        double s = Math.sin(a);
        return new Vec3(this.x * c + this.z * s, this.y, this.z * c - this.x * s);
    }

    public Vec3 zRot(float a) {
        double c = Math.cos(a);
        double s = Math.sin(a);
        return new Vec3(this.x * c + this.y * s, this.y * c - this.x * s, this.z);
    }

    public Vec3 lerp(Vec3 o, double t) {
        return new Vec3(this.x + (o.x - this.x) * t, this.y + (o.y - this.y) * t, this.z + (o.z - this.z) * t);
    }

    public Vec3 with(Axis axis, double v) {
        switch (axis) {
            case X:
                return new Vec3(v, this.y, this.z);
            case Y:
                return new Vec3(this.x, v, this.z);
            default:
                return new Vec3(this.x, this.y, v);
        }
    }

    public double get(Axis axis) {
        return axis.choose(this.x, this.y, this.z);
    }

    @Override
    public boolean equals(Object o) {
        return !(o instanceof Vec3 v) ? false : v.x == this.x && v.y == this.y && v.z == this.z;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(this.x) * 961 + Double.hashCode(this.y) * 31 + Double.hashCode(this.z);
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}
