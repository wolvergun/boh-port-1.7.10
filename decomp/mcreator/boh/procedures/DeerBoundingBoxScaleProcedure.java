package net.mcreator.boh.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class DeerBoundingBoxScaleProcedure {
   public static double execute(Entity entity) {
      if (entity == null) {
         return 0.0;
      } else {
         return entity instanceof LivingEntity _livEnt0 && _livEnt0.isBaby() ? 0.6 : 1.0;
      }
   }
}
