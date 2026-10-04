package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.mcreator.boh.compat.M;

public class BackroomsFloorUpdateTickProcedure {

    public static void execute(World world, double x, double y, double z) {
        if (M.canSeeSkyFromBelowWater(world, BlockPos.containing(x, y, z))) {
            M.setBlock(world, BlockPos.containing(x, y + 7.0, z), M.defaultBlockState(((Block) BohModBlocks.BACKROOMS_CEILING_TILE.get())), 3);
        }
    }
}
