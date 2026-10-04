package net.mcreator.boh.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class Unown1OnEntityTickUpdateProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * 0.5, entity.getLookAngle().y * 0.9, entity.getLookAngle().z * 0.5));
         if (Math.random() < 0.25) {
            if (Math.random() < 0.025) {
               entity.setDeltaMovement(new Vec3(0.0, 1.0, 0.0));
            } else if (Math.random() < 0.025) {
               entity.setDeltaMovement(new Vec3(0.0, -1.0, 0.0));
            }
         }
      }
   }
}
