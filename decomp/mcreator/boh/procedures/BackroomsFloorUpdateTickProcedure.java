package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;

public class BackroomsFloorUpdateTickProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (world.canSeeSkyFromBelowWater(BlockPos.containing(x, y, z))) {
         world.setBlock(BlockPos.containing(x, y + 7.0, z), ((Block)BohModBlocks.BACKROOMS_CEILING_TILE.get()).defaultBlockState(), 3);
      }
   }
}
