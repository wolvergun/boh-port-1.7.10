package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.world.World;

public class BackroomsFloorUpdateTickProcedure {
    public static void execute(World world, double x, double y, double z) {
        if (M.canSeeSkyFromBelowWater(world, BlockPos.containing(x, y, z))) {
            M.setBlock(world, BlockPos.containing(x, y + 7.0, z), M.defaultBlockState(BohModBlocks.BACKROOMS_CEILING_TILE.get()), 3);
        }
    }
}
