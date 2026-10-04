package net.mcreator.boh.procedures;

import net.mcreator.boh.network.BohModVariables;
import net.minecraft.world.World;

public class GoldLostNaturalEntitySpawningConditionProcedure {
    public static boolean execute(World world) {
        return BohModVariables.MapVariables.get(world).spawn_gold == 0.0;
    }
}
