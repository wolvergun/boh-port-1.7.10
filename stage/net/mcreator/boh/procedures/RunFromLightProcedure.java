package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class RunFromLightProcedure {

    public static boolean execute(World world, double x, double y, double z) {
        return M.getMaxLocalRawBrightness(world, BlockPos.containing(x, y, z)) >= 7;
    }
}
