package net.mcreator.boh.procedures;

import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.M;

public class MountedOnProcedure {

    public static boolean execute(Entity entity) {
        return entity == null ? false : M.isVehicle(entity);
    }
}
