package net.mcreator.boh.procedures;

import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.M;

public class SlenderInfluenceEffectEffectExpiresProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            M.putBoolean(M.getPersistentData(entity), "expireeffect", false);
        }
    }
}
