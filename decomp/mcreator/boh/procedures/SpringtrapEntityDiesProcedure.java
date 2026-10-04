package net.mcreator.boh.procedures;

import net.minecraft.world.entity.Entity;

public class SpringtrapEntityDiesProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         entity.getPersistentData().putBoolean("death_springtrap", true);
      }
   }
}
