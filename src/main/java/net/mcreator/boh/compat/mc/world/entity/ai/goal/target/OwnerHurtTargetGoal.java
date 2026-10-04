package net.mcreator.boh.compat.mc.world.entity.ai.goal.target;

import net.mcreator.boh.compat.mc.world.entity.ai.goal.WrappedGoal;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.passive.EntityTameable;

public class OwnerHurtTargetGoal extends WrappedGoal {
    public OwnerHurtTargetGoal(EntityTameable tamable) {
        super(new EntityAIOwnerHurtTarget(tamable));
    }
}
