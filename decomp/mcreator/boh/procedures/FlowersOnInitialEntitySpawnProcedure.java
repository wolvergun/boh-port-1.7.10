package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.FlowersEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class FlowersOnInitialEntitySpawnProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         BohMod.queueServerWork(1, () -> {
            if (entity instanceof FlowersEntity) {
               ((FlowersEntity)entity).setAnimation("spawn");
            }
         });
      }
   }
}
