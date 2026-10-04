package net.mcreator.boh.compat.mc.core;

public class Vec3i implements Comparable<Vec3i> {
    public static final Vec3i ZERO = new Vec3i(0, 0, 0);
    protected final int x;
    protected final int y;
    protected final int z;

    public Vec3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public double distSqr(Vec3i o) {
        double dx = this.x - o.x;
        double dy = this.y - o.y;
        double dz = this.z - o.z;
        return dx * dx + dy * dy + dz * dz;
    }

    public double distToCenterSqr(double px, double py, double pz) {
        double dx = this.x + 0.5 - px;
        double dy = this.y + 0.5 - py;
        double dz = this.z + 0.5 - pz;
        return dx * dx + dy * dy + dz * dz;
    }

    public boolean closerThan(Vec3i o, double dist) {
        return this.distSqr(o) < dist * dist;
    }

    public int distManhattan(Vec3i o) {
        return Math.abs(this.x - o.x) + Math.abs(this.y - o.y) + Math.abs(this.z - o.z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else {
            return !(o instanceof Vec3i v) ? false : this.x == v.x && this.y == v.y && this.z == v.z;
        }
    }

    @Override
    public int hashCode() {
        return (this.y + this.z * 31) * 31 + this.x;
    }

    public int compareTo(Vec3i o) {
        if (this.y != o.y) {
            return Integer.compare(this.y, o.y);
        } else {
            return this.z != o.z ? Integer.compare(this.z, o.z) : Integer.compare(this.x, o.x);
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{x=" + this.x + ", y=" + this.y + ", z=" + this.z + "}";
    }
}
