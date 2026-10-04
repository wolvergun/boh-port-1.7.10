package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;

public class BoilerRoomDimensionPlayerLeavesDimensionProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         if (entity instanceof LivingEntity _entity) {
            _entity.removeAllEffects();
         }

         BohMod.queueServerWork(5, () -> entity.fallDistance = 0.0F);
      }
   }
}
