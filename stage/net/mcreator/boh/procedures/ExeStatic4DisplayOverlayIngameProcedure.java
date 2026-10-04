package net.mcreator.boh.procedures;

import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.M;

public class ExeStatic4DisplayOverlayIngameProcedure {

    public static boolean execute(Entity entity) {
        return entity == null ? false : M.getDouble(M.getPersistentData(entity), "exe_static") == 4.0;
    }
}
