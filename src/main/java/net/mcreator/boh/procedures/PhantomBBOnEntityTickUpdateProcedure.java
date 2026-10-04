package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.Entity;

public class PhantomBBOnEntityTickUpdateProcedure {
    public static void execute(Entity entity) {
        if (entity != null) {
            M.setDeltaMovement(entity, new Vec3(M.getLookAngle(entity).x * 0.3, M.getLookAngle(entity).y * 0.9, M.getLookAngle(entity).z * 0.3));
        }
    }
}
