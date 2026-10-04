package net.mcreator.boh.procedures;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.M;

public class CelebiOnEntityTickUpdateProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            if (entity instanceof EntityLiving _mob && M.isAggressive(_mob)) {
                M.setDeltaMovement(entity, new Vec3(M.getLookAngle(entity).x * 0.2, M.getLookAngle(entity).y * 0.9, M.getLookAngle(entity).z * 0.2));
                if (Math.random() < 0.5 && Math.random() < 0.025) {
                    M.setDeltaMovement(entity, new Vec3(0.0, 1.0, 0.0));
                }
            }
        }
    }
}
