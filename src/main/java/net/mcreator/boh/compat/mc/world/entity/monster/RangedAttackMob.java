package net.mcreator.boh.compat.mc.world.entity.monster;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;

public interface RangedAttackMob extends IRangedAttackMob {
    void performRangedAttack(EntityLivingBase var1, float var2);

    default void attackEntityWithRangedAttack(EntityLivingBase target, float velocity) {
        this.performRangedAttack(target, velocity);
    }
}
