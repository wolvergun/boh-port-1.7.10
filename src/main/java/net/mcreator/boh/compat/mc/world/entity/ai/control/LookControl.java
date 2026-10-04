package net.mcreator.boh.compat.mc.world.entity.ai.control;

import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

public class LookControl {
    protected final EntityLiving mob;

    public LookControl(EntityLiving mob) {
        this.mob = mob;
    }

    public void setLookAt(Entity e) {
        this.mob.getLookHelper().setLookPositionWithEntity(e, 10.0F, this.mob.getVerticalFaceSpeed());
    }

    public void setLookAt(Entity e, float yawSpeed, float pitchSpeed) {
        this.mob.getLookHelper().setLookPositionWithEntity(e, yawSpeed, pitchSpeed);
    }

    public void setLookAt(double x, double y, double z) {
        this.mob.getLookHelper().setLookPosition(x, y, z, 10.0F, this.mob.getVerticalFaceSpeed());
    }

    public void setLookAt(double x, double y, double z, float yawSpeed, float pitchSpeed) {
        this.mob.getLookHelper().setLookPosition(x, y, z, yawSpeed, pitchSpeed);
    }

    public void setLookAt(Vec3 v) {
        this.setLookAt(v.x, v.y, v.z);
    }

    public boolean isLookingAtTarget() {
        return true;
    }

    public double getWantedX() {
        return this.mob.getLookHelper().posX;
    }

    public double getWantedY() {
        return this.mob.getLookHelper().posY;
    }

    public double getWantedZ() {
        return this.mob.getLookHelper().posZ;
    }
}
