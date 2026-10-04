package net.mcreator.boh.procedures;

import net.minecraft.world.entity.Entity;

public class ReturnNPC10Procedure {
   public static boolean execute(Entity entity) {
      return entity == null ? false : entity.getPersistentData().getDouble("npc_000_overlay") == 10.0;
   }
}
