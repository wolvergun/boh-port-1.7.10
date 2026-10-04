package net.mcreator.boh.compat.mc.world.level.block;

import net.mcreator.boh.compat.mc.core.Axis;
import net.mcreator.boh.compat.mc.core.Direction;

public enum Mirror {
    NONE,
    LEFT_RIGHT,
    FRONT_BACK;

    public Direction mirror(Direction d) {
        if (this == FRONT_BACK && d.getAxis() == Axis.X) {
            return d.getOpposite();
        } else {
            return this == LEFT_RIGHT && d.getAxis() == Axis.Z ? d.getOpposite() : d;
        }
    }

    public Rotation getRotation(Direction d) {
        return this.mirror(d) != d ? Rotation.CLOCKWISE_180 : Rotation.NONE;
    }
}
