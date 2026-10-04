package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.RexyEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class RexyOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (!entity.getPersistentData().getBoolean("rexy_roar") && (entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player) {
            entity.getPersistentData().putBoolean("rexy_roar", true);
         }

         if (Math.random() < 0.1 && !entity.getPersistentData().getBoolean("twitch") && entity.getPersistentData().getBoolean("rexy_roar")) {
            if ((entity instanceof RexyEntity _datEntI ? (Integer)_datEntI.getEntityData().get(RexyEntity.DATA_Variant) : 0) == 0) {
               if (entity instanceof RexyEntity) {
                  ((RexyEntity)entity).setAnimation("roar");
               }

               if (world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_roar")),
                        SoundSource.HOSTILE,
                        3.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_roar")),
                        SoundSource.HOSTILE,
                        3.0F,
                        1.0F,
                        false
                     );
                  }
               }
            } else if ((entity instanceof RexyEntity _datEntI ? (Integer)_datEntI.getEntityData().get(RexyEntity.DATA_Variant) : 0) == 1) {
               if (entity instanceof RexyEntity) {
                  ((RexyEntity)entity).setAnimation("roar2");
               }

               if (world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_roar_novel")),
                        SoundSource.HOSTILE,
                        3.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_roar_novel")),
                        SoundSource.HOSTILE,
                        3.0F,
                        1.0F,
                        false
                     );
                  }
               }
            } else if ((entity instanceof RexyEntity _datEntI ? (Integer)_datEntI.getEntityData().get(RexyEntity.DATA_Variant) : 0) == 2) {
               if (entity instanceof RexyEntity) {
                  ((RexyEntity)entity).setAnimation("roar2");
               }

               if (world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rex_roar")),
                        SoundSource.HOSTILE,
                        3.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rex_roar")),
                        SoundSource.HOSTILE,
                        3.0F,
                        1.0F,
                        false
                     );
                  }
               }
            }

            entity.getPersistentData().putBoolean("twitch", true);
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 140, 254, false, false));
            }

            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 140, 254, false, false));
            }
         }

         if (!((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player)) {
            entity.getPersistentData().putBoolean("rexy_roar", false);
            entity.getPersistentData().putBoolean("twitch", false);
         }

         if (entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
            entity.getPersistentData().putDouble("timer_step", entity.getPersistentData().getDouble("timer_step") + 1.0);
         } else {
            entity.getPersistentData().putDouble("timer_step", 0.0);
         }

         if (entity instanceof Mob _mob && _mob.isAggressive()) {
            if (entity instanceof Mob _mobx && _mobx.isAggressive() && entity.getPersistentData().getDouble("timer_step") == 9.0) {
               if (!world.isClientSide() && world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")),
                        SoundSource.HOSTILE,
                        3.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")),
                        SoundSource.HOSTILE,
                        3.0F,
                        1.0F,
                        false
                     );
                  }
               }

               entity.getPersistentData().putDouble("timer_step", 0.0);
            }
         } else if (entity.getPersistentData().getDouble("timer_step") == 28.0) {
            if (!world.isClientSide() && world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")),
                     SoundSource.HOSTILE,
                     3.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")), SoundSource.HOSTILE, 3.0F, 1.0F, false
                  );
               }
            }

            entity.getPersistentData().putDouble("timer_step", 0.0);
         }

         if (!(entity instanceof Mob _mob && _mob.isAggressive()) && !(world instanceof Level _lvl37 && _lvl37.isDay())) {
            entity.setShiftKeyDown(true);
         }

         if (!(entity instanceof Mob _mob && _mob.isAggressive()) && world instanceof Level _lvl40 && _lvl40.isDay()) {
            entity.setShiftKeyDown(false);
         }

         if (entity instanceof Mob _mob && _mob.isAggressive() && !(world instanceof Level _lvl43 && _lvl43.isDay())) {
            entity.setShiftKeyDown(false);
         }

         if (entity.isShiftKeyDown()) {
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 254, false, false));
            }

            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
            }
         }

         if ((entity instanceof RexyEntity _datEntI ? (Integer)_datEntI.getEntityData().get(RexyEntity.DATA_Variant) : 0) == 2) {
            entity.setCustomName(Component.literal("Rex"));
         }
      }
   }
}
