package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.minecraft.entity.Entity;

public class EngagedEffectStartedappliedProcedure {
    public static void execute(Entity entity) {
        if (entity != null) {
            M.putDouble(M.getPersistentData(entity), "static_slender", 0.0);
        }
    }
}
