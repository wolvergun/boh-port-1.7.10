package net.mcreator.boh.procedures;

import net.minecraft.world.entity.Entity;

public class RayGunProjectileProjectileProjectileHitsLivingEntityProcedure {
   public static void execute(Entity immediatesourceentity) {
      if (immediatesourceentity != null) {
         if (!immediatesourceentity.level().isClientSide()) {
            immediatesourceentity.discard();
         }
      }
   }
}
