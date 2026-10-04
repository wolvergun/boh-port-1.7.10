package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.MXEntity;
import net.mcreator.boh.network.BohModVariables;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.minecraft.world.scores.criteria.ObjectiveCriteria.RenderType;
import net.minecraftforge.registries.ForgeRegistries;

public class MXOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (!world.isClientSide()) {
            if ((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player
               && world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 8.0, 8.0, 8.0), e -> true).isEmpty()) {
               entity.getPersistentData().putDouble("TimerTick", entity.getPersistentData().getDouble("TimerTick") + 1.0);
               if (entity.getPersistentData().getDouble("TimerTick") >= 20.0) {
                  entity.getPersistentData().putDouble("TimerTick", 0.0);
                  if (Math.random() < 0.1) {
                     entity.getPersistentData().putBoolean("lunge", true);
                  }
               }
            }

            if (entity.getPersistentData().getBoolean("lunge")) {
               Entity _ent = entity;
               Scoreboard _sc = _ent.level().getScoreboard();
               Objective _so = _sc.getObjective("anim");
               if (_so == null) {
                  _so = _sc.addObjective("anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
               }

               _sc.getOrCreatePlayerScore(_ent.getScoreboardName(), _so).setScore(1);
               entity.getPersistentData().putBoolean("lunge", false);
            }

            if ((new Object() {
               public int getScore(String score, Entity _ent) {
                  Scoreboard _sc = _ent.level().getScoreboard();
                  Objective _so = _sc.getObjective(score);
                  return _so != null ? _sc.getOrCreatePlayerScore(_ent.getScoreboardName(), _so).getScore() : 0;
               }
            }).getScore("anim", entity) == 1) {
               world.levelEvent(2001, BlockPos.containing(x, y, z), Block.getId(world.getBlockState(BlockPos.containing(x, y - 1.0, z))));
               if (entity instanceof MXEntity) {
                  ((MXEntity)entity).setAnimation("charge");
               }

               if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                  _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 5, false, false));
               }

               BohMod.queueServerWork(26, () -> {
                  if (entity instanceof MXEntity) {
                     ((MXEntity)entity).setAnimation("empty");
                  }

                  Entity _ent = entity;
                  Scoreboard _sc = _ent.level().getScoreboard();
                  Objective _so = _sc.getObjective("anim");
                  if (_so == null) {
                     _so = _sc.addObjective("anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
                  }

                  _sc.getOrCreatePlayerScore(_ent.getScoreboardName(), _so).setScore(0);
               });
            }

            if ((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player
               && !world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 8.0, 8.0, 8.0), e -> true).isEmpty()
               && Math.random() < 0.01) {
               entity.getPersistentData().putBoolean("aoe", true);
            }
         }

         if ((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player
            && !world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 4.0, 4.0, 4.0), e -> true).isEmpty()
            && Math.random() < 0.03) {
            if (entity instanceof MXEntity) {
               ((MXEntity)entity).setAnimation("stomp");
            }

            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 255, false, false));
            }

            BohMod.queueServerWork(
               25,
               () -> {
                  for (int index0 = 0; index0 < 10; index0++) {
                     if (world instanceof Level _levelx) {
                        if (!_levelx.isClientSide()) {
                           _levelx.playSound(
                              null,
                              BlockPos.containing(x, y, z),
                              (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.explode")),
                              SoundSource.HOSTILE,
                              1.0F,
                              1.0F
                           );
                        } else {
                           _levelx.playLocalSound(
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
                  25,
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

         if ((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player) {
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
               .toList()) {
               if (entityiterator instanceof Player) {
                  boolean _setval = true;
                  entityiterator.getCapability(BohModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                     capability.chase_mx = _setval;
                     capability.syncPlayerVariables(entityiterator);
                  });
               }
            }
         }

         if (!((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player) && world instanceof ServerLevel _level) {
            _level.getServer()
               .getCommands()
               .performPrefixedCommand(
                  new CommandSourceStack(
                        CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null
                     )
                     .withSuppressedOutput(),
                  "/stopsound @a music boh:chase_mx"
               );
         }
      }
   }
}
