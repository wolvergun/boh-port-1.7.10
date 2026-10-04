package net.mcreator.boh.procedures;

import net.minecraft.world.entity.Entity;

public class StaticClose3DisplayOverlayIngameProcedure {
   public static boolean execute(Entity entity) {
      return entity == null ? false : entity.getPersistentData().getDouble("static_slender") == 8.0;
   }
}
