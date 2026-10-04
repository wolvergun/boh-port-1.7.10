package net.mcreator.boh.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;

public class TwigTrapOnTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      world.destroyBlock(BlockPos.containing(x, y, z), false);
   }
}
