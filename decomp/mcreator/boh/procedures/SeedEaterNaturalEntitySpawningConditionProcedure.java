package net.mcreator.boh.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;

public class SeedEaterNaturalEntitySpawningConditionProcedure {
   public static boolean execute(LevelAccessor world, double x, double y, double z) {
      return world instanceof ServerLevel _level0 && _level0.isVillage(BlockPos.containing(x, y, z));
   }
}
