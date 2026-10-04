package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModMobEffects;
import net.mcreator.boh.init.BohModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber
public class BloodRainProcedure {
   @SubscribeEvent
   public static void onPlayerTick(PlayerTickEvent event) {
      if (event.phase == Phase.END) {
         execute(event, event.player.level(), event.player.getX(), event.player.getY(), event.player.getZ(), event.player);
      }
   }

   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      execute(null, world, x, y, z, entity);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (entity instanceof LivingEntity _livEnt0
            && _livEnt0.hasEffect((MobEffect)BohModMobEffects.COGNITO_HAZART.get())
            && entity.getPersistentData().getBoolean("blood_rain")
            && world.isClientSide()) {
            world.addParticle(
               (SimpleParticleType)BohModParticleTypes.BLOOD_FALL.get(),
               entity.getX() + Mth.nextInt(RandomSource.create(), -20, 20),
               entity.getY() + 25.0,
               entity.getZ() + Mth.nextInt(RandomSource.create(), -20, 20),
               0.0,
               0.0,
               0.0
            );
            world.addParticle(
               (SimpleParticleType)BohModParticleTypes.BLOOD_FALL.get(),
               entity.getX() + Mth.nextInt(RandomSource.create(), -20, 20),
               entity.getY() + 25.0,
               entity.getZ() + Mth.nextInt(RandomSource.create(), -20, 20),
               0.0,
               0.0,
               0.0
            );
            world.addParticle(
               (SimpleParticleType)BohModParticleTypes.BLOOD_FALL.get(),
               entity.getX() + Mth.nextInt(RandomSource.create(), -20, 20),
               entity.getY() + 25.0,
               entity.getZ() + Mth.nextInt(RandomSource.create(), -20, 20),
               0.0,
               0.0,
               0.0
            );
            world.addParticle(
               (SimpleParticleType)BohModParticleTypes.BLOOD_FALL.get(),
               entity.getX() + Mth.nextInt(RandomSource.create(), -20, 20),
               entity.getY() + 25.0,
               entity.getZ() + Mth.nextInt(RandomSource.create(), -20, 20),
               0.0,
               0.0,
               0.0
            );
            world.addParticle(
               (SimpleParticleType)BohModParticleTypes.BLOOD_FALL.get(),
               entity.getX() + Mth.nextInt(RandomSource.create(), -20, 20),
               entity.getY() + 25.0,
               entity.getZ() + Mth.nextInt(RandomSource.create(), -20, 20),
               0.0,
               0.0,
               0.0
            );
            world.addParticle(
               (SimpleParticleType)BohModParticleTypes.BLOOD_FALL.get(),
               entity.getX() + Mth.nextInt(RandomSource.create(), -20, 20),
               entity.getY() + 25.0,
               entity.getZ() + Mth.nextInt(RandomSource.create(), -20, 20),
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
                     x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("weather.rain")), SoundSource.AMBIENT, 1.0F, 1.0F, false
                  );
               }
            }
         }
      }
   }
}
