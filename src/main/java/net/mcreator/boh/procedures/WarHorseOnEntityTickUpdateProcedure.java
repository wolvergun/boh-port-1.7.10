package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class WarHorseOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (M.isVehicle(entity) && M.getDeltaMovement(entity).x() != 0.0 && M.getDeltaMovement(entity).z() != 0.0) {
                M.setSprinting(entity, true);
            } else if (M.getDeltaMovement(entity).x() == 0.0 && M.getDeltaMovement(entity).z() == 0.0) {
                M.setSprinting(entity, false);
            }

            if (M.isSprinting(entity)
                && M.isEmptyBlock(world, BlockPos.containing(x, y, z))
                && M.getBlockFloorHeight(world, BlockPos.containing(x, y - 1.0, z)) > 0.0) {
                M.setBlock(world, BlockPos.containing(x, y, z), M.defaultBlockState(Blocks.FIRE), 3);
            }
        }
    }
}
