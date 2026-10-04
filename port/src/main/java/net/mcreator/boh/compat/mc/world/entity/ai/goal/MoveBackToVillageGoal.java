package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;

public class MoveBackToVillageGoal extends WrappedGoal {

    public MoveBackToVillageGoal(EntityCreature mob, double speed, boolean checkNoActionTime) {
        super(new EntityAIMoveThroughVillage(mob, speed, false));
    }
}
