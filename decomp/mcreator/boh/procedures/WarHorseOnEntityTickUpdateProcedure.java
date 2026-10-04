package net.mcreator.boh.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class WarHorseOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (entity.isVehicle() && entity.getDeltaMovement().x() != 0.0 && entity.getDeltaMovement().z() != 0.0) {
            entity.setSprinting(true);
         } else if (entity.getDeltaMovement().x() == 0.0 && entity.getDeltaMovement().z() == 0.0) {
            entity.setSprinting(false);
         }

         if (entity.isSprinting() && world.isEmptyBlock(BlockPos.containing(x, y, z)) && world.getBlockFloorHeight(BlockPos.containing(x, y - 1.0, z)) > 0.0) {
            world.setBlock(BlockPos.containing(x, y, z), Blocks.FIRE.defaultBlockState(), 3);
         }
      }
   }
}
