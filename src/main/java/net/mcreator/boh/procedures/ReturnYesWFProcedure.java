package net.mcreator.boh.procedures;

import net.mcreator.boh.network.BohModVariables;
import net.minecraft.world.World;

public class ReturnYesWFProcedure {
    public static boolean execute(World world) {
        return BohModVariables.MapVariables.get(world).Kill_WF == 0.0;
    }
}
