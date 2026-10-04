package net.mcreator.boh.compat.mc.world.entity.monster;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;

/** 1.20 RangedAttackMob; bridges to the 1.7.10 interface used by EntityAIArrowAttack. */
public interface RangedAttackMob extends IRangedAttackMob {

    void performRangedAttack(EntityLivingBase target, float velocity);

    @Override
    default void attackEntityWithRangedAttack(EntityLivingBase target, float velocity) {
        performRangedAttack(target, velocity);
    }
}
