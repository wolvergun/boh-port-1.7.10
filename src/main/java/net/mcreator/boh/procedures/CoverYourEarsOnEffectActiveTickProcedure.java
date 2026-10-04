package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class CoverYourEarsOnEffectActiveTickProcedure {
    public static void execute(Entity entity) {
        if (entity != null) {
            M.setYRot(entity, M.getYRot(entity));
            M.setXRot(entity, (float)(M.getXRot(entity) - 0.1));
            M.setYBodyRot(entity, M.getYRot(entity));
            M.setYHeadRot(entity, M.getYRot(entity));
            M.set_yRotO(entity, M.getYRot(entity));
            M.set_xRotO(entity, M.getXRot(entity));
            if (entity instanceof EntityLivingBase _entity) {
                M.set_yBodyRotO(_entity, M.getYRot(_entity));
                M.set_yHeadRotO(_entity, M.getYRot(_entity));
            }
        }
    }
}
