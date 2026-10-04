package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;

public class DesireEdgeLivingEntityIsHitWithToolProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (Math.random() < 0.5 && world.isEmptyBlock(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()))) {
            world.setBlock(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), ((Block)BohModBlocks.TWIG_TRAP.get()).defaultBlockState(), 3);
            world.levelEvent(2001, BlockPos.containing(x, y, z), Block.getId(((Block)BohModBlocks.TWIG_TRAP.get()).defaultBlockState()));
         }
      }
   }
}
