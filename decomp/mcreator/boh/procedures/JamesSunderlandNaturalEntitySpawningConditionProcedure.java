package net.mcreator.boh.procedures;

import net.mcreator.boh.network.BohModVariables;
import net.minecraft.world.level.LevelAccessor;

public class JamesSunderlandNaturalEntitySpawningConditionProcedure {
   public static boolean execute(LevelAccessor world) {
      return BohModVariables.MapVariables.get(world).spawn_james == 0.0;
   }
}
