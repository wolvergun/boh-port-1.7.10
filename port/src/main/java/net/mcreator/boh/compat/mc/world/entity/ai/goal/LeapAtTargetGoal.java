package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAILeapAtTarget;

public class LeapAtTargetGoal extends WrappedGoal {

    public LeapAtTargetGoal(EntityLiving mob, float yd) {
        super(new EntityAILeapAtTarget(mob, yd));
    }
}
