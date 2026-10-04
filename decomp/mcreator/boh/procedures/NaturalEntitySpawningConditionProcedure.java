package net.mcreator.boh.procedures;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;

public class NaturalEntitySpawningConditionProcedure {
   public static boolean execute(LevelAccessor world) {
      return (world instanceof Level _lvl ? _lvl.dimension() : (world instanceof WorldGenLevel _wgl ? _wgl.getLevel().dimension() : Level.OVERWORLD))
         == Level.OVERWORLD;
   }
}
