package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.FlowersEntity;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class FlowersOnInitialEntitySpawnProcedure {
    public static void execute(World world, Entity entity) {
        if (entity != null) {
            BohMod.queueServerWork(1, () -> {
                if (entity instanceof FlowersEntity) {
                    ((FlowersEntity)entity).setAnimation("spawn");
                }
            });
        }
    }
}
