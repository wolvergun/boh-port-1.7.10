package net.mcreator.boh.procedures;

import net.mcreator.boh.network.BohModVariables;
import net.minecraft.world.World;

public class SimonhenrikssonEntityDiesProcedure {
    public static void execute(World world) {
        BohModVariables.MapVariables.get(world).spawn_simon = 0.0;
        BohModVariables.MapVariables.get(world).syncData(world);
    }
}
