package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.world.World;

public class PipeBurstBlockAddedProcedure {
    public static void execute(World world, double x, double y, double z) {
        if (world instanceof World) {
            M.updateNeighborsAt(world, BlockPos.containing(x, y, z), M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z))));
        }
    }
}
