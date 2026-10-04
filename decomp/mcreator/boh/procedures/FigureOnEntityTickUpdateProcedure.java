package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class FigureOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         Vec3 _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(10.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
            .toList()) {
            if (entityiterator instanceof Player
               && !entityiterator.isShiftKeyDown()
               && !(entity instanceof Mob _mob && _mob.isAggressive())
               && entity instanceof Mob _entity) {
               _entity.getNavigation().moveTo(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ(), 1.0);
            }
         }

         _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(2.5), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
            .toList()) {
            if (entityiterator instanceof Player
               && !(entity instanceof Mob _mob && _mob.isAggressive())
               && entity instanceof Mob _entity
               && entityiterator instanceof LivingEntity _ent) {
               _entity.setTarget(_ent);
            }
         }

         if (!(entity instanceof Mob _mob && _mob.isAggressive()) && Math.random() < 0.1 && Math.random() < 0.05 && world instanceof Level _level) {
            if (!_level.isClientSide()) {
               _level.playSound(
                  null,
                  BlockPos.containing(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:figure_idle")),
                  SoundSource.NEUTRAL,
                  (float)Mth.nextDouble(RandomSource.create(), 0.1, 1.5),
                  1.0F
               );
            } else {
               _level.playLocalSound(
                  x,
                  y,
                  z,
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:figure_idle")),
                  SoundSource.NEUTRAL,
                  (float)Mth.nextDouble(RandomSource.create(), 0.1, 1.5),
                  1.0F,
                  false
               );
            }
         }

         if (entity instanceof Mob _mob && _mob.isAggressive() && Math.random() < 0.1 && Math.random() < 0.05 && world instanceof Level _level) {
            if (!_level.isClientSide()) {
               _level.playSound(
                  null,
                  BlockPos.containing(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:figure_aggro")),
                  SoundSource.NEUTRAL,
                  (float)Mth.nextDouble(RandomSource.create(), 0.1, 1.5),
                  1.0F
               );
            } else {
               _level.playLocalSound(
                  x,
                  y,
                  z,
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:figure_aggro")),
                  SoundSource.NEUTRAL,
                  (float)Mth.nextDouble(RandomSource.create(), 0.1, 1.5),
                  1.0F,
                  false
               );
            }
         }

         if (!entity.getPersistentData().getBoolean("lines_michael") && (entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player) {
            entity.getPersistentData().putBoolean("lines_michael", true);
         }

         if (!entity.getPersistentData().getBoolean("throlgular") && entity.getPersistentData().getBoolean("lines_michael")) {
            if (world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:figure_scream")),
                     SoundSource.HOSTILE,
                     2.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:figure_scream")),
                     SoundSource.HOSTILE,
                     2.0F,
                     1.0F,
                     false
                  );
               }
            }

            entity.getPersistentData().putBoolean("throlgular", true);
         }

         if (!((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player)) {
            entity.getPersistentData().putBoolean("lines_michael", false);
            entity.getPersistentData().putBoolean("throlgular", false);
         }

         if (entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
            entity.getPersistentData().putDouble("timer_step", entity.getPersistentData().getDouble("timer_step") + 1.0);
         } else {
            entity.getPersistentData().putDouble("timer_step", 0.0);
         }

         if (entity instanceof Mob _mob && _mob.isAggressive()) {
            if (entity instanceof Mob _mobx && _mobx.isAggressive() && entity.getPersistentData().getDouble("timer_step") == 7.0) {
               if (!world.isClientSide() && world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:figure_step")),
                        SoundSource.HOSTILE,
                        1.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:figure_step")),
                        SoundSource.HOSTILE,
                        1.0F,
                        1.0F,
                        false
                     );
                  }
               }

               entity.getPersistentData().putDouble("timer_step", 0.0);
            }
         } else if (entity.getPersistentData().getDouble("timer_step") == 12.0) {
            if (!world.isClientSide() && world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:figure_step")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:figure_step")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }

            entity.getPersistentData().putDouble("timer_step", 0.0);
         }

         if (entity instanceof Mob _mob
            && _mob.isAggressive()
            && !(entity instanceof LivingEntity _livEnt45 && _livEnt45.hasEffect(MobEffects.SATURATION))
            && Math.random() < 0.02
            && Math.random() < 0.02
            && entity instanceof LivingEntity _entity
            && !_entity.level().isClientSide()) {
            _entity.addEffect(new MobEffectInstance(MobEffects.SATURATION, 30, 0, false, false));
         }
      }
   }
}
