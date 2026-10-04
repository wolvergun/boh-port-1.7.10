package net.mcreator.boh.procedures;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;

public class GojibreathUpdateTickProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (Math.random() < 0.01 && world instanceof ServerLevel _level) {
         _level.sendParticles(ParticleTypes.LARGE_SMOKE, x + 0.5, y, z + 0.5, 1, 0.1, 0.1, 0.1, 0.0);
      }
   }
}
