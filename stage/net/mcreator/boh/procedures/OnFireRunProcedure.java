package net.mcreator.boh.procedures;

import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.M;

public class OnFireRunProcedure {

    public static boolean execute(Entity entity) {
        return entity == null ? false : M.isOnFire(entity);
    }
}
