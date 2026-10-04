package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.SirenHeadEntity;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class SirenheadOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if ((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof LivingEntity && Math.random() < 0.005 && world instanceof Level _level) {
            if (!_level.isClientSide()) {
               _level.playSound(
                  null,
                  BlockPos.containing(x, y + 30.0, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sirenhead_siren")),
                  SoundSource.HOSTILE,
                  10.0F,
                  1.0F
               );
            } else {
               _level.playLocalSound(
                  x,
                  y + 30.0,
                  z,
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sirenhead_siren")),
                  SoundSource.HOSTILE,
                  10.0F,
                  1.0F,
                  false
               );
            }
         }

         Entity _ent = entity;
         if (!_ent.level().isClientSide() && _ent.getServer() != null) {
            _ent.getServer()
               .getCommands()
               .performPrefixedCommand(
                  new CommandSourceStack(
                     CommandSource.NULL,
                     _ent.position(),
                     _ent.getRotationVector(),
                     _ent.level() instanceof ServerLevel ? (ServerLevel)_ent.level() : null,
                     4,
                     _ent.getName().getString(),
                     _ent.getDisplayName(),
                     _ent.level().getServer(),
                     _ent
                  ),
                  "/fill ~-1 ~ ~-1 ~1 ~9 ~1 air replace #minecraft:leaves"
               );
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
                        2.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")),
                        SoundSource.HOSTILE,
                        2.0F,
                        1.0F,
                        false
                     );
                  }
               }

               entity.getPersistentData().putDouble("timer_step", 0.0);
            }
         } else if (entity.getPersistentData().getDouble("timer_step") == 22.0) {
            if (!world.isClientSide() && world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")),
                     SoundSource.HOSTILE,
                     2.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")), SoundSource.HOSTILE, 2.0F, 1.0F, false
                  );
               }
            }

            entity.getPersistentData().putDouble("timer_step", 0.0);
         }

         if (!(entity instanceof SirenHeadEntity _datEntL18 && (Boolean)_datEntL18.getEntityData().get(SirenHeadEntity.DATA_sonicboom_logic))) {
            if (entity instanceof Mob _mob
               && _mob.isAggressive()
               && !(entity instanceof SirenHeadEntity _datEntL20 && (Boolean)_datEntL20.getEntityData().get(SirenHeadEntity.DATA_sonicboom))
               && !world.getEntitiesOfClass(LivingEntity.class, AABB.ofSize(new Vec3(x, y, z), 13.0, 13.0, 13.0), e -> true).isEmpty()
               && Math.random() < 0.02
               && entity instanceof SirenHeadEntity _datEntSetL) {
               _datEntSetL.getEntityData().set(SirenHeadEntity.DATA_sonicboom, true);
            }

            if (entity instanceof SirenHeadEntity _datEntL23 && (Boolean)_datEntL23.getEntityData().get(SirenHeadEntity.DATA_sonicboom)) {
               if (entity instanceof SirenHeadEntity _datEntSetL) {
                  _datEntSetL.getEntityData().set(SirenHeadEntity.DATA_sonicboom_logic, true);
               }

               BohMod.queueServerWork(
                  9,
                  () -> {
                     if (world instanceof Level _level) {
                        if (!_level.isClientSide()) {
                           _level.playSound(
                              null,
                              BlockPos.containing(x, y, z),
                              (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sirenhead_sonicboom")),
                              SoundSource.HOSTILE,
                              2.0F,
                              0.5F
                           );
                        } else {
                           _level.playLocalSound(
                              x,
                              y,
                              z,
                              (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sirenhead_sonicboom")),
                              SoundSource.HOSTILE,
                              2.0F,
                              0.5F,
                              false
                           );
                        }
                     }
                  }
               );
               if (world instanceof ServerLevel _level) {
                  Entity entityToSpawn = ((EntityType)BohModEntities.SONIC_BOOM.get())
                     .spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                  if (entityToSpawn != null) {
                  }
               }

               if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                  _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 75, 254, false, false));
               }

               if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                  _entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 75, 255, false, false));
               }

               if (entity instanceof SirenHeadEntity) {
                  ((SirenHeadEntity)entity).setAnimation("stun");
               }

               Vec3 _center = new Vec3(x, y, z);

               for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(7.5), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                  .toList()) {
                  if (!(entityiterator instanceof SirenHeadEntity)) {
                     if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                        _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 250, 2, false, false));
                     }

                     if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                        _entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 250, 0, false, false));
                     }
                  }
               }

               BohMod.queueServerWork(200, () -> {
                  if (entity instanceof SirenHeadEntity _datEntSetL) {
                     _datEntSetL.getEntityData().set(SirenHeadEntity.DATA_sonicboom_logic, false);
                  }

                  if (entity instanceof SirenHeadEntity _datEntSetL) {
                     _datEntSetL.getEntityData().set(SirenHeadEntity.DATA_sonicboom, false);
                  }
               });
            }
         }
      }
   }
}
