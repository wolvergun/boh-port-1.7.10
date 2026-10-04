package net.mcreator.boh.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class PhantomPuppetOnEntityTickUpdateProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * 0.45, entity.getLookAngle().y * 0.9, entity.getLookAngle().z * 0.45));
      }
   }
}
