package net.mcreator.boh.compat.mc.world.phys;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.AxisAlignedBB;

/** Immutable axis-aligned box (1.20 AABB). */
public class AABB {

    public final double minX, minY, minZ, maxX, maxY, maxZ;

    public AABB(double x1, double y1, double z1, double x2, double y2, double z2) {
        minX = Math.min(x1, x2);
        minY = Math.min(y1, y2);
        minZ = Math.min(z1, z2);
        maxX = Math.max(x1, x2);
        maxY = Math.max(y1, y2);
        maxZ = Math.max(z1, z2);
    }

    public AABB(BlockPos p) {
        this(p.getX(), p.getY(), p.getZ(), p.getX() + 1, p.getY() + 1, p.getZ() + 1);
    }

    public AABB(BlockPos a, BlockPos b) {
        this(a.getX(), a.getY(), a.getZ(), b.getX(), b.getY(), b.getZ());
    }

    public AABB(Vec3 a, Vec3 b) {
        this(a.x, a.y, a.z, b.x, b.y, b.z);
    }

    public static AABB ofSize(Vec3 center, double w, double h, double d) {
        return new AABB(center.x - w / 2, center.y - h / 2, center.z - d / 2, center.x + w / 2, center.y + h / 2,
            center.z + d / 2);
    }

    public static AABB unitCubeFromLowerCorner(Vec3 v) {
        return new AABB(v.x, v.y, v.z, v.x + 1, v.y + 1, v.z + 1);
    }

    public static AABB of(AxisAlignedBB b) {
        return b == null ? null : new AABB(b.minX, b.minY, b.minZ, b.maxX, b.maxY, b.maxZ);
    }

    public AxisAlignedBB toVanilla() {
        return AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public AABB inflate(double v) {
        return inflate(v, v, v);
    }

    public AABB inflate(double x, double y, double z) {
        return new AABB(minX - x, minY - y, minZ - z, maxX + x, maxY + y, maxZ + z);
    }

    public AABB deflate(double v) {
        return inflate(-v);
    }

    public AABB expandTowards(double x, double y, double z) {
        double x0 = minX, y0 = minY, z0 = minZ, x1 = maxX, y1 = maxY, z1 = maxZ;
        if (x < 0) x0 += x;
        else x1 += x;
        if (y < 0) y0 += y;
        else y1 += y;
        if (z < 0) z0 += z;
        else z1 += z;
        return new AABB(x0, y0, z0, x1, y1, z1);
    }

    public AABB expandTowards(Vec3 v) {
        return expandTowards(v.x, v.y, v.z);
    }

    public AABB contract(double x, double y, double z) {
        return expandTowards(-x, -y, -z);
    }

    public AABB move(double x, double y, double z) {
        return new AABB(minX + x, minY + y, minZ + z, maxX + x, maxY + y, maxZ + z);
    }

    public AABB move(Vec3 v) {
        return move(v.x, v.y, v.z);
    }

    public AABB move(BlockPos p) {
        return move(p.getX(), p.getY(), p.getZ());
    }

    public boolean intersects(AABB o) {
        return intersects(o.minX, o.minY, o.minZ, o.maxX, o.maxY, o.maxZ);
    }

    public boolean intersects(double x1, double y1, double z1, double x2, double y2, double z2) {
        return minX < x2 && maxX > x1 && minY < y2 && maxY > y1 && minZ < z2 && maxZ > z1;
    }

    public boolean contains(Vec3 v) {
        return contains(v.x, v.y, v.z);
    }

    public boolean contains(double x, double y, double z) {
        return x >= minX && x < maxX && y >= minY && y < maxY && z >= minZ && z < maxZ;
    }

    public Vec3 getCenter() {
        return new Vec3((minX + maxX) / 2, (minY + maxY) / 2, (minZ + maxZ) / 2);
    }

    public double getXsize() {
        return maxX - minX;
    }

    public double getYsize() {
        return maxY - minY;
    }

    public double getZsize() {
        return maxZ - minZ;
    }

    public double getSize() {
        return (getXsize() + getYsize() + getZsize()) / 3.0;
    }

    public AABB minmax(AABB o) {
        return new AABB(Math.min(minX, o.minX), Math.min(minY, o.minY), Math.min(minZ, o.minZ), Math.max(maxX, o.maxX),
            Math.max(maxY, o.maxY), Math.max(maxZ, o.maxZ));
    }

    public java.util.Optional<Vec3> clip(Vec3 from, Vec3 to) {
        net.minecraft.util.MovingObjectPosition hit = toVanilla().calculateIntercept(from.toVanilla(), to.toVanilla());
        return hit == null ? java.util.Optional.empty() : java.util.Optional.of(Vec3.of(hit.hitVec));
    }

    @Override
    public String toString() {
        return "AABB[" + minX + ", " + minY + ", " + minZ + "] -> [" + maxX + ", " + maxY + ", " + maxZ + "]";
    }
}
