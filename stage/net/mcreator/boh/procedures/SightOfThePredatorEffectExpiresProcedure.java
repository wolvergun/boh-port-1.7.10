package net.mcreator.boh.procedures;

import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.M;

public class SightOfThePredatorEffectExpiresProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            M.putDouble(M.getPersistentData(entity), "predator_lockon", 0.0);
        }
    }
}
