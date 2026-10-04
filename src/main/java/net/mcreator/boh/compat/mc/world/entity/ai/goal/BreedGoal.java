package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.passive.EntityAnimal;

public class BreedGoal extends WrappedGoal {
    public BreedGoal(EntityAnimal animal, double speed) {
        super(new EntityAIMate(animal, speed));
    }
}
