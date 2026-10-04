package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.RabbidEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class RabbidOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (!(world instanceof Level _lvl0 && _lvl0.isDay())) {
            entity.setShiftKeyDown(true);
            entity.makeStuckInBlock(Blocks.AIR.defaultBlockState(), new Vec3(0.25, 0.05, 0.25));
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
            }
         }

         if (!(world instanceof Level _lvl4 && _lvl4.isDay()) && entity.getPersistentData().getDouble("state_ai") == 0.0 && entity instanceof RabbidEntity) {
            ((RabbidEntity)entity).setAnimation("sleep");
         }

         if (world instanceof Level _lvl7 && _lvl7.isDay()) {
            entity.setShiftKeyDown(false);
         }

         if (world instanceof Level _lvl9 && _lvl9.isDay()) {
            if (Math.random() < 0.12 && Math.random() < 0.012) {
               entity.getPersistentData().putBoolean("scream", true);
            }

            if (entity.getPersistentData().getBoolean("scream")) {
               entity.getPersistentData().putBoolean("scream_sound", true);
               entity.getPersistentData().putBoolean("scream", false);
               if (entity instanceof RabbidEntity) {
                  ((RabbidEntity)entity).setAnimation("scream");
               }

               if (entity instanceof RabbidEntity animatable) {
                  animatable.setTexture("rabbids_scream");
               }

               BohMod.queueServerWork(10, () -> {
                  if (entity instanceof RabbidEntity animatable) {
                     animatable.setTexture("rabbids");
                  }
               });
            }

            if (entity.getPersistentData().getBoolean("scream")) {
               entity.makeStuckInBlock(Blocks.AIR.defaultBlockState(), new Vec3(0.25, 0.05, 0.25));
               if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                  _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
               }
            }

            if (entity.getPersistentData().getBoolean("scream_sound")) {
               if (world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rabbids")),
                        SoundSource.AMBIENT,
                        1.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rabbids")), SoundSource.AMBIENT, 1.0F, 1.0F, false
                     );
                  }
               }

               entity.getPersistentData().putBoolean("scream_sound", false);
            }
         }
      }
   }
}
