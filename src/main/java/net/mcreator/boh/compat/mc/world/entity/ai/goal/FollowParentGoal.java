package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.passive.EntityAnimal;

public class FollowParentGoal extends WrappedGoal {
    public FollowParentGoal(EntityAnimal animal, double speed) {
        super(new EntityAIFollowParent(animal, speed));
    }
}
