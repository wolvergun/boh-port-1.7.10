package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class PipeBurstBlockAddedProcedure {

    public static void execute(World world, double x, double y, double z) {
        if (world instanceof World _level) {
            M.updateNeighborsAt(_level, BlockPos.containing(x, y, z), M.getBlock(M.getBlockState(_level, BlockPos.containing(x, y, z))));
        }
    }
}
