package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIEatGrass;

public class EatBlockGoal extends WrappedGoal {
    public EatBlockGoal(EntityLiving mob) {
        super(new EntityAIEatGrass(mob));
    }
}
