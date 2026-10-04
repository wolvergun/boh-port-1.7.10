package net.mcreator.boh.procedures;

import net.minecraft.world.entity.Entity;

public class TheWhisleOnEffectActiveTickProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         entity.getPersistentData().putDouble("yaw_skybox", entity.getPersistentData().getDouble("yaw_skybox") - 1.0);
      }
   }
}
