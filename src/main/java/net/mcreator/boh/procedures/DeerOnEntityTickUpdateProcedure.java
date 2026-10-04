package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.entity.DeerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class DeerOnEntityTickUpdateProcedure {
    public static void execute(Entity entity) {
        if (entity != null) {
            if (Math.random() < 2.0E-4) {
                if (entity instanceof DeerEntity) {
                    ((DeerEntity)entity).setAnimation("eat");
                }

                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 60, 254, false, false));
                }
            }

            if (entity instanceof EntityLivingBase _livEnt2 && M.isBaby(_livEnt2) && entity instanceof DeerEntity animatable) {
                animatable.setTexture("doe");
            }

            if (entity instanceof EntityLivingBase _livEnt4 && M.hasEffect(_livEnt4, MobEffects.MOVEMENT_SPEED)) {
                M.setShiftKeyDown(entity, true);
            }

            if (!(entity instanceof EntityLivingBase _livEnt6 && M.hasEffect(_livEnt6, MobEffects.MOVEMENT_SPEED))) {
                M.setShiftKeyDown(entity, false);
            }
        }
    }
}
