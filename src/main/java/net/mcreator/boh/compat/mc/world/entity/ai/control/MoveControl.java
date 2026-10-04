package net.mcreator.boh.compat.mc.world.entity.ai.control;

import net.minecraft.entity.EntityLiving;

public class MoveControl {
    protected final EntityLiving mob;
    public double wantedX;
    public double wantedY;
    public double wantedZ;
    protected double speedModifier;
    protected Operation operation = Operation.WAIT;

    public MoveControl(EntityLiving mob) {
        this.mob = mob;
    }

    public boolean hasWanted() {
        return this.operation == Operation.MOVE_TO || this.mob.getMoveHelper().isUpdating();
    }

    public double getSpeedModifier() {
        return this.speedModifier;
    }

    public void setWantedPosition(double x, double y, double z, double speed) {
        this.wantedX = x;
        this.wantedY = y;
        this.wantedZ = z;
        this.speedModifier = speed;
        this.operation = Operation.MOVE_TO;
        this.mob.getMoveHelper().setMoveTo(x, y, z, speed);
    }

    public void strafe(float forward, float strafe) {
        this.operation = Operation.STRAFE;
        this.mob.moveForward = forward;
        this.mob.moveStrafing = strafe;
    }

    public double getWantedX() {
        return this.wantedX;
    }

    public double getWantedY() {
        return this.wantedY;
    }

    public double getWantedZ() {
        return this.wantedZ;
    }

    public void tick() {
        if (this.operation == Operation.MOVE_TO && !this.mob.getMoveHelper().isUpdating()) {
            this.operation = Operation.WAIT;
        }
    }
}
