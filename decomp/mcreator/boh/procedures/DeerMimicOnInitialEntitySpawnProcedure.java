package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.DeerMimicEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class DeerMimicOnInitialEntitySpawnProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         if (Math.random() < 0.5) {
            BohMod.queueServerWork(2, () -> {
               if (entity instanceof DeerMimicEntity animatable) {
                  animatable.setTexture("doe");
               }
            });
         }
      }
   }
}
