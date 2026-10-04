package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class SeedEaterNaturalEntitySpawningConditionProcedure {
    public static boolean execute(World world, double x, double y, double z) {
        return world instanceof WorldServer _level0 && M.isVillage(_level0, BlockPos.containing(x, y, z));
    }
}
