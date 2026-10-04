package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.BloodSpillEntity;
import net.mcreator.boh.entity.GojiEntity;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.minecraft.world.scores.criteria.ObjectiveCriteria.RenderType;
import net.minecraftforge.registries.ForgeRegistries;

public class GojiOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if ((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player
            && !world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 4.0, 4.0, 4.0), e -> true).isEmpty()
            && Math.random() < 0.03) {
            if (entity instanceof GojiEntity) {
               ((GojiEntity)entity).setAnimation("stomp");
            }

            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 255, false, false));
            }

            BohMod.queueServerWork(
               14,
               () -> {
                  for (int index0 = 0; index0 < 10; index0++) {
                     if (world instanceof Level _level) {
                        if (!_level.isClientSide()) {
                           _level.playSound(
                              null,
                              BlockPos.containing(x, y, z),
                              (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.explode")),
                              SoundSource.HOSTILE,
                              1.0F,
                              1.0F
                           );
                        } else {
                           _level.playLocalSound(
                              x,
                              y,
                              z,
                              (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.explode")),
                              SoundSource.HOSTILE,
                              1.0F,
                              1.0F,
                              false
                           );
                        }
                     }

                     world.levelEvent(
                        2001,
                        BlockPos.containing(Mth.nextInt(RandomSource.create(), -3, 3) + x, y, Mth.nextInt(RandomSource.create(), -3, 3) + z),
                        Block.getId(world.getBlockState(BlockPos.containing(x, y - 1.0, z)))
                     );
                  }
               }
            );
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(2.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
               .toList()) {
               BohMod.queueServerWork(
                  14,
                  () -> {
                     if (entityiterator instanceof Player) {
                        Entity _ent = entityiterator;
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
                                 "/effect give @s minecraft:levitation 1 6 true"
                              );
                        }

                        entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC)), 7.0F);
                     }
                  }
               );
            }
         }

         if (!entity.getPersistentData().getBoolean("rexy_roar") && (entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player) {
            entity.getPersistentData().putBoolean("rexy_roar", true);
         }

         if (Math.random() < 0.25 && !entity.getPersistentData().getBoolean("twitch") && entity.getPersistentData().getBoolean("rexy_roar")) {
            if (entity instanceof GojiEntity) {
               ((GojiEntity)entity).setAnimation("roar");
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
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:goji_roar")),
                     SoundSource.HOSTILE,
                     3.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:goji_roar")), SoundSource.HOSTILE, 3.0F, 1.0F, false
                  );
               }
            }

            entity.getPersistentData().putBoolean("twitch", true);
            BohMod.queueServerWork(68, () -> {
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
            entity.getPersistentData().putBoolean("rexy_roar", false);
            entity.getPersistentData().putBoolean("twitch", false);
         }

         if ((new Object() {
            public int getScore(String score, Entity _ent) {
               Scoreboard _sc = _ent.level().getScoreboard();
               Objective _so = _sc.getObjective(score);
               return _so != null ? _sc.getOrCreatePlayerScore(_ent.getScoreboardName(), _so).getScore() : 0;
            }
         }).getScore("anim", entity) == 2) {
            entity.makeStuckInBlock(Blocks.AIR.defaultBlockState(), new Vec3(0.25, 0.05, 0.25));
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 255, false, false));
            }
         }

         if (entity.getPersistentData().getBoolean("shoot_goji")) {
            entity.makeStuckInBlock(Blocks.AIR.defaultBlockState(), new Vec3(0.25, 0.05, 0.25));
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 255, false, false));
            }
         }

         if ((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player
            && Math.random() < 0.005
            && !entity.getPersistentData().getBoolean("shoot_goji")) {
            entity.getPersistentData().putBoolean("shoot_goji", true);
            if (entity instanceof GojiEntity) {
               ((GojiEntity)entity).setAnimation("spit");
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
                     "/playsound boh:goji_buildup hostile @a ~ ~ ~ 2 1"
                  );
            }

            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(50.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
               .toList()) {
               if (entityiterator instanceof Player) {
                  entity.lookAt(Anchor.EYES, new Vec3(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ()));
               }
            }

            BohMod.queueServerWork(
               10,
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
                           "/playsound boh:goji_breath hostile @a ~ ~ ~ 0.4 1"
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
                           "/playsound minecraft:entity.llama.spit hostile @a ~ ~ ~ 0.4 0.2"
                        );
                  }

                  _entx = entity;
                  Level projectileLevel = _entx.level();
                  if (!projectileLevel.isClientSide()) {
                     Projectile _entityToSpawn = (new Object() {
                        public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
                           AbstractArrow entityToSpawn = new BloodSpillEntity((EntityType<? extends BloodSpillEntity>)BohModEntities.BLOOD_SPILL.get(), level);
                           entityToSpawn.setOwner(shooter);
                           entityToSpawn.setBaseDamage(damage);
                           entityToSpawn.setKnockback(knockback);
                           entityToSpawn.setSilent(true);
                           return entityToSpawn;
                        }
                     }).getArrow(projectileLevel, entity, 5.0F, 0);
                     _entityToSpawn.setPos(_entx.getX(), _entx.getEyeY() - 0.1, _entx.getZ());
                     _entityToSpawn.shoot(_entx.getLookAngle().x, _entx.getLookAngle().y, _entx.getLookAngle().z, 0.9F, 0.0F);
                     projectileLevel.addFreshEntity(_entityToSpawn);
                  }

                  BohMod.queueServerWork(
                     10,
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
                                 "/playsound minecraft:entity.llama.spit hostile @a ~ ~ ~ 0.4 0.2"
                              );
                        }

                        _entx = entity;
                        Level projectileLevelx = _entx.level();
                        if (!projectileLevelx.isClientSide()) {
                           Projectile _entityToSpawnx = (new Object() {
                                 public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
                                    AbstractArrow entityToSpawn = new BloodSpillEntity(
                                       (EntityType<? extends BloodSpillEntity>)BohModEntities.BLOOD_SPILL.get(), level
                                    );
                                    entityToSpawn.setOwner(shooter);
                                    entityToSpawn.setBaseDamage(damage);
                                    entityToSpawn.setKnockback(knockback);
                                    entityToSpawn.setSilent(true);
                                    return entityToSpawn;
                                 }
                              })
                              .getArrow(projectileLevelx, entity, 5.0F, 0);
                           _entityToSpawnx.setPos(_entx.getX(), _entx.getEyeY() - 0.1, _entx.getZ());
                           _entityToSpawnx.shoot(_entx.getLookAngle().x, _entx.getLookAngle().y, _entx.getLookAngle().z, 0.9F, 0.0F);
                           projectileLevelx.addFreshEntity(_entityToSpawnx);
                        }

                        BohMod.queueServerWork(
                           10,
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
                                       "/playsound minecraft:entity.llama.spit hostile @a ~ ~ ~ 0.4 0.2"
                                    );
                              }

                              _entx = entity;
                              Level projectileLevelxx = _entx.level();
                              if (!projectileLevelxx.isClientSide()) {
                                 Projectile _entityToSpawnxx = (new Object() {
                                       public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
                                          AbstractArrow entityToSpawn = new BloodSpillEntity(
                                             (EntityType<? extends BloodSpillEntity>)BohModEntities.BLOOD_SPILL.get(), level
                                          );
                                          entityToSpawn.setOwner(shooter);
                                          entityToSpawn.setBaseDamage(damage);
                                          entityToSpawn.setKnockback(knockback);
                                          entityToSpawn.setSilent(true);
                                          return entityToSpawn;
                                       }
                                    })
                                    .getArrow(projectileLevelxx, entity, 5.0F, 0);
                                 _entityToSpawnxx.setPos(_entx.getX(), _entx.getEyeY() - 0.1, _entx.getZ());
                                 _entityToSpawnxx.shoot(_entx.getLookAngle().x, _entx.getLookAngle().y, _entx.getLookAngle().z, 0.9F, 0.0F);
                                 projectileLevelxx.addFreshEntity(_entityToSpawnxx);
                              }

                              entity.getPersistentData().putBoolean("shoot_goji", false);
                           }
                        );
                     }
                  );
               }
            );
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
                  "/fill ~-1 ~ ~-1 ~1 ~4 ~1 air replace #minecraft:leaves"
               );
         }

         if (!((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player)) {
            entity.getPersistentData().putBoolean("rexy_roar", false);
            entity.getPersistentData().putBoolean("twitch", false);
         }

         if (entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
            entity.getPersistentData().putDouble("timer_step", entity.getPersistentData().getDouble("timer_step") + 1.0);
         } else {
            entity.getPersistentData().putDouble("timer_step", 0.0);
         }

         if (entity instanceof Mob _mob && _mob.isAggressive()) {
            if (entity instanceof Mob _mobx && _mobx.isAggressive() && entity.getPersistentData().getDouble("timer_step") == 11.0) {
               if (!world.isClientSide() && world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")),
                        SoundSource.HOSTILE,
                        3.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")),
                        SoundSource.HOSTILE,
                        3.0F,
                        1.0F,
                        false
                     );
                  }
               }

               entity.getPersistentData().putDouble("timer_step", 0.0);
            }
         } else if (entity.getPersistentData().getDouble("timer_step") == 19.0) {
            if (!world.isClientSide() && world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")),
                     SoundSource.NEUTRAL,
                     3.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")), SoundSource.NEUTRAL, 3.0F, 1.0F, false
                  );
               }
            }

            entity.getPersistentData().putDouble("timer_step", 0.0);
         }
      }
   }
}
