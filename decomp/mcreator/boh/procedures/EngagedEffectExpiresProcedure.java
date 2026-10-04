package net.mcreator.boh.procedures;

import net.minecraft.world.entity.Entity;

public class EngagedEffectExpiresProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         entity.getPersistentData().putDouble("static_slender", 0.0);
      }
   }
}
