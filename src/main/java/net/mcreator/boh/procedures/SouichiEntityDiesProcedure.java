package net.mcreator.boh.procedures;

import net.mcreator.boh.network.BohModVariables;
import net.minecraft.world.World;

public class SouichiEntityDiesProcedure {
    public static void execute(World world) {
        BohModVariables.MapVariables.get(world).spawn_souichi = 0.0;
        BohModVariables.MapVariables.get(world).syncData(world);
    }
}
