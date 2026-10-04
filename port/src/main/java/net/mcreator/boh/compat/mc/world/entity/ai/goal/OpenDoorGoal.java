package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIOpenDoor;

public class OpenDoorGoal extends WrappedGoal {

    public OpenDoorGoal(EntityLiving mob, boolean closeDoor) {
        super(new EntityAIOpenDoor(mob, closeDoor));
        mob.getNavigator().setEnterDoors(true);
    }
}
