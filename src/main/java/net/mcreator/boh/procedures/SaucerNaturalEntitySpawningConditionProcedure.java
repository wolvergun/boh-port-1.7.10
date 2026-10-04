package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.network.BohModVariables;
import net.minecraft.world.World;

public class SaucerNaturalEntitySpawningConditionProcedure {
    public static boolean execute(World world) {
        return M.isRaining(M.getLevelData(world))
            && (!(world instanceof World) || !M.isDay(world))
            && BohModVariables.MapVariables.get(world).spawn_saucer == 0.0;
    }
}
