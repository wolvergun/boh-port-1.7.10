package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.minecraft.entity.Entity;

public class HypnoShotProjectileProjectileHitsLivingEntityProcedure {
    public static void execute(Entity immediatesourceentity) {
        if (immediatesourceentity != null && !M.isClientSide(M.level(immediatesourceentity))) {
            M.discard(immediatesourceentity);
        }
    }
}
