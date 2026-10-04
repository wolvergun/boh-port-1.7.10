package net.mcreator.boh.compat.mc.world.entity.ai.control;

import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

/** 1.20 LookControl over the 1.7.10 EntityLookHelper. */
public class LookControl {

    protected final EntityLiving mob;

    public LookControl(EntityLiving mob) {
        this.mob = mob;
    }

    public void setLookAt(Entity e) {
        mob.getLookHelper().setLookPositionWithEntity(e, 10f, mob.getVerticalFaceSpeed());
    }

    public void setLookAt(Entity e, float yawSpeed, float pitchSpeed) {
        mob.getLookHelper().setLookPositionWithEntity(e, yawSpeed, pitchSpeed);
    }

    public void setLookAt(double x, double y, double z) {
        mob.getLookHelper().setLookPosition(x, y, z, 10f, mob.getVerticalFaceSpeed());
    }

    public void setLookAt(double x, double y, double z, float yawSpeed, float pitchSpeed) {
        mob.getLookHelper().setLookPosition(x, y, z, yawSpeed, pitchSpeed);
    }

    public void setLookAt(Vec3 v) {
        setLookAt(v.x, v.y, v.z);
    }

    public boolean isLookingAtTarget() {
        return true;
    }

    public double getWantedX() {
        return mob.getLookHelper().posX;
    }

    public double getWantedY() {
        return mob.getLookHelper().posY;
    }

    public double getWantedZ() {
        return mob.getLookHelper().posZ;
    }
}
