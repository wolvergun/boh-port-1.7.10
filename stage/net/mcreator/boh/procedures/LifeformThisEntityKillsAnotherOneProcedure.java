package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.mcreator.boh.compat.M;

public class LifeformThisEntityKillsAnotherOneProcedure {

    public static void execute(World world, double x, double y, double z) {
        M.setBlock(world, BlockPos.containing(x, y + 1.0, z), M.defaultBlockState(((Block) BohModBlocks.LIFEFORM_GROWTH.get())), 3);
    }
}
