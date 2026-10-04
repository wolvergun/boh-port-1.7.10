package net.mcreator.boh.procedures;

import net.minecraft.world.entity.Entity;

public class ExeApparison4DisplayOverlayIngameProcedure {
   public static boolean execute(Entity entity) {
      return entity == null ? false : entity.getPersistentData().getDouble("exe_apparison") == 4.0;
   }
}
