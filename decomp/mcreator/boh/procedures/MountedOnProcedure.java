package net.mcreator.boh.procedures;

import net.minecraft.world.entity.Entity;

public class MountedOnProcedure {
   public static boolean execute(Entity entity) {
      return entity == null ? false : entity.isVehicle();
   }
}
