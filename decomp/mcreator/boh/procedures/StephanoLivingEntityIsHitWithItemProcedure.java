package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class StephanoLivingEntityIsHitWithItemProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         if (world instanceof ServerLevel _level) {
            _level.sendParticles(
               (SimpleParticleType)BohModParticleTypes.GOLD_HIT.get(),
               entity.getX(),
               entity.getY() + entity.getBbHeight() / 2.0F,
               entity.getZ(),
               1,
               0.0,
               0.0,
               0.0,
               0.0
            );
         }
      }
   }
}
