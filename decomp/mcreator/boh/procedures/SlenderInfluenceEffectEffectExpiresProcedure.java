package net.mcreator.boh.procedures;

import net.minecraft.world.entity.Entity;

public class SlenderInfluenceEffectEffectExpiresProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         entity.getPersistentData().putBoolean("expireeffect", false);
      }
   }
}
