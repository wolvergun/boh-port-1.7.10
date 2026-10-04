package net.mcreator.boh.compat.mc.core;

import java.util.ArrayList;
import java.util.List;

import net.mcreator.boh.compat.mc.world.phys.Vec3;

/** Immutable block position (1.20 BlockPos). */
public class BlockPos extends Vec3i {

    public static final BlockPos ZERO = new BlockPos(0, 0, 0);

    public BlockPos(int x, int y, int z) {
        super(x, y, z);
    }

    public BlockPos(double x, double y, double z) {
        super((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
    }

    public BlockPos(Vec3i v) {
        super(v.getX(), v.getY(), v.getZ());
    }

    public static BlockPos containing(double x, double y, double z) {
        return new BlockPos((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
    }

    public static BlockPos containing(Vec3 v) {
        return containing(v.x, v.y, v.z);
    }

    public static BlockPos of(long packed) {
        return new BlockPos((int) (packed >> 38), (int) (packed << 52 >> 52), (int) (packed << 26 >> 38));
    }

    public long asLong() {
        return ((long) x & 0x3FFFFFFL) << 38 | ((long) z & 0x3FFFFFFL) << 12 | ((long) y & 0xFFFL);
    }

    public BlockPos offset(int dx, int dy, int dz) {
        return dx == 0 && dy == 0 && dz == 0 ? this : new BlockPos(x + dx, y + dy, z + dz);
    }

    public BlockPos offset(double dx, double dy, double dz) {
        return offset((int) dx, (int) dy, (int) dz);
    }

    public BlockPos offset(Vec3i v) {
        return offset(v.getX(), v.getY(), v.getZ());
    }

    public BlockPos subtract(Vec3i v) {
        return offset(-v.getX(), -v.getY(), -v.getZ());
    }

    public BlockPos multiply(int f) {
        return new BlockPos(x * f, y * f, z * f);
    }

    public BlockPos above() {
        return above(1);
    }

    public BlockPos above(int n) {
        return offset(0, n, 0);
    }

    public BlockPos below() {
        return below(1);
    }

    public BlockPos below(int n) {
        return offset(0, -n, 0);
    }

    public BlockPos north() {
        return north(1);
    }

    public BlockPos north(int n) {
        return offset(0, 0, -n);
    }

    public BlockPos south() {
        return south(1);
    }

    public BlockPos south(int n) {
        return offset(0, 0, n);
    }

    public BlockPos west() {
        return west(1);
    }

    public BlockPos west(int n) {
        return offset(-n, 0, 0);
    }

    public BlockPos east() {
        return east(1);
    }

    public BlockPos east(int n) {
        return offset(n, 0, 0);
    }

    public BlockPos relative(Direction d) {
        return relative(d, 1);
    }

    public BlockPos relative(Direction d, int n) {
        return offset(d.getStepX() * n, d.getStepY() * n, d.getStepZ() * n);
    }

    public BlockPos atY(int ny) {
        return new BlockPos(x, ny, z);
    }

    public BlockPos immutable() {
        return this;
    }

    public Vec3 getCenter() {
        return Vec3.atCenterOf(this);
    }

    public MutableBlockPos mutable() {
        return new MutableBlockPos(x, y, z);
    }

    public static Iterable<BlockPos> betweenClosed(BlockPos a, BlockPos b) {
        List<BlockPos> out = new ArrayList<>();
        int x0 = Math.min(a.x, b.x), x1 = Math.max(a.x, b.x);
        int y0 = Math.min(a.y, b.y), y1 = Math.max(a.y, b.y);
        int z0 = Math.min(a.z, b.z), z1 = Math.max(a.z, b.z);
        for (int yy = y0; yy <= y1; yy++) for (int zz = z0; zz <= z1; zz++) for (int xx = x0; xx <= x1; xx++)
            out.add(new BlockPos(xx, yy, zz));
        return out;
    }

    /** Positions stay immutable underneath; the MCreator code only ever reads them. */
    public static class MutableBlockPos extends BlockPos {

        public MutableBlockPos(int x, int y, int z) {
            super(x, y, z);
        }

        public MutableBlockPos() {
            super(0, 0, 0);
        }
    }
}
