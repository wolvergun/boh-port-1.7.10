package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.passive.EntityTameable;

public class FollowOwnerGoal extends WrappedGoal {
    protected final EntityTameable tamable;

    public FollowOwnerGoal(EntityTameable tamable, double speed, float startDistance, float stopDistance, boolean canFly) {
        super(new EntityAIFollowOwner(tamable, speed, startDistance, stopDistance));
        this.tamable = tamable;
    }
}
