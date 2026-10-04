package net.mcreator.boh.procedures;

import net.minecraft.world.entity.Entity;

public class WanderTameProcedure {
   public static boolean execute(Entity entity) {
      return entity == null ? false : entity.getPersistentData().getDouble("state_ai") == 0.0;
   }
}
