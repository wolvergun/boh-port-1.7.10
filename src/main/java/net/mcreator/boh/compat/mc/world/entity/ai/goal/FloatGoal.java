package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;

public class FloatGoal extends WrappedGoal {
    protected final EntityLiving mob;

    public FloatGoal(EntityLiving mob) {
        super(new EntityAISwimming(mob));
        this.mob = mob;
    }
}
