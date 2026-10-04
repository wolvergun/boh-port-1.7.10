package net.mcreator.boh.procedures;

import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.M;

public class ReturnNPC10Procedure {

    public static boolean execute(Entity entity) {
        return entity == null ? false : M.getDouble(M.getPersistentData(entity), "npc_000_overlay") == 10.0;
    }
}
