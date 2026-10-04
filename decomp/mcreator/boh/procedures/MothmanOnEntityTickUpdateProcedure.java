package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.MothmanEntity;
import net.mcreator.boh.entity.MothmanbastProjectileEntity;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.minecraft.world.scores.criteria.ObjectiveCriteria.RenderType;
import net.minecraftforge.registries.ForgeRegistries;

public class MothmanOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (!world.isClientSide()) {
            if (!entity.getPersistentData().getBoolean("lines_demo") && (entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player) {
               entity.getPersistentData().putBoolean("lines_demo", true);
            }

            if (Math.random() < 0.2 && !entity.getPersistentData().getBoolean("throlgular") && entity.getPersistentData().getBoolean("lines_demo")) {
               if (entity instanceof MothmanEntity) {
                  ((MothmanEntity)entity).setAnimation("trigger_aggro");
               }

               Entity _ent = entity;
               Scoreboard _sc = _ent.level().getScoreboard();
               Objective _so = _sc.getObjective("anim");
               if (_so == null) {
                  _so = _sc.addObjective("anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
               }

               _sc.getOrCreatePlayerScore(_ent.getScoreboardName(), _so).setScore(2);
               if (world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mothman_aggro")),
                        SoundSource.HOSTILE,
                        2.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mothman_aggro")),
                        SoundSource.HOSTILE,
                        2.0F,
                        1.0F,
                        false
                     );
                  }
               }

               if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                  _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 254, false, false));
               }

               entity.getPersistentData().putBoolean("throlgular", true);
               BohMod.queueServerWork(65, () -> {
                  if (entity instanceof LivingEntity _entityx) {
                     _entityx.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
                  }

                  Entity _entx = entity;
                  Scoreboard _scx = _entx.level().getScoreboard();
                  Objective _sox = _scx.getObjective("anim");
                  if (_sox == null) {
                     _sox = _scx.addObjective("anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
                  }

                  _scx.getOrCreatePlayerScore(_entx.getScoreboardName(), _sox).setScore(0);
               });
            }

            if (!((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player)) {
               entity.getPersistentData().putBoolean("lines_demo", false);
               entity.getPersistentData().putBoolean("throlgular", false);
            }

            if (world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 10.0, 10.0, 10.0), e -> true).isEmpty()
               && Math.random() < 0.1
               && (entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player) {
               if (world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.bell.resonate")),
                        SoundSource.HOSTILE,
                        1.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.bell.resonate")),
                        SoundSource.HOSTILE,
                        1.0F,
                        1.0F,
                        false
                     );
                  }
               }

               Entity _shootFrom = entity;
               Level projectileLevel = _shootFrom.level();
               if (!projectileLevel.isClientSide()) {
                  Projectile _entityToSpawn = (new Object() {
                        public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
                           AbstractArrow entityToSpawn = new MothmanbastProjectileEntity(
                              (EntityType<? extends MothmanbastProjectileEntity>)BohModEntities.MOTHMANBAST_PROJECTILE.get(), level
                           );
                           entityToSpawn.setOwner(shooter);
                           entityToSpawn.setBaseDamage(damage);
                           entityToSpawn.setKnockback(knockback);
                           entityToSpawn.setSilent(true);
                           return entityToSpawn;
                        }
                     })
                     .getArrow(projectileLevel, entity, 5.0F, 0);
                  _entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
                  _entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 3.0F, 0.0F);
                  projectileLevel.addFreshEntity(_entityToSpawn);
               }
            }

            if (!world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 15.0, 15.0, 15.0), e -> true).isEmpty() && Math.random() < 0.001) {
               Vec3 _center = new Vec3(x, y, z);

               for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                  .toList()) {
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

                  if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                     _entity.addEffect(new MobEffectInstance(MobEffects.BAD_OMEN, 9999999, 0, false, false));
                  }

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

                  if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                     _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
                  }

                  if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                     _entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 254, false, false));
                  }

                  if (entity instanceof MothmanEntity) {
                     ((MothmanEntity)entity).setAnimation("fly");
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
                           ((MothmanEntity)entity).setAnimation("land");
                        }

                        if (world instanceof Level _level) {
                           if (!_level.isClientSide()) {
                              _level.playSound(
                                 null,
                                 BlockPos.containing(x, y, z),
                                 (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mothman_land")),
                                 SoundSource.HOSTILE,
                                 3.0F,
                                 1.0F
                              );
                           } else {
                              _level.playLocalSound(
                                 x,
                                 y,
                                 z,
                                 (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mothman_land")),
                                 SoundSource.HOSTILE,
                                 3.0F,
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
   }
}
