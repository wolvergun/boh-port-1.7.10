package net.mcreator.boh.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class TheGreatKnifeLivingEntityIsHitWithItemProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         entity.setDeltaMovement(new Vec3(entity.getDeltaMovement().x() * 2.5, entity.getDeltaMovement().y(), entity.getDeltaMovement().z() * 2.5));
      }
   }
}
