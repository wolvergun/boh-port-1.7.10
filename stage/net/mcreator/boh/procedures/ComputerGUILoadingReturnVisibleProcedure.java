package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.IntegerProperty;
import net.mcreator.boh.compat.M;

public class ComputerGUILoadingReturnVisibleProcedure {

    public static boolean execute(World world, double x, double y, double z) {
        return (M.getProperty(M.getStateDefinition(M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z)))), "downloading_time") instanceof IntegerProperty _getip1 ? (Integer) M.getBlockState(world, BlockPos.containing(x, y, z)).getValue(_getip1) : -1) != 0;
    }
}
