package net.mcreator.boh.procedures;

import net.mcreator.boh.network.BohModVariables;
import net.minecraft.world.level.LevelAccessor;

public class GoldLostOnInitialEntitySpawnProcedure {
   public static void execute(LevelAccessor world) {
      BohModVariables.MapVariables.get(world).spawn_gold = 1.0;
      BohModVariables.MapVariables.get(world).syncData(world);
   }
}
