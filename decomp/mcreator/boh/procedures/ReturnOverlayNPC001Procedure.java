package net.mcreator.boh.procedures;

import net.minecraft.world.entity.Entity;

public class ReturnOverlayNPC001Procedure {
   public static double execute(Entity entity) {
      return entity == null ? 0.0 : entity.getPersistentData().getDouble("npc_000_overlay");
   }
}
