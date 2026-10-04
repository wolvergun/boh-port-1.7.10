package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class TwigTrapOnTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z) {
        M.destroyBlock(world, BlockPos.containing(x, y, z), false);
    }
}
