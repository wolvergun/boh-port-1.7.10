package net.mcreator.boh.compat.mc.world.phys;

import java.util.Optional;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;

public class AABB {
    public final double minX;
    public final double minY;
    public final double minZ;
    public final double maxX;
    public final double maxY;
    public final double maxZ;

    public AABB(double x1, double y1, double z1, double x2, double y2, double z2) {
        this.minX = Math.min(x1, x2);
        this.minY = Math.min(y1, y2);
        this.minZ = Math.min(z1, z2);
        this.maxX = Math.max(x1, x2);
        this.maxY = Math.max(y1, y2);
        this.maxZ = Math.max(z1, z2);
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
        return new AABB(center.x - w / 2.0, center.y - h / 2.0, center.z - d / 2.0, center.x + w / 2.0, center.y + h / 2.0, center.z + d / 2.0);
    }

    public static AABB unitCubeFromLowerCorner(Vec3 v) {
        return new AABB(v.x, v.y, v.z, v.x + 1.0, v.y + 1.0, v.z + 1.0);
    }

    public static AABB of(AxisAlignedBB b) {
        return b == null ? null : new AABB(b.minX, b.minY, b.minZ, b.maxX, b.maxY, b.maxZ);
    }

    public AxisAlignedBB toVanilla() {
        return AxisAlignedBB.getBoundingBox(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
    }

    public AABB inflate(double v) {
        return this.inflate(v, v, v);
    }

    public AABB inflate(double x, double y, double z) {
        return new AABB(this.minX - x, this.minY - y, this.minZ - z, this.maxX + x, this.maxY + y, this.maxZ + z);
    }

    public AABB deflate(double v) {
        return this.inflate(-v);
    }

    public AABB expandTowards(double x, double y, double z) {
        double x0 = this.minX;
        double y0 = this.minY;
        double z0 = this.minZ;
        double x1 = this.maxX;
        double y1 = this.maxY;
        double z1 = this.maxZ;
        if (x < 0.0) {
            x0 += x;
        } else {
            x1 += x;
        }

        if (y < 0.0) {
            y0 += y;
        } else {
            y1 += y;
        }

        if (z < 0.0) {
            z0 += z;
        } else {
            z1 += z;
        }

        return new AABB(x0, y0, z0, x1, y1, z1);
    }

    public AABB expandTowards(Vec3 v) {
        return this.expandTowards(v.x, v.y, v.z);
    }

    public AABB contract(double x, double y, double z) {
        return this.expandTowards(-x, -y, -z);
    }

    public AABB move(double x, double y, double z) {
        return new AABB(this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z);
    }

    public AABB move(Vec3 v) {
        return this.move(v.x, v.y, v.z);
    }

    public AABB move(BlockPos p) {
        return this.move(p.getX(), p.getY(), p.getZ());
    }

    public boolean intersects(AABB o) {
        return this.intersects(o.minX, o.minY, o.minZ, o.maxX, o.maxY, o.maxZ);
    }

    public boolean intersects(double x1, double y1, double z1, double x2, double y2, double z2) {
        return this.minX < x2 && this.maxX > x1 && this.minY < y2 && this.maxY > y1 && this.minZ < z2 && this.maxZ > z1;
    }

    public boolean contains(Vec3 v) {
        return this.contains(v.x, v.y, v.z);
    }

    public boolean contains(double x, double y, double z) {
        return x >= this.minX && x < this.maxX && y >= this.minY && y < this.maxY && z >= this.minZ && z < this.maxZ;
    }

    public Vec3 getCenter() {
        return new Vec3((this.minX + this.maxX) / 2.0, (this.minY + this.maxY) / 2.0, (this.minZ + this.maxZ) / 2.0);
    }

    public double getXsize() {
        return this.maxX - this.minX;
    }

    public double getYsize() {
        return this.maxY - this.minY;
    }

    public double getZsize() {
        return this.maxZ - this.minZ;
    }

    public double getSize() {
        return (this.getXsize() + this.getYsize() + this.getZsize()) / 3.0;
    }

    public AABB minmax(AABB o) {
        return new AABB(
            Math.min(this.minX, o.minX),
            Math.min(this.minY, o.minY),
            Math.min(this.minZ, o.minZ),
            Math.max(this.maxX, o.maxX),
            Math.max(this.maxY, o.maxY),
            Math.max(this.maxZ, o.maxZ)
        );
    }

    public Optional<Vec3> clip(Vec3 from, Vec3 to) {
        MovingObjectPosition hit = this.toVanilla().calculateIntercept(from.toVanilla(), to.toVanilla());
        return hit == null ? Optional.empty() : Optional.of(Vec3.of(hit.hitVec));
    }

    @Override
    public String toString() {
        return "AABB[" + this.minX + ", " + this.minY + ", " + this.minZ + "] -> [" + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
    }
}
