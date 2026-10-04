package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.entity.DeerEntity;
import net.minecraft.entity.Entity;

public class DeerEntityVisualScaleProcedure {
    public static double execute(Entity entity) {
        if (entity == null) {
            return 0.0;
        } else {
            return entity instanceof DeerEntity _datEntI ? M.getEntityData(_datEntI).get(DeerEntity.DATA_scale).intValue() : 0.0;
        }
    }
}
