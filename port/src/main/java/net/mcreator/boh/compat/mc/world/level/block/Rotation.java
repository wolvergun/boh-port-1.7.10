package net.mcreator.boh.compat.mc.world.level.block;

import net.mcreator.boh.compat.mc.core.Direction;

public enum Rotation {

    NONE,
    CLOCKWISE_90,
    CLOCKWISE_180,
    COUNTERCLOCKWISE_90;

    public Direction rotate(Direction d) {
        if (d.getAxis() == net.mcreator.boh.compat.mc.core.Axis.Y) return d;
        switch (this) {
            case CLOCKWISE_90:
                return d.getClockWise();
            case CLOCKWISE_180:
                return d.getOpposite();
            case COUNTERCLOCKWISE_90:
                return d.getCounterClockWise();
            default:
                return d;
        }
    }

    public Rotation getRotated(Rotation other) {
        return values()[(ordinal() + other.ordinal()) % 4];
    }

    public static Rotation getRandom(java.util.Random r) {
        return values()[r.nextInt(4)];
    }
}
