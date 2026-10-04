package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.PatrickBatemanEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class PatrickBatemanOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (entity instanceof Mob _mob
            && _mob.isAggressive()
            && Math.random() < 0.2
            && Math.random() < 0.2
            && Math.random() < 0.2
            && entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6
            && entity instanceof LivingEntity _entity
            && !_entity.level().isClientSide()) {
            _entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 60, 0, false, false));
         }

         if (entity instanceof LivingEntity _livEnt3 && _livEnt3.hasEffect(MobEffects.DAMAGE_BOOST)) {
            entity.getPersistentData().putBoolean("trigger", true);
            if (entity instanceof PatrickBatemanEntity) {
               ((PatrickBatemanEntity)entity).setAnimation("lunge");
            }

            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, 1, false, false));
            }

            BohMod.queueServerWork(20, () -> {
               if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                  _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 255, false, false));
               }

               if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                  _entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 255, false, false));
               }

               if (entity instanceof LivingEntity _entity) {
                  _entity.removeEffect(MobEffects.DAMAGE_BOOST);
               }

               entity.getPersistentData().putBoolean("trigger", false);
               entity.getPersistentData().putBoolean("line", false);
            });
         }

         if (entity.getPersistentData().getBoolean("trigger") && entity instanceof LivingEntity _livEnt14 && _livEnt14.hasEffect(MobEffects.DAMAGE_BOOST)) {
            entity.getPersistentData().putBoolean("line", true);
            if (world instanceof Level _level && _level.isClientSide()) {
               _level.playLocalSound(
                  x,
                  y,
                  z,
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:bateman_attack")),
                  SoundSource.HOSTILE,
                  1.0F,
                  1.0F,
                  false
               );
            }
         }

         if (entity.getPersistentData().getBoolean("line")) {
            entity.getPersistentData().putBoolean("line", false);
         }
      }
   }
}
