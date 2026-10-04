package net.mcreator.boh.procedures;

import net.mcreator.boh.network.BohModVariables;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class SaucerNaturalEntitySpawningConditionProcedure {
   public static boolean execute(LevelAccessor world) {
      return world.getLevelData().isRaining() && !(world instanceof Level _lvl1 && _lvl1.isDay()) && BohModVariables.MapVariables.get(world).spawn_saucer == 0.0;
   }
}
