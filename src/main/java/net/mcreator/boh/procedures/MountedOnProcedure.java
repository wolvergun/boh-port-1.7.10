package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.minecraft.entity.Entity;

public class MountedOnProcedure {
    public static boolean execute(Entity entity) {
        return entity == null ? false : M.isVehicle(entity);
    }
}
