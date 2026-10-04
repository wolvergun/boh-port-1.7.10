package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.DeerEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class DeerOnEntityTickUpdateProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (Math.random() < 2.0E-4) {
            if (entity instanceof DeerEntity) {
               ((DeerEntity)entity).setAnimation("eat");
            }

            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 254, false, false));
            }
         }

         if (entity instanceof LivingEntity _livEnt2 && _livEnt2.isBaby() && entity instanceof DeerEntity animatable) {
            animatable.setTexture("doe");
         }

         if (entity instanceof LivingEntity _livEnt4 && _livEnt4.hasEffect(MobEffects.MOVEMENT_SPEED)) {
            entity.setShiftKeyDown(true);
         }

         if (!(entity instanceof LivingEntity _livEnt6 && _livEnt6.hasEffect(MobEffects.MOVEMENT_SPEED))) {
            entity.setShiftKeyDown(false);
         }
      }
   }
}
