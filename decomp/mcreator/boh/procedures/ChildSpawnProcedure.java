package net.mcreator.boh.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level.ExplosionInteraction;

public class ChildSpawnProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (entity instanceof LivingEntity _livEnt0 && _livEnt0.isBaby() && world instanceof Level _level && !_level.isClientSide()) {
            _level.explode(null, x, y, z, 4.0F, ExplosionInteraction.NONE);
         }
      }
   }
}
