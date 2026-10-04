package net.mcreator.boh.procedures;

import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class NaturalEntitySpawningConditionProcedure {

    public static boolean execute(World world) {
        return (world instanceof World _lvl ? M.dimension(_lvl) : (world instanceof World _wgl ? M.dimension(M.getLevel(_wgl)) : M.OVERWORLD)) == M.OVERWORLD;
    }
}
