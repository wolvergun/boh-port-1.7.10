package net.mcreator.boh.procedures;

import net.minecraft.world.entity.Entity;

public class SightOfThePredatorEffectExpiresProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         entity.getPersistentData().putDouble("predator_lockon", 0.0);
      }
   }
}
