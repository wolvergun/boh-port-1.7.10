package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.entity.TamedRatEntity;
import net.minecraft.entity.Entity;

public class TamedRatOnEntityTickUpdateProcedure {
    public static void execute(Entity entity) {
        if (entity != null) {
            if (M.getDouble(M.getPersistentData(entity), "skin") == 0.0) {
                if (entity instanceof TamedRatEntity animatable) {
                    animatable.setTexture("rat1_tamed");
                }
            } else if (M.getDouble(M.getPersistentData(entity), "skin") == 0.0) {
                if (entity instanceof TamedRatEntity animatable) {
                    animatable.setTexture("rat2_tamed");
                }
            } else if (M.getDouble(M.getPersistentData(entity), "skin") == 0.0 && entity instanceof TamedRatEntity animatable) {
                animatable.setTexture("rat3_tamed");
            }
        }
    }
}
