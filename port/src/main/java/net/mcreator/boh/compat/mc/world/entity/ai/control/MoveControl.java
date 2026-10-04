package net.mcreator.boh.compat.mc.world.entity.ai.control;

import net.minecraft.entity.EntityLiving;

/** 1.20 MoveControl over the entity's 1.7.10 EntityMoveHelper. */
public class MoveControl {

    protected final EntityLiving mob;
    public double wantedX, wantedY, wantedZ;
    protected double speedModifier;
    protected Operation operation = Operation.WAIT;

    public MoveControl(EntityLiving mob) {
        this.mob = mob;
    }

    public boolean hasWanted() {
        return operation == Operation.MOVE_TO || mob.getMoveHelper().isUpdating();
    }

    public double getSpeedModifier() {
        return speedModifier;
    }

    public void setWantedPosition(double x, double y, double z, double speed) {
        wantedX = x;
        wantedY = y;
        wantedZ = z;
        speedModifier = speed;
        operation = Operation.MOVE_TO;
        mob.getMoveHelper().setMoveTo(x, y, z, speed);
    }

    public void strafe(float forward, float strafe) {
        operation = Operation.STRAFE;
        mob.moveForward = forward;
        mob.moveStrafing = strafe;
    }

    public double getWantedX() {
        return wantedX;
    }

    public double getWantedY() {
        return wantedY;
    }

    public double getWantedZ() {
        return wantedZ;
    }

    /** Called by the vanilla move helper once the target was handed over. */
    public void tick() {
        if (operation == Operation.MOVE_TO && !mob.getMoveHelper().isUpdating()) operation = Operation.WAIT;
    }
}
