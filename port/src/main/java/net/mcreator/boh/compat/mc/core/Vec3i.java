package net.mcreator.boh.compat.mc.core;

/** Immutable integer vector (1.20 Vec3i). */
public class Vec3i implements Comparable<Vec3i> {

    public static final Vec3i ZERO = new Vec3i(0, 0, 0);

    protected final int x, y, z;

    public Vec3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public double distSqr(Vec3i o) {
        double dx = x - o.x, dy = y - o.y, dz = z - o.z;
        return dx * dx + dy * dy + dz * dz;
    }

    public double distToCenterSqr(double px, double py, double pz) {
        double dx = x + 0.5 - px, dy = y + 0.5 - py, dz = z + 0.5 - pz;
        return dx * dx + dy * dy + dz * dz;
    }

    public boolean closerThan(Vec3i o, double dist) {
        return distSqr(o) < dist * dist;
    }

    public int distManhattan(Vec3i o) {
        return Math.abs(x - o.x) + Math.abs(y - o.y) + Math.abs(z - o.z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vec3i)) return false;
        Vec3i v = (Vec3i) o;
        return x == v.x && y == v.y && z == v.z;
    }

    @Override
    public int hashCode() {
        return (y + z * 31) * 31 + x;
    }

    @Override
    public int compareTo(Vec3i o) {
        if (y != o.y) return Integer.compare(y, o.y);
        if (z != o.z) return Integer.compare(z, o.z);
        return Integer.compare(x, o.x);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{x=" + x + ", y=" + y + ", z=" + z + "}";
    }
}
