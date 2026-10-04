package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIWatchClosest;

public class LookAtPlayerGoal extends WrappedGoal {
    protected final EntityLiving mob;

    public LookAtPlayerGoal(EntityLiving mob, Class<?> lookAtType, float lookDistance) {
        super(new EntityAIWatchClosest(mob, lookAtType, lookDistance));
        this.mob = mob;
    }

    public LookAtPlayerGoal(EntityLiving mob, Class<?> lookAtType, float lookDistance, float probability) {
        super(new EntityAIWatchClosest(mob, lookAtType, lookDistance, probability));
        this.mob = mob;
    }

    public LookAtPlayerGoal(EntityLiving mob, Class<?> lookAtType, float lookDistance, float probability, boolean onlyHorizontal) {
        this(mob, lookAtType, lookDistance, probability);
    }
}
