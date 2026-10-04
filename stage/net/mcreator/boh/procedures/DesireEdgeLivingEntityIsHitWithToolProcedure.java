package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.mcreator.boh.compat.M;

public class DesireEdgeLivingEntityIsHitWithToolProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (Math.random() < 0.5 && M.isEmptyBlock(world, BlockPos.containing(M.getX(entity), M.getY(entity), M.getZ(entity)))) {
                M.setBlock(world, BlockPos.containing(M.getX(entity), M.getY(entity), M.getZ(entity)), M.defaultBlockState(((Block) BohModBlocks.TWIG_TRAP.get())), 3);
                M.levelEvent(world, 2001, BlockPos.containing(x, y, z), M.blockStateId(M.defaultBlockState(((Block) BohModBlocks.TWIG_TRAP.get()))));
            }
        }
    }
}
