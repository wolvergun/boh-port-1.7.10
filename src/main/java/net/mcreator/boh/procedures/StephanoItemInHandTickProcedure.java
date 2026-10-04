package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class StephanoItemInHandTickProcedure {
    public static void execute(Entity entity) {
        if (entity != null
            && (entity instanceof EntityLivingBase _livEntx ? M.getHealth(_livEntx) : -1.0F)
                <= (entity instanceof EntityLivingBase _livEnt ? M.getMaxHealth(_livEnt) : -1.0F) / 5.0F) {
            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.DAMAGE_RESISTANCE, 100, 1));
            }

            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.NIGHT_VISION, 100, 1));
            }
        }
    }
}
