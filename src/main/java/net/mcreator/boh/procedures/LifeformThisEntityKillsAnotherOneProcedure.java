package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.world.World;

public class LifeformThisEntityKillsAnotherOneProcedure {
    public static void execute(World world, double x, double y, double z) {
        M.setBlock(world, BlockPos.containing(x, y + 1.0, z), M.defaultBlockState(BohModBlocks.LIFEFORM_GROWTH.get()), 3);
    }
}
