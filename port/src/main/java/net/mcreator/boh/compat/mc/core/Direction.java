package net.mcreator.boh.compat.mc.core;

import java.util.Random;

import net.mcreator.boh.compat.mc.world.phys.Vec3;

/** 1.20 Direction; the ordinal matches 1.7.10 side numbers (0 down, 1 up, 2 north, 3 south, 4 west, 5 east). */
public enum Direction {

    DOWN(0, -1, 0, "down", -1),
    UP(0, 1, 0, "up", -1),
    NORTH(0, 0, -1, "north", 2),
    SOUTH(0, 0, 1, "south", 0),
    WEST(-1, 0, 0, "west", 1),
    EAST(1, 0, 0, "east", 3);



    private final int stepX, stepY, stepZ;
    private final String name;
    private final int data2d;

    Direction(int x, int y, int z, String name, int data2d) {
        stepX = x;
        stepY = y;
        stepZ = z;
        this.name = name;
        this.data2d = data2d;
    }

    public int getStepX() {
        return stepX;
    }

    public int getStepY() {
        return stepY;
    }

    public int getStepZ() {
        return stepZ;
    }

    public Vec3i getNormal() {
        return new Vec3i(stepX, stepY, stepZ);
    }

    public String getName() {
        return name;
    }

    public String getSerializedName() {
        return name;
    }

    public int get3DDataValue() {
        return ordinal();
    }

    public int get2DDataValue() {
        return data2d;
    }

    public Axis getAxis() {
        return stepX != 0 ? Axis.X : stepY != 0 ? Axis.Y : Axis.Z;
    }

    public AxisDirection getAxisDirection() {
        return stepX + stepY + stepZ > 0 ? AxisDirection.POSITIVE : AxisDirection.NEGATIVE;
    }

    public Direction getOpposite() {
        switch (this) {
            case DOWN:
                return UP;
            case UP:
                return DOWN;
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            case WEST:
                return EAST;
            default:
                return WEST;
        }
    }

    public Direction getClockWise() {
        switch (this) {
            case NORTH:
                return EAST;
            case EAST:
                return SOUTH;
            case SOUTH:
                return WEST;
            case WEST:
                return NORTH;
            default:
                return this;
        }
    }

    public Direction getCounterClockWise() {
        return getClockWise().getOpposite();
    }

    public float toYRot() {
        return (data2d & 3) * 90f;
    }

    public static Direction from3DDataValue(int v) {
        return values()[Math.abs(v % 6)];
    }

    public static Direction from2DDataValue(int v) {
        switch (Math.abs(v % 4)) {
            case 0:
                return SOUTH;
            case 1:
                return WEST;
            case 2:
                return NORTH;
            default:
                return EAST;
        }
    }

    public static Direction fromYRot(double yRot) {
        return from2DDataValue((int) Math.floor(yRot / 90.0 + 0.5) & 3);
    }

    public static Direction fromAxisAndDirection(Axis axis, AxisDirection dir) {
        switch (axis) {
            case X:
                return dir == AxisDirection.POSITIVE ? EAST : WEST;
            case Y:
                return dir == AxisDirection.POSITIVE ? UP : DOWN;
            default:
                return dir == AxisDirection.POSITIVE ? SOUTH : NORTH;
        }
    }

    public static Direction byName(String name) {
        for (Direction d : values()) if (d.name.equalsIgnoreCase(name)) return d;
        return null;
    }

    public static Direction getNearest(double x, double y, double z) {
        Direction best = NORTH;
        double bestDot = -Double.MAX_VALUE;
        for (Direction d : values()) {
            double dot = x * d.stepX + y * d.stepY + z * d.stepZ;
            if (dot > bestDot) {
                bestDot = dot;
                best = d;
            }
        }
        return best;
    }

    public static Direction getNearest(Vec3 v) {
        return getNearest(v.x, v.y, v.z);
    }

    public static Direction getRandom(Random random) {
        return values()[random.nextInt(6)];
    }

    @Override
    public String toString() {
        return name;
    }

}
