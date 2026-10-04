package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.MothmanEntity;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class MothmanEntityIsHurtProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (Math.random() < 0.1) {
            if (world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mothman_fly")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mothman_fly")),
                     SoundSource.HOSTILE,
                     1.0F,
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
                     "/particle minecraft:squid_ink ~ ~ ~ 0.5 .5 0.5 0 100"
                  );
            }

            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
            }

            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 254, false, false));
            }

            if (entity instanceof MothmanEntity) {
               ((MothmanEntity)entity).setAnimation("flight");
            }

            BohMod.queueServerWork(
               20,
               () -> {
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
                           "/spreadplayers ~ ~ 30 30 false @e[type=boh:mothman,limit=1,sort=nearest]"
                        );
                  }

                  if (entity instanceof MothmanEntity) {
                     ((MothmanEntity)entity).setAnimation("landing");
                  }

                  if (world instanceof Level _level) {
                     if (!_level.isClientSide()) {
                        _level.playSound(
                           null,
                           BlockPos.containing(x, y, z),
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mothman_land")),
                           SoundSource.HOSTILE,
                           1.0F,
                           1.0F
                        );
                     } else {
                        _level.playLocalSound(
                           x,
                           y,
                           z,
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mothman_land")),
                           SoundSource.HOSTILE,
                           1.0F,
                           1.0F,
                           false
                        );
                     }
                  }

                  BohMod.queueServerWork(
                     12,
                     () -> {
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
                                 "/particle minecraft:squid_ink ~ ~ ~ 1 .1 1 0 100"
                              );
                        }
                     }
                  );
               }
            );
         }
      }
   }
}
