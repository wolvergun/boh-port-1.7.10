package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.minecraft.entity.Entity;

public class SightOfThePredatorOnEffectActiveTickProcedure {
    public static void execute(Entity entity) {
        if (entity != null) {
            M.putDouble(M.getPersistentData(entity), "predator_lockon", M.getDouble(M.getPersistentData(entity), "predator_lockon") + 1.0);
        }
    }
}
