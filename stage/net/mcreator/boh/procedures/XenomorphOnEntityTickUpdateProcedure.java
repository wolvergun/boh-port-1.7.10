package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.mcreator.boh.compat.M;

public class XenomorphOnEntityTickUpdateProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            if (entity instanceof EntityLivingBase _entity) {
                M.removeEffect(_entity, MobEffects.WITHER);
            }
            if (entity instanceof EntityLivingBase _entity) {
                M.removeEffect(_entity, MobEffects.POISON);
            }
            if (Math.random() < 0.06 && entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.DAMAGE_BOOST, 60, 0, false, false));
            }
        }
    }
}
