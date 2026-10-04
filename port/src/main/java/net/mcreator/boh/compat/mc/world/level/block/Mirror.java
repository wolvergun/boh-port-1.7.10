package net.mcreator.boh.compat.mc.world.level.block;

import net.mcreator.boh.compat.mc.core.Direction;

public enum Mirror {

    NONE,
    LEFT_RIGHT,
    FRONT_BACK;

    public Direction mirror(Direction d) {
        if (this == FRONT_BACK && d.getAxis() == net.mcreator.boh.compat.mc.core.Axis.X) return d.getOpposite();
        if (this == LEFT_RIGHT && d.getAxis() == net.mcreator.boh.compat.mc.core.Axis.Z) return d.getOpposite();
        return d;
    }

    public Rotation getRotation(Direction d) {
        return mirror(d) != d ? Rotation.CLOCKWISE_180 : Rotation.NONE;
    }
}
