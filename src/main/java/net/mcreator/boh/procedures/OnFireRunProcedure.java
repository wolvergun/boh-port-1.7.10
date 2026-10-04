package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.minecraft.entity.Entity;

public class OnFireRunProcedure {
    public static boolean execute(Entity entity) {
        return entity == null ? false : M.isOnFire(entity);
    }
}
