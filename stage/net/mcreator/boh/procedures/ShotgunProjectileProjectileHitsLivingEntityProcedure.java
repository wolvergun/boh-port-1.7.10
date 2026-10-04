package net.mcreator.boh.procedures;

import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.M;

public class ShotgunProjectileProjectileHitsLivingEntityProcedure {

    public static void execute(Entity immediatesourceentity) {
        if (immediatesourceentity != null) {
            if (!M.isClientSide(M.level(immediatesourceentity))) {
                M.discard(immediatesourceentity);
            }
        }
    }
}
