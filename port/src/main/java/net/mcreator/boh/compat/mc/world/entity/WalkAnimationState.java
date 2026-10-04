package net.mcreator.boh.compat.mc.world.entity;

import net.minecraft.entity.EntityLivingBase;

/** 1.20 WalkAnimationState view over the 1.7.10 limbSwing fields. */
public class WalkAnimationState {

    private final EntityLivingBase e;

    public WalkAnimationState(EntityLivingBase e) {
        this.e = e;
    }

    public float position() {
        return e.limbSwing;
    }

    public float position(float partial) {
        return e.limbSwing - e.limbSwingAmount * (1.0F - partial);
    }

    public float speed() {
        return e.limbSwingAmount;
    }

    public float speed(float partial) {
        return e.prevLimbSwingAmount + (e.limbSwingAmount - e.prevLimbSwingAmount) * partial;
    }

    public boolean isMoving() {
        return e.limbSwingAmount > 1.0E-5F;
    }

    public void setSpeed(float s) {
        e.limbSwingAmount = s;
    }
}
