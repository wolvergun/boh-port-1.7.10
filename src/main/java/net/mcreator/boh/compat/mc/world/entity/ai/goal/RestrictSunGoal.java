package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIRestrictSun;

public class RestrictSunGoal extends WrappedGoal {
    public RestrictSunGoal(EntityCreature mob) {
        super(new EntityAIRestrictSun(mob));
    }
}
