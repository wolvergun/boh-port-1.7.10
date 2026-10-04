package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.minecraft.entity.Entity;

public class TheWhisleOnEffectActiveTickProcedure {
    public static void execute(Entity entity) {
        if (entity != null) {
            M.putDouble(M.getPersistentData(entity), "yaw_skybox", M.getDouble(M.getPersistentData(entity), "yaw_skybox") - 1.0);
        }
    }
}
