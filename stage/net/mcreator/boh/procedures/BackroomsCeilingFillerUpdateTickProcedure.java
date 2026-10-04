package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.mcreator.boh.compat.M;

public class BackroomsCeilingFillerUpdateTickProcedure {

    public static void execute(World world, double x, double y, double z) {
        if ((world instanceof World _lvl ? M.dimension(_lvl) : (world instanceof World _wgl ? M.dimension(M.getLevel(_wgl)) : M.OVERWORLD)) == net.mcreator.boh.compat.world.Dimensions.dimensionKey( new ResourceLocation("boh:level_0"))) {
            if (M.isEmptyBlock(world, BlockPos.containing(x + 1.0, y, z))) {
                M.setBlock(world, BlockPos.containing(x + 1.0, y, z), M.defaultBlockState(((Block) BohModBlocks.BACKROOMS_CEILING_FILLER.get())), 3);
            } else if (M.isEmptyBlock(world, BlockPos.containing(x - 1.0, y, z))) {
                M.setBlock(world, BlockPos.containing(x - 1.0, y, z), M.defaultBlockState(((Block) BohModBlocks.BACKROOMS_CEILING_FILLER.get())), 3);
            } else if (M.isEmptyBlock(world, BlockPos.containing(x, y, z + 1.0))) {
                M.setBlock(world, BlockPos.containing(x, y, z + 1.0), M.defaultBlockState(((Block) BohModBlocks.BACKROOMS_CEILING_FILLER.get())), 3);
            } else if (M.isEmptyBlock(world, BlockPos.containing(x, y, z - 1.0))) {
                M.setBlock(world, BlockPos.containing(x, y, z - 1.0), M.defaultBlockState(((Block) BohModBlocks.BACKROOMS_CEILING_FILLER.get())), 3);
            }
        }
    }
}
