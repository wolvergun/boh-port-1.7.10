package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraftforge.registries.ForgeRegistries;

public class CognitoHazartOnEffectActiveTickProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         Entity _entityTeam = entity;
         PlayerTeam _pt = _entityTeam.level().getScoreboard().getPlayerTeam("cognito");
         if (_pt != null) {
            if (_entityTeam instanceof Player _player) {
               _entityTeam.level().getScoreboard().addPlayerToTeam(_player.getGameProfile().getName(), _pt);
            } else {
               _entityTeam.level().getScoreboard().addPlayerToTeam(_entityTeam.getStringUUID(), _pt);
            }
         }

         if (!entity.getPersistentData().getBoolean("start") && Math.random() < 0.025) {
            entity.getPersistentData().putBoolean("start", true);
            if (world.isClientSide() && world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:boiled_one_trumpet")),
                     SoundSource.AMBIENT,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:boiled_one_trumpet")),
                     SoundSource.AMBIENT,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }
         }

         if (world.isClientSide() && entity.getPersistentData().getBoolean("start")) {
            entity.getPersistentData().putDouble("timer", entity.getPersistentData().getDouble("timer") + 1.0);
            if (Math.random() < 0.5) {
               world.addParticle(
                  (SimpleParticleType)BohModParticleTypes.BLOOD_FALL.get(),
                  entity.getX() + Mth.nextInt(RandomSource.create(), -10, 10),
                  entity.getY() + 25.0,
                  entity.getZ() + Mth.nextInt(RandomSource.create(), -10, 10),
                  0.0,
                  0.0,
                  0.0
               );
               world.addParticle(
                  (SimpleParticleType)BohModParticleTypes.BLOOD_FALL.get(),
                  entity.getX() + Mth.nextInt(RandomSource.create(), -10, 10),
                  entity.getY() + 25.0,
                  entity.getZ() + Mth.nextInt(RandomSource.create(), -10, 10),
                  0.0,
                  0.0,
                  0.0
               );
               world.addParticle(
                  (SimpleParticleType)BohModParticleTypes.BLOOD_FALL.get(),
                  entity.getX() + Mth.nextInt(RandomSource.create(), -10, 10),
                  entity.getY() + 25.0,
                  entity.getZ() + Mth.nextInt(RandomSource.create(), -10, 10),
                  0.0,
                  0.0,
                  0.0
               );
               world.addParticle(
                  (SimpleParticleType)BohModParticleTypes.BLOOD_FALL.get(),
                  entity.getX() + Mth.nextInt(RandomSource.create(), -10, 10),
                  entity.getY() + 25.0,
                  entity.getZ() + Mth.nextInt(RandomSource.create(), -10, 10),
                  0.0,
                  0.0,
                  0.0
               );
               world.addParticle(
                  (SimpleParticleType)BohModParticleTypes.BLOOD_FALL.get(),
                  entity.getX() + Mth.nextInt(RandomSource.create(), -10, 10),
                  entity.getY() + 25.0,
                  entity.getZ() + Mth.nextInt(RandomSource.create(), -10, 10),
                  0.0,
                  0.0,
                  0.0
               );
               world.addParticle(
                  (SimpleParticleType)BohModParticleTypes.BLOOD_FALL.get(),
                  entity.getX() + Mth.nextInt(RandomSource.create(), -10, 10),
                  entity.getY() + 25.0,
                  entity.getZ() + Mth.nextInt(RandomSource.create(), -10, 10),
                  0.0,
                  0.0,
                  0.0
               );
               world.addParticle(
                  (SimpleParticleType)BohModParticleTypes.BLOOD_FALL.get(),
                  entity.getX() + Mth.nextInt(RandomSource.create(), -10, 10),
                  entity.getY() + 25.0,
                  entity.getZ() + Mth.nextInt(RandomSource.create(), -10, 10),
                  0.0,
                  0.0,
                  0.0
               );
               world.addParticle(
                  (SimpleParticleType)BohModParticleTypes.BLOOD_FALL.get(),
                  entity.getX() + Mth.nextInt(RandomSource.create(), -10, 10),
                  entity.getY() + 25.0,
                  entity.getZ() + Mth.nextInt(RandomSource.create(), -10, 10),
                  0.0,
                  0.0,
                  0.0
               );
               if (Math.random() < 0.33 && world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("weather.rain")),
                        SoundSource.AMBIENT,
                        1.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("weather.rain")),
                        SoundSource.AMBIENT,
                        1.0F,
                        1.0F,
                        false
                     );
                  }
               }
            }
         }

         if (entity.getPersistentData().getDouble("timer") == 200.0) {
            entity.getPersistentData().putDouble("timer", 0.0);
            entity.getPersistentData().putBoolean("start", false);
         }
      }
   }
}
