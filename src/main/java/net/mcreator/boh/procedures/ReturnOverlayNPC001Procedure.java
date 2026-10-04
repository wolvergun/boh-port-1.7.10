package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.minecraft.entity.Entity;

public class ReturnOverlayNPC001Procedure {
    public static double execute(Entity entity) {
        return entity == null ? 0.0 : M.getDouble(M.getPersistentData(entity), "npc_000_overlay");
    }
}
