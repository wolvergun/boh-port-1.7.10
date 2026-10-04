package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAITempt;

public class TemptGoal extends WrappedGoal {

    public TemptGoal(EntityCreature mob, double speed, Ingredient items, boolean canScare) {
        super(new EntityAITempt(mob, speed, items.firstItem(), canScare));
    }
}
