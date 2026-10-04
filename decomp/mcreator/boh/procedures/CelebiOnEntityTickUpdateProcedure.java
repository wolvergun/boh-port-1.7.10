package net.mcreator.boh.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

public class CelebiOnEntityTickUpdateProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (entity instanceof Mob _mob && _mob.isAggressive()) {
            entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * 0.2, entity.getLookAngle().y * 0.9, entity.getLookAngle().z * 0.2));
            if (Math.random() < 0.5 && Math.random() < 0.025) {
               entity.setDeltaMovement(new Vec3(0.0, 1.0, 0.0));
            }
         }
      }
   }
}
