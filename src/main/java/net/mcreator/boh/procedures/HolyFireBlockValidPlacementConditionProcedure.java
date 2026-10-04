package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.world.World;

public class HolyFireBlockValidPlacementConditionProcedure {
    public static boolean execute(World world, double x, double y, double z) {
        return M.canOcclude(M.getBlockState(world, BlockPos.containing(x, y - 1.0, z)));
    }
}
