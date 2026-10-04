package net.mcreator.boh.procedures;

import net.minecraft.world.entity.Entity;

public class OnFireRunProcedure {
   public static boolean execute(Entity entity) {
      return entity == null ? false : entity.isOnFire();
   }
}
