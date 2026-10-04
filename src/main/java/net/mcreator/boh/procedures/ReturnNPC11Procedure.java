package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.minecraft.entity.Entity;

public class ReturnNPC11Procedure {
    public static boolean execute(Entity entity) {
        return entity == null ? false : M.getDouble(M.getPersistentData(entity), "npc_000_overlay") == 11.0;
    }
}
