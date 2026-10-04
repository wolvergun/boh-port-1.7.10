package net.mcreator.boh.procedures;

import net.minecraft.world.entity.Entity;

public class SightOfThePredatorOnEffectActiveTickProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         entity.getPersistentData().putDouble("predator_lockon", entity.getPersistentData().getDouble("predator_lockon") + 1.0);
      }
   }
}
