package net.mcreator.boh.procedures;

import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.M;

public class TheGreatKnifeLivingEntityIsHitWithItemProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            M.setDeltaMovement(entity, new Vec3(M.getDeltaMovement(entity).x() * 2.5, M.getDeltaMovement(entity).y(), M.getDeltaMovement(entity).z() * 2.5));
        }
    }
}
