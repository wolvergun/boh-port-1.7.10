package net.mcreator.boh.procedures;

import net.minecraft.world.entity.Entity;

public class Staticfar5DisplayOverlayIngameProcedure {
   public static boolean execute(Entity entity) {
      return entity == null ? false : entity.getPersistentData().getDouble("static_slender") == 5.0;
   }
}
