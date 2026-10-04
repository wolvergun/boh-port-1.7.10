package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.minecraft.entity.Entity;

public class StaticClose2DisplayOverlayIngameProcedure {
    public static boolean execute(Entity entity) {
        return entity == null ? false : M.getDouble(M.getPersistentData(entity), "static_slender") == 7.0;
    }
}
