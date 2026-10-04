package net.mcreator.boh.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;

public class VampireNaturalEntitySpawningConditionProcedure {
   public static boolean execute(LevelAccessor world, double x, double y, double z) {
      return (world instanceof Level _lvl ? _lvl.dimension() : (world instanceof WorldGenLevel _wgl ? _wgl.getLevel().dimension() : Level.OVERWORLD))
            == Level.OVERWORLD
         && !(world instanceof Level _lvl3 && _lvl3.isDay())
         && world.getBlockState(BlockPos.containing(x, y, z)).getBlock() == Blocks.AIR
         && (
            world.getBlockState(BlockPos.containing(x, y - 1.0, z)).getBlock() == Blocks.PODZOL
               || world.getBlockState(BlockPos.containing(x, y - 1.0, z)).getBlock() == Blocks.COARSE_DIRT
               || world.getBlockState(BlockPos.containing(x, y - 1.0, z)).getBlock() == Blocks.GRASS_BLOCK
         );
   }
}
