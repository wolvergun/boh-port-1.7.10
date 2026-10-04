package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.minecraft.entity.Entity;

public class WanderTameProcedure {
    public static boolean execute(Entity entity) {
        return entity == null ? false : M.getDouble(M.getPersistentData(entity), "state_ai") == 0.0;
    }
}
