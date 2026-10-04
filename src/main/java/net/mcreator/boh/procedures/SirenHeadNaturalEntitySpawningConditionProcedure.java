package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.network.BohModVariables;
import net.minecraft.world.World;

public class SirenHeadNaturalEntitySpawningConditionProcedure {
    public static boolean execute(World world, double x, double y, double z) {
        return M.getBlock(M.getBlockState(world, BlockPos.containing(x, y - 1.0, z))) == Blocks.GRASS_BLOCK
            && BohModVariables.MapVariables.get(world).spawn_siren == 0.0;
    }
}
