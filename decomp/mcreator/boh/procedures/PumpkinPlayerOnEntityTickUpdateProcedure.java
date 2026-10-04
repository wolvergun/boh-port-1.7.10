package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class PumpkinPlayerOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      boolean YourCondition = false;
      if (Blocks.FARMLAND == world.getBlockState(BlockPos.containing(x, 1.0 - y, z)).getBlock() && Math.random() < 0.7 && world instanceof Level _level) {
         BlockPos _bp = BlockPos.containing(x, y, z);
         if ((BoneMealItem.growCrop(new ItemStack(Items.BONE_MEAL), _level, _bp) || BoneMealItem.growWaterPlant(new ItemStack(Items.BONE_MEAL), _level, _bp, null))
            && !_level.isClientSide()) {
            _level.levelEvent(2005, _bp, 0);
         }
      }

      if (world.isEmptyBlock(BlockPos.containing(x, 1.0 + y, z))) {
         world.setBlock(BlockPos.containing(x, 1.0 + y, z), ((Block)BohModBlocks.PUMPKIN_PLAYER_LIGHTSORUCE.get()).defaultBlockState(), 3);
      }
   }
}
