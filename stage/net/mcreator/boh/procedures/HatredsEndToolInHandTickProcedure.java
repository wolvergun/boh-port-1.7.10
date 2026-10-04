package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.mcreator.boh.compat.M;

public class HatredsEndToolInHandTickProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            if ((entity instanceof EntityLivingBase _livEnt ? M.getHealth(_livEnt) : -1.0F) < (entity instanceof EntityLivingBase _livEnt ? M.getMaxHealth(_livEnt) : -1.0F) / 2.0F) {
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.DAMAGE_BOOST, 20, 1));
                }
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SPEED, 20, 1));
                }
            }
        }
    }
}
