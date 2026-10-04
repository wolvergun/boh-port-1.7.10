package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.mcreator.boh.compat.M;

public class HypnoShotProjectileProjectileHitsPlayerProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.CONFUSION, 400, 0, false, false));
            }
            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 400, 3, false, false));
            }
        }
    }
}
