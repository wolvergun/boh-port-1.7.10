package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;

public class LifeformThisEntityKillsAnotherOneProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      world.setBlock(BlockPos.containing(x, y + 1.0, z), ((Block)BohModBlocks.LIFEFORM_GROWTH.get()).defaultBlockState(), 3);
   }
}
