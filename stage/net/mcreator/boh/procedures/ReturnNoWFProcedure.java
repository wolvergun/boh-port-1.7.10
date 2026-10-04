package net.mcreator.boh.procedures;

import net.mcreator.boh.network.BohModVariables;
import net.minecraft.world.World;

public class ReturnNoWFProcedure {

    public static boolean execute(World world) {
        return BohModVariables.MapVariables.get(world).Kill_WF == 1.0;
    }
}
