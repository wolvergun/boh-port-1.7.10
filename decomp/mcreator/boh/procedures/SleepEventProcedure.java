package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.EyelessJackEntity;
import net.mcreator.boh.entity.JeffTheKillerEntity;
import net.mcreator.boh.entity.RakeEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber
public class SleepEventProcedure {
   @SubscribeEvent
   public static void onPlayerInBed(PlayerSleepInBedEvent event) {
      execute(event, event.getEntity().level(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getEntity());
   }

   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      execute(null, world, x, y, z, entity);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (!world.isClientSide()
            && !(entity instanceof LivingEntity _livEnt1 && _livEnt1.hasEffect((MobEffect)BohModMobEffects.SAFE_AND_SOUND.get()))
            && Math.random() < 0.1
            && Math.random() < 0.1) {
            if (Math.random() < 0.8) {
               if (world.getEntitiesOfClass(JeffTheKillerEntity.class, AABB.ofSize(new Vec3(x, y, z), 900.0, 900.0, 900.0), e -> true).isEmpty()
                  && !(world instanceof Level _lvl3 && _lvl3.isDay())) {
                  if (entity instanceof Player _player && !_player.level().isClientSide()) {
                     _player.displayClientMessage(Component.literal("§l§4 GO TO SLEEP"), true);
                  }

                  if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                     _entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 120, 0, false, false));
                  }

                  if (world instanceof Level _level) {
                     if (!_level.isClientSide()) {
                        _level.playSound(
                           null,
                           BlockPos.containing(x, y, z),
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:jeff_sleep")),
                           SoundSource.HOSTILE,
                           1.0F,
                           1.0F
                        );
                     } else {
                        _level.playLocalSound(
                           x,
                           y,
                           z,
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:jeff_sleep")),
                           SoundSource.HOSTILE,
                           1.0F,
                           1.0F,
                           false
                        );
                     }
                  }

                  if (world.getEntitiesOfClass(JeffTheKillerEntity.class, AABB.ofSize(new Vec3(x, y, z), 50.0, 50.0, 50.0), e -> true).isEmpty()) {
                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = ((EntityType)BohModEntities.JEFF_THE_KILLER.get())
                           .spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                           entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                        }
                     }
                  } else {
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
                              "/tp @e[type=boh:jeff_the_killer,limit=1,distance=0..50] @p"
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
                           "/execute at @p run spreadplayers ~ ~ 10 10 false @e[type=boh:jeff_the_killer]"
                        );
                  }
               }
            } else if (Math.random() < 0.64) {
               if (world.getEntitiesOfClass(RakeEntity.class, AABB.ofSize(new Vec3(x, y, z), 900.0, 900.0, 900.0), e -> true).isEmpty()
                  && !(world instanceof Level _lvl12 && _lvl12.isDay())) {
                  if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                     _entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 900, 0, false, false));
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
                           "effect give @s kurolib:sleep 0 45"
                        );
                  }

                  if (world.getEntitiesOfClass(RakeEntity.class, AABB.ofSize(new Vec3(x, y, z), 50.0, 50.0, 50.0), e -> true).isEmpty()) {
                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = ((EntityType)BohModEntities.RAKE.get())
                           .spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                           entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                        }
                     }
                  } else {
                     _ent = entity;
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
                              "/tp @e[type=boh:rake,limit=1,distance=0..50] @p"
                           );
                     }
                  }
               }
            } else if (Math.random() < 0.48) {
               if (world.getEntitiesOfClass(EyelessJackEntity.class, AABB.ofSize(new Vec3(x, y, z), 900.0, 900.0, 900.0), e -> true).isEmpty()
                  && !(world instanceof Level _lvl19 && _lvl19.isDay())) {
                  if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                     _entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 900, 0, false, false));
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
                           "effect give @s kurolib:sleep 0 45"
                        );
                  }

                  if (world.getEntitiesOfClass(EyelessJackEntity.class, AABB.ofSize(new Vec3(x, y, z), 50.0, 50.0, 50.0), e -> true).isEmpty()) {
                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = ((EntityType)BohModEntities.EYELESS_JACK.get())
                           .spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                           entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                        }
                     }
                  } else {
                     _ent = entity;
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
                              "/tp @e[type=boh:eyeless_jack,limit=1,distance=0..50] @p"
                           );
                     }
                  }

                  BohMod.queueServerWork(
                     40,
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
                                 "/spreadplayers ~ ~ 30 30 false @e[type=boh:eyeless_jack,limit=1,sort=nearest]"
                              );
                        }

                        _entx = entity;
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
                                 "/attribute @p minecraft:generic.max_health base set 16"
                              );
                        }
                     }
                  );
               }
            } else if (Math.random() < 0.32) {
               if (!(world instanceof Level _lvl28 && _lvl28.isDay()) && entity instanceof ServerPlayer _player && !_player.level().isClientSide()) {
                  ResourceKey<Level> destinationType = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("boh:boiler_room_dimension"));
                  if (_player.level().dimension() == destinationType) {
                     return;
                  }

                  ServerLevel nextLevel = _player.server.getLevel(destinationType);
                  if (nextLevel != null) {
                     _player.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.WIN_GAME, 0.0F));
                     _player.teleportTo(nextLevel, _player.getX(), _player.getY(), _player.getZ(), _player.getYRot(), _player.getXRot());
                     _player.connection.send(new ClientboundPlayerAbilitiesPacket(_player.getAbilities()));

                     for (MobEffectInstance _effectinstance : _player.getActiveEffects()) {
                        _player.connection.send(new ClientboundUpdateMobEffectPacket(_player.getId(), _effectinstance));
                     }

                     _player.connection.send(new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));
                  }
               }
            } else if (Math.random() < 0.16) {
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
                        "effect give @s kurolib:sleep 0 45"
                     );
               }

               if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                  _entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 900, 0, false, false));
               }

               if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                  _entity.addEffect(new MobEffectInstance((MobEffect)BohModMobEffects.HYSTM_EFFECT.get(), 900, 0, false, false));
               }
            } else if (Math.random() < 0.3) {
               if (!world.getEntitiesOfClass(Phantom.class, AABB.ofSize(new Vec3(x, y, z), 50.0, 50.0, 50.0), e -> true).isEmpty()
                  && world instanceof ServerLevel _level) {
                  Entity entityToSpawn = ((EntityType)BohModEntities.RUSSIAN_SLEEP_EXPERIMENT.get())
                     .spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                  if (entityToSpawn != null) {
                     entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                  }
               }
            } else if (Math.random() < 0.3 && world instanceof ServerLevel _level) {
               Entity entityToSpawn = ((EntityType)BohModEntities.LAUGHING_JACK.get())
                  .spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
               if (entityToSpawn != null) {
                  entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
               }
            }
         }
      }
   }
}
