package net.mcreator.boh.procedures;

import net.mcreator.boh.network.BohModVariables;
import net.minecraft.world.World;

public class GasterNaturalEntitySpawningConditionProcedure {
    public static boolean execute(World world) {
        return BohModVariables.MapVariables.get(world).spawn_gaster == 0.0;
    }
}
