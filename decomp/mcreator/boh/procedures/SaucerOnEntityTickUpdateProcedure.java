package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.GrayAlienEntity;
import net.mcreator.boh.entity.MartianDroneEntity;
import net.mcreator.boh.entity.SaucerEntity;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class SaucerOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (Math.random() < 0.005) {
            Entity _ent = entity;
            _ent.teleportTo(
               entity.getX() + Mth.nextDouble(RandomSource.create(), -5.0, 5.0),
               entity.getY(),
               entity.getZ() + Mth.nextDouble(RandomSource.create(), -5.0, 5.0)
            );
            if (_ent instanceof ServerPlayer _serverPlayer) {
               _serverPlayer.connection
                  .teleport(
                     entity.getX() + Mth.nextDouble(RandomSource.create(), -5.0, 5.0),
                     entity.getY(),
                     entity.getZ() + Mth.nextDouble(RandomSource.create(), -5.0, 5.0),
                     _ent.getYRot(),
                     _ent.getXRot()
                  );
            }
         }

         if (!world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, entity.getY() - 20.0, z), 4.0, 4.0, 4.0), e -> true).isEmpty()
            || !world.getEntitiesOfClass(Cow.class, AABB.ofSize(new Vec3(x, entity.getY() - 20.0, z), 4.0, 4.0, 4.0), e -> true).isEmpty()) {
            Vec3 _center = new Vec3(x, entity.getY() - Mth.nextDouble(RandomSource.create(), 15.0, 35.0), z);

            for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(2.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
               .toList()) {
               if (entityiterator instanceof Cow || entityiterator instanceof Player) {
                  if (entity instanceof SaucerEntity) {
                     ((SaucerEntity)entity).setAnimation("abduction");
                  }

                  if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                     _entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 30, 4, false, false));
                  }
               }
            }
         }

         if (Math.random() < 0.01) {
            if (world instanceof ServerLevel _level) {
               Entity entityToSpawn = ((EntityType)BohModEntities.GRAY_ALIEN.get())
                  .spawn(_level, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), MobSpawnType.MOB_SUMMONED);
               if (entityToSpawn != null) {
                  entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
               }
            }

            BohMod.queueServerWork(
               4,
               () -> {
                  Vec3 _center = new Vec3(x, y, z);

                  for (Entity entityiteratorx : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(50.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                     .toList()) {
                     if (entityiteratorx instanceof GrayAlienEntity) {
                        entityiteratorx.getPersistentData().putBoolean("spawn_thru_ship", true);
                     }
                  }
               }
            );
         }

         if (Math.random() < 1.0E-4 && world instanceof ServerLevel _level) {
            Entity entityToSpawn = ((EntityType)BohModEntities.FLATWOODS_MONSTER.get())
               .spawn(_level, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), MobSpawnType.MOB_SUMMONED);
            if (entityToSpawn != null) {
               entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
            }
         }

         if (Math.random() < 0.005) {
            if (world instanceof ServerLevel _level) {
               Entity entityToSpawn = ((EntityType)BohModEntities.MARTIAN_DRONE.get())
                  .spawn(_level, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), MobSpawnType.MOB_SUMMONED);
               if (entityToSpawn != null) {
                  entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
               }
            }

            BohMod.queueServerWork(
               4,
               () -> {
                  Vec3 _center = new Vec3(x, y, z);

                  for (Entity entityiteratorx : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(50.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                     .toList()) {
                     if (entityiteratorx instanceof MartianDroneEntity) {
                        entityiteratorx.getPersistentData().putBoolean("spawn_thru_ship", true);
                     }
                  }
               }
            );
         }

         if (Math.random() < 0.01 && world instanceof Level _level) {
            if (!_level.isClientSide()) {
               _level.playSound(
                  null,
                  BlockPos.containing(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mother_ship_idle")),
                  SoundSource.HOSTILE,
                  100.0F,
                  (float)Mth.nextDouble(RandomSource.create(), -1.0, 2.0)
               );
            } else {
               _level.playLocalSound(
                  x,
                  y,
                  z,
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mother_ship_idle")),
                  SoundSource.HOSTILE,
                  100.0F,
                  (float)Mth.nextDouble(RandomSource.create(), -1.0, 2.0),
                  false
               );
            }
         }

         entity.getPersistentData().putDouble("tick_ambience", entity.getPersistentData().getDouble("tick_ambience") + 1.0);
         if (entity.getPersistentData().getDouble("tick_ambience") == 580.0) {
            if (world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mother_ship_loop")),
                     SoundSource.HOSTILE,
                     100.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mother_ship_loop")),
                     SoundSource.HOSTILE,
                     100.0F,
                     1.0F,
                     false
                  );
               }
            }

            entity.getPersistentData().putDouble("tick_ambience", 0.0);
         }

         entity.getPersistentData().putDouble("tick_music", entity.getPersistentData().getDouble("tick_music") + 1.0);
         if (entity.getPersistentData().getDouble("tick_music") == 2140.0) {
            if (world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:gray_ost")),
                     SoundSource.MUSIC,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:gray_ost")), SoundSource.MUSIC, 1.0F, 1.0F, false
                  );
               }
            }

            entity.getPersistentData().putDouble("tick_music", 0.0);
         }
      }
   }
}
