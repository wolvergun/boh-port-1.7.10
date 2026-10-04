package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.minecraft.entity.Entity;

public class NPC000influenceOnEffectActiveTickProcedure {
    public static void execute(Entity entity) {
        if (entity != null) {
            if (M.getDouble(M.getPersistentData(entity), "npc_000_overlay") <= 17.0) {
                M.putDouble(M.getPersistentData(entity), "npc_000_overlay", M.getDouble(M.getPersistentData(entity), "npc_000_overlay") + 1.0);
            }

            if (M.getDouble(M.getPersistentData(entity), "npc_000_overlay") == 17.0) {
                M.putDouble(M.getPersistentData(entity), "npc_000_overlay", 1.0);
            }
        }
    }
}
