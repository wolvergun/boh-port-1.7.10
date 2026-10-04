package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.SonicExeEntity;
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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class SonicExeEntityIsHurtProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
      if (entity != null && sourceentity != null) {
         if (entity instanceof SonicExeEntity) {
            ((SonicExeEntity)entity).setAnimation("hurt");
         }

         if (Math.random() < 0.01 && entity instanceof Mob _entity && sourceentity instanceof LivingEntity _ent) {
            _entity.setTarget(_ent);
         }

         if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
            _entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 255, false, false));
         }

         if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
            _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 255, false, false));
         }

         BohMod.queueServerWork(
            8,
            () -> {
               if (world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:exe_teleport")),
                        SoundSource.HOSTILE,
                        1.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:exe_teleport")),
                        SoundSource.HOSTILE,
                        1.0F,
                        1.0F,
                        false
                     );
                  }
               }

               Entity _entx = entity;
               if (!_entx.level().isClientSide() && _entx.getServer() != null) {
                  _entx.getServer()
                     .getCommands()
                     .performPrefixedCommand(
                        new CommandSourceStack(
                           CommandSource.NULL,
                           _entx.position(),
                           _entx.getRotationVector(),
                           _entx.level() instanceof ServerLevel ? (ServerLevel)_entx.level() : null,
                           4,
                           _entx.getName().getString(),
                           _entx.getDisplayName(),
                           _entx.level().getServer(),
                           _entx
                        ),
                        "/spreadplayers ~ ~ 20 20 false @e[type=boh:sonic_exe,limit=1,distance=0..2]"
                     );
               }

               if (entity instanceof SonicExeEntity) {
                  ((SonicExeEntity)entity).setAnimation("hurt");
               }
            }
         );
         BohMod.queueServerWork(
            12,
            () -> {
               if (Math.random() < 0.6 && world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:exe_laugh")),
                        SoundSource.HOSTILE,
                        1.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:exe_laugh")),
                        SoundSource.HOSTILE,
                        1.0F,
                        1.0F,
                        false
                     );
                  }
               }

               if (entity instanceof SonicExeEntity) {
                  ((SonicExeEntity)entity).setAnimation("laugh");
               }
            }
         );
      }
   }
}
