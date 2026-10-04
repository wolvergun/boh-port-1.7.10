package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class DesireEdgeLivingEntityIsHitWithToolProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null && Math.random() < 0.5 && M.isEmptyBlock(world, BlockPos.containing(M.getX(entity), M.getY(entity), M.getZ(entity)))) {
            M.setBlock(world, BlockPos.containing(M.getX(entity), M.getY(entity), M.getZ(entity)), M.defaultBlockState(BohModBlocks.TWIG_TRAP.get()), 3);
            M.levelEvent(world, 2001, BlockPos.containing(x, y, z), M.blockStateId(M.defaultBlockState(BohModBlocks.TWIG_TRAP.get())));
        }
    }
}
