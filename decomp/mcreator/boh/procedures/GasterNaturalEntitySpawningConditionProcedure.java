package net.mcreator.boh.procedures;

import net.mcreator.boh.network.BohModVariables;
import net.minecraft.world.level.LevelAccessor;

public class GasterNaturalEntitySpawningConditionProcedure {
   public static boolean execute(LevelAccessor world) {
      return BohModVariables.MapVariables.get(world).spawn_gaster == 0.0;
   }
}
