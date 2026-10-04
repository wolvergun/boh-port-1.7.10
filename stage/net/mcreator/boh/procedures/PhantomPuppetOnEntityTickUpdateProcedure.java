package net.mcreator.boh.procedures;

import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.M;

public class PhantomPuppetOnEntityTickUpdateProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            M.setDeltaMovement(entity, new Vec3(M.getLookAngle(entity).x * 0.45, M.getLookAngle(entity).y * 0.9, M.getLookAngle(entity).z * 0.45));
        }
    }
}
