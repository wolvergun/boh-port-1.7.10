package net.mcreator.boh.procedures;

import net.minecraft.world.entity.Entity;

public class ExeStatic1DisplayOverlayIngameProcedure {
   public static boolean execute(Entity entity) {
      return entity == null ? false : entity.getPersistentData().getDouble("exe_static") == 1.0;
   }
}
