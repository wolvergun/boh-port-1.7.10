package net.mcreator.boh.compat.mc.world.entity;

import net.minecraft.entity.EntityLivingBase;

public class WalkAnimationState {
    private final EntityLivingBase e;

    public WalkAnimationState(EntityLivingBase e) {
        this.e = e;
    }

    public float position() {
        return this.e.limbSwing;
    }

    public float position(float partial) {
        return this.e.limbSwing - this.e.limbSwingAmount * (1.0F - partial);
    }

    public float speed() {
        return this.e.limbSwingAmount;
    }

    public float speed(float partial) {
        return this.e.prevLimbSwingAmount + (this.e.limbSwingAmount - this.e.prevLimbSwingAmount) * partial;
    }

    public boolean isMoving() {
        return this.e.limbSwingAmount > 1.0E-5F;
    }

    public void setSpeed(float s) {
        this.e.limbSwingAmount = s;
    }
}
