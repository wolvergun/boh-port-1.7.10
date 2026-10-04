package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;

public class BackroomsCeilingFillerUpdateTickProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if ((world instanceof Level _lvl ? _lvl.dimension() : (world instanceof WorldGenLevel _wgl ? _wgl.getLevel().dimension() : Level.OVERWORLD))
         == ResourceKey.create(Registries.DIMENSION, new ResourceLocation("boh:level_0"))) {
         if (world.isEmptyBlock(BlockPos.containing(x + 1.0, y, z))) {
            world.setBlock(BlockPos.containing(x + 1.0, y, z), ((Block)BohModBlocks.BACKROOMS_CEILING_FILLER.get()).defaultBlockState(), 3);
         } else if (world.isEmptyBlock(BlockPos.containing(x - 1.0, y, z))) {
            world.setBlock(BlockPos.containing(x - 1.0, y, z), ((Block)BohModBlocks.BACKROOMS_CEILING_FILLER.get()).defaultBlockState(), 3);
         } else if (world.isEmptyBlock(BlockPos.containing(x, y, z + 1.0))) {
            world.setBlock(BlockPos.containing(x, y, z + 1.0), ((Block)BohModBlocks.BACKROOMS_CEILING_FILLER.get()).defaultBlockState(), 3);
         } else if (world.isEmptyBlock(BlockPos.containing(x, y, z - 1.0))) {
            world.setBlock(BlockPos.containing(x, y, z - 1.0), ((Block)BohModBlocks.BACKROOMS_CEILING_FILLER.get()).defaultBlockState(), 3);
         }
      }
   }
}
