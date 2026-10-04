package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.M;

public class VampireNaturalEntitySpawningConditionProcedure {

    public static boolean execute(World world, double x, double y, double z) {
        return (world instanceof World _lvl ? M.dimension(_lvl) : (world instanceof World _wgl ? M.dimension(M.getLevel(_wgl)) : M.OVERWORLD)) == M.OVERWORLD && !(world instanceof World _lvl3 && M.isDay(_lvl3)) && M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z))) == Blocks.AIR && (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y - 1.0, z))) == Blocks.PODZOL || M.getBlock(M.getBlockState(world, BlockPos.containing(x, y - 1.0, z))) == Blocks.COARSE_DIRT || M.getBlock(M.getBlockState(world, BlockPos.containing(x, y - 1.0, z))) == Blocks.GRASS_BLOCK);
    }
}
