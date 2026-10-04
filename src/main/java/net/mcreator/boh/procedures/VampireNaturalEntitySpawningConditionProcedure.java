package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.minecraft.world.World;

public class VampireNaturalEntitySpawningConditionProcedure {
    public static boolean execute(World world, double x, double y, double z) {
        return (world instanceof World ? M.dimension(world) : (world instanceof World ? M.dimension(M.getLevel(world)) : M.OVERWORLD)) == M.OVERWORLD
            && (!(world instanceof World) || !M.isDay(world))
            && M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z))) == Blocks.AIR
            && (
                M.getBlock(M.getBlockState(world, BlockPos.containing(x, y - 1.0, z))) == Blocks.PODZOL
                    || M.getBlock(M.getBlockState(world, BlockPos.containing(x, y - 1.0, z))) == Blocks.COARSE_DIRT
                    || M.getBlock(M.getBlockState(world, BlockPos.containing(x, y - 1.0, z))) == Blocks.GRASS_BLOCK
            );
    }
}
