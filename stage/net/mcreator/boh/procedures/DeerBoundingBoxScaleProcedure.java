package net.mcreator.boh.procedures;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.mcreator.boh.compat.M;

public class DeerBoundingBoxScaleProcedure {

    public static double execute(Entity entity) {
        if (entity == null) {
            return 0.0;
        } else {
            return entity instanceof EntityLivingBase _livEnt0 && M.isBaby(_livEnt0) ? 0.6 : 1.0;
        }
    }
}
