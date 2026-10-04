package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import java.util.function.Predicate;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBreakDoor;

public class BreakDoorGoal extends WrappedGoal {
    protected final EntityLiving mob;

    public BreakDoorGoal(EntityLiving mob, Predicate<?> validDifficulties) {
        super(new EntityAIBreakDoor(mob));
        this.mob = mob;
        mob.getNavigator().setBreakDoors(true);
    }

    public BreakDoorGoal(EntityLiving mob, int doorBreakTime, Predicate<?> validDifficulties) {
        this(mob, validDifficulties);
    }
}
