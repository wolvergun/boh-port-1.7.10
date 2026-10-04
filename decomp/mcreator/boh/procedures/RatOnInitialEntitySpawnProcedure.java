package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.RatEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;

public class RatOnInitialEntitySpawnProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         if (entity instanceof RatEntity) {
            ((RatEntity)entity).setAnimation("spawn");
         }

         if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
            _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 254, false, false));
         }

         BohMod.queueServerWork(2, () -> {
            if (Math.random() < 0.25) {
               entity.getPersistentData().putDouble("skin", 0.0);
               if (entity instanceof RatEntity animatable) {
                  animatable.setTexture("rat1");
               }
            } else if (Math.random() < 0.25) {
               entity.getPersistentData().putDouble("skin", 1.0);
               if (entity instanceof RatEntity animatable) {
                  animatable.setTexture("rat2");
               }
            } else {
               entity.getPersistentData().putDouble("skin", 2.0);
               if (entity instanceof RatEntity animatable) {
                  animatable.setTexture("rat3");
               }
            }
         });
      }
   }
}
