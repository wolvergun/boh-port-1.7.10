package net.mcreator.boh.procedures;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.mcreator.boh.compat.M;

public class CoverYourEarsOnEffectActiveTickProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            Entity _ent = entity;
            M.setYRot(_ent, M.getYRot(entity));
            M.setXRot(_ent, (float) (M.getXRot(entity) - 0.1));
            M.setYBodyRot(_ent, M.getYRot(_ent));
            M.setYHeadRot(_ent, M.getYRot(_ent));
            M.set_yRotO(_ent, M.getYRot(_ent));
            M.set_xRotO(_ent, M.getXRot(_ent));
            if (_ent instanceof EntityLivingBase _entity) {
                M.set_yBodyRotO(_entity, M.getYRot(_entity));
                M.set_yHeadRotO(_entity, M.getYRot(_entity));
            }
        }
    }
}
