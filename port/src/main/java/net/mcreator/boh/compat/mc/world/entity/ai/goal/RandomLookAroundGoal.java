package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAILookIdle;

public class RandomLookAroundGoal extends WrappedGoal {

    protected final EntityLiving mob;

    public RandomLookAroundGoal(EntityLiving mob) {
        super(new EntityAILookIdle(mob));
        this.mob = mob;
    }
}
