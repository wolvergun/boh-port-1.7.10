package net.mcreator.boh.procedures;

import net.minecraft.world.entity.Entity;

public class NPC000influenceOnEffectActiveTickProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (entity.getPersistentData().getDouble("npc_000_overlay") <= 17.0) {
            entity.getPersistentData().putDouble("npc_000_overlay", entity.getPersistentData().getDouble("npc_000_overlay") + 1.0);
         }

         if (entity.getPersistentData().getDouble("npc_000_overlay") == 17.0) {
            entity.getPersistentData().putDouble("npc_000_overlay", 1.0);
         }
      }
   }
}
