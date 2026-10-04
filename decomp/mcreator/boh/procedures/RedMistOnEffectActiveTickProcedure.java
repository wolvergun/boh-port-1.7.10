package net.mcreator.boh.procedures;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class RedMistOnEffectActiveTickProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         if (world.isClientSide()) {
            world.addParticle(
               ParticleTypes.ASH,
               entity.getX() + Mth.nextDouble(RandomSource.create(), -20.0, 20.0),
               entity.getY() + 5.0,
               entity.getZ() + Mth.nextDouble(RandomSource.create(), -20.0, 20.0),
               20.0,
               20.0,
               20.0
            );
            world.addParticle(
               ParticleTypes.ASH,
               entity.getX() + Mth.nextDouble(RandomSource.create(), -20.0, 20.0),
               entity.getY() + 5.0,
               entity.getZ() + Mth.nextDouble(RandomSource.create(), -20.0, 20.0),
               20.0,
               20.0,
               20.0
            );
            world.addParticle(
               ParticleTypes.ASH,
               entity.getX() + Mth.nextDouble(RandomSource.create(), -20.0, 20.0),
               entity.getY() + 5.0,
               entity.getZ() + Mth.nextDouble(RandomSource.create(), -20.0, 20.0),
               20.0,
               20.0,
               20.0
            );
            world.addParticle(
               ParticleTypes.ASH,
               entity.getX() + Mth.nextDouble(RandomSource.create(), -20.0, 20.0),
               entity.getY() + 5.0,
               entity.getZ() + Mth.nextDouble(RandomSource.create(), -20.0, 20.0),
               20.0,
               20.0,
               20.0
            );
            world.addParticle(
               ParticleTypes.ASH,
               entity.getX() + Mth.nextDouble(RandomSource.create(), -20.0, 20.0),
               entity.getY() + 5.0,
               entity.getZ() + Mth.nextDouble(RandomSource.create(), -20.0, 20.0),
               20.0,
               20.0,
               20.0
            );
            world.addParticle(
               ParticleTypes.ASH,
               entity.getX() + Mth.nextDouble(RandomSource.create(), -20.0, 20.0),
               entity.getY() + 5.0,
               entity.getZ() + Mth.nextDouble(RandomSource.create(), -20.0, 20.0),
               20.0,
               20.0,
               20.0
            );
         }
      }
   }
}
