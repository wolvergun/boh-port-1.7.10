package net.mcreator.boh.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class ComputerGuiLoadingReturnProcedure {
   public static double execute(LevelAccessor world, double x, double y, double z) {
      return world.getBlockState(BlockPos.containing(x, y, z)).getBlock().getStateDefinition().getProperty("downloading_time") instanceof IntegerProperty _getip1
         ? ((Integer)world.getBlockState(BlockPos.containing(x, y, z)).getValue(_getip1)).intValue()
         : -1.0;
   }
}
