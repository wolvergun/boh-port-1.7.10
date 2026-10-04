package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.init.BohModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class FromOutOfThisEarthOnEffectActiveTickProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (entity instanceof Villager) {
            if (world.getNearestPlayer(x, y, z, 5.0, true) != null && Math.random() < 0.1) {
               if (!entity.level().isClientSide()) {
                  entity.discard();
               }

               if (world instanceof ServerLevel _level) {
                  _level.sendParticles(
                     (SimpleParticleType)BohModParticleTypes.BLOOD_FALL.get(),
                     entity.getX(),
                     entity.getY() + entity.getBbHeight() / 2.0F,
                     entity.getZ(),
                     20,
                     0.5,
                     entity.getBbHeight() / 2.0F,
                     0.5,
                     0.02
                  );
               }

               if (world instanceof ServerLevel _level) {
                  Entity entityToSpawn = ((EntityType)BohModEntities.THE_THING_VILLAGER.get())
                     .spawn(_level, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), MobSpawnType.MOB_SUMMONED);
                  if (entityToSpawn != null) {
                  }
               }

               if (world instanceof Level _level && _level.isClientSide()) {
                  _level.playLocalSound(
                     entity.getX(),
                     entity.getY(),
                     entity.getZ(),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chestburster_kill")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }
         } else if (entity instanceof Wolf && world.getNearestPlayer(x, y, z, 5.0, true) != null && Math.random() < 0.1) {
            if (!entity.level().isClientSide()) {
               entity.discard();
            }

            if (world instanceof ServerLevel _level) {
               _level.sendParticles(
                  (SimpleParticleType)BohModParticleTypes.BLOOD_FALL.get(),
                  entity.getX(),
                  entity.getY() + entity.getBbHeight() / 2.0F,
                  entity.getZ(),
                  20,
                  0.5,
                  entity.getBbHeight() / 2.0F,
                  0.5,
                  0.02
               );
            }

            if (world instanceof ServerLevel _level) {
               Entity entityToSpawn = ((EntityType)BohModEntities.THE_THING_DOG.get())
                  .spawn(_level, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), MobSpawnType.MOB_SUMMONED);
               if (entityToSpawn != null) {
               }
            }

            if (world instanceof Level _level && _level.isClientSide()) {
               _level.playLocalSound(
                  entity.getX(),
                  entity.getY(),
                  entity.getZ(),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chestburster_kill")),
                  SoundSource.HOSTILE,
                  1.0F,
                  1.0F,
                  false
               );
            }
         }
      }
   }
}
