package net.mcreator.boh.procedures;

import net.mcreator.boh.network.BohModVariables;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class SaucerNaturalEntitySpawningConditionProcedure {

    public static boolean execute(World world) {
        return M.isRaining(M.getLevelData(world)) && !(world instanceof World _lvl1 && M.isDay(_lvl1)) && BohModVariables.MapVariables.get(world).spawn_saucer == 0.0;
    }
}
