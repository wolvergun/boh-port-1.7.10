package net.mcreator.boh.compat.mc.world.entity.ai.goal.target;

import net.mcreator.boh.compat.mc.world.entity.ai.goal.WrappedGoal;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.passive.EntityTameable;

public class OwnerHurtByTargetGoal extends WrappedGoal {
    public OwnerHurtByTargetGoal(EntityTameable tamable) {
        super(new EntityAIOwnerHurtByTarget(tamable));
    }
}
