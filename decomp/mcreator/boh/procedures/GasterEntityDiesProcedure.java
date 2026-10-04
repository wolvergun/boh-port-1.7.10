package net.mcreator.boh.procedures;

import net.mcreator.boh.network.BohModVariables;
import net.minecraft.world.level.LevelAccessor;

public class GasterEntityDiesProcedure {
   public static void execute(LevelAccessor world) {
      BohModVariables.MapVariables.get(world).spawn_gaster = 0.0;
      BohModVariables.MapVariables.get(world).syncData(world);
   }
}
