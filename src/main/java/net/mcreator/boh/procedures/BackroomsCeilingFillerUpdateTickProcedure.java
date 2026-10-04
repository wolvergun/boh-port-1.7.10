package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.world.Dimensions;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BackroomsCeilingFillerUpdateTickProcedure {
    public static void execute(World world, double x, double y, double z) {
        if ((world instanceof World ? M.dimension(world) : (world instanceof World ? M.dimension(M.getLevel(world)) : M.OVERWORLD))
            == Dimensions.dimensionKey(new ResourceLocation("boh:level_0"))) {
            if (M.isEmptyBlock(world, BlockPos.containing(x + 1.0, y, z))) {
                M.setBlock(world, BlockPos.containing(x + 1.0, y, z), M.defaultBlockState(BohModBlocks.BACKROOMS_CEILING_FILLER.get()), 3);
            } else if (M.isEmptyBlock(world, BlockPos.containing(x - 1.0, y, z))) {
                M.setBlock(world, BlockPos.containing(x - 1.0, y, z), M.defaultBlockState(BohModBlocks.BACKROOMS_CEILING_FILLER.get()), 3);
            } else if (M.isEmptyBlock(world, BlockPos.containing(x, y, z + 1.0))) {
                M.setBlock(world, BlockPos.containing(x, y, z + 1.0), M.defaultBlockState(BohModBlocks.BACKROOMS_CEILING_FILLER.get()), 3);
            } else if (M.isEmptyBlock(world, BlockPos.containing(x, y, z - 1.0))) {
                M.setBlock(world, BlockPos.containing(x, y, z - 1.0), M.defaultBlockState(BohModBlocks.BACKROOMS_CEILING_FILLER.get()), 3);
            }
        }
    }
}
