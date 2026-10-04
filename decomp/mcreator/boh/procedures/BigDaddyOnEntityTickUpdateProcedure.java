package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.BigDaddyEntity;
import net.mcreator.boh.entity.LittleSisterEntity;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.minecraft.world.scores.criteria.ObjectiveCriteria.RenderType;
import net.minecraftforge.registries.ForgeRegistries;

public class BigDaddyOnEntityTickUpdateProcedure {
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
               if (world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:big_daddy_melee")),
                        SoundSource.HOSTILE,
                        1.0F,
                        0.1F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:big_daddy_melee")),
                        SoundSource.HOSTILE,
                        1.0F,
                        0.1F,
                        false
                     );
                  }
               }

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
               if (entity instanceof BigDaddyEntity) {
                  ((BigDaddyEntity)entity).setAnimation("charge");
               }

               if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                  _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 5, false, false));
               }

               BohMod.queueServerWork(26, () -> {
                  if (entity instanceof BigDaddyEntity) {
                     ((BigDaddyEntity)entity).setAnimation("empty");
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

            if (entity.getPersistentData().getBoolean("aoe")) {
               world.levelEvent(
                  2001,
                  BlockPos.containing(Mth.nextInt(RandomSource.create(), -8, 8) + x, y, Mth.nextInt(RandomSource.create(), -8, 8) + z),
                  Block.getId(
                     world.getBlockState(
                        BlockPos.containing(Mth.nextInt(RandomSource.create(), -8, 8) + x, y - 1.0, Mth.nextInt(RandomSource.create(), -8, 8) + z)
                     )
                  )
               );
               world.levelEvent(
                  2001,
                  BlockPos.containing(Mth.nextInt(RandomSource.create(), -8, 8) + x, y, Mth.nextInt(RandomSource.create(), -8, 8) + z),
                  Block.getId(
                     world.getBlockState(
                        BlockPos.containing(Mth.nextInt(RandomSource.create(), -8, 8) + x, y - 1.0, Mth.nextInt(RandomSource.create(), -8, 8) + z)
                     )
                  )
               );
               if (world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:big_daddy_melee")),
                        SoundSource.HOSTILE,
                        1.0F,
                        0.1F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:big_daddy_melee")),
                        SoundSource.HOSTILE,
                        1.0F,
                        0.1F,
                        false
                     );
                  }
               }

               Entity _ent = entity;
               Scoreboard _sc = _ent.level().getScoreboard();
               Objective _so = _sc.getObjective("anim");
               if (_so == null) {
                  _so = _sc.addObjective("anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
               }

               _sc.getOrCreatePlayerScore(_ent.getScoreboardName(), _so).setScore(2);
               BohMod.queueServerWork(20, () -> entity.getPersistentData().putBoolean("aoe", false));
            }

            if ((new Object() {
               public int getScore(String score, Entity _ent) {
                  Scoreboard _sc = _ent.level().getScoreboard();
                  Objective _so = _sc.getObjective(score);
                  return _so != null ? _sc.getOrCreatePlayerScore(_ent.getScoreboardName(), _so).getScore() : 0;
               }
            }).getScore("anim", entity) == 2) {
               if (entity instanceof BigDaddyEntity) {
                  ((BigDaddyEntity)entity).setAnimation("shake");
               }

               if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                  _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 255, false, false));
               }

               Vec3 _center = new Vec3(x, y, z);

               for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                  .toList()) {
                  if (entityiterator instanceof Player && entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                     _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 140, 6, false, false));
                  }
               }

               BohMod.queueServerWork(25, () -> {
                  Entity _ent = entity;
                  Scoreboard _sc = _ent.level().getScoreboard();
                  Objective _so = _sc.getObjective("anim");
                  if (_so == null) {
                     _so = _sc.addObjective("anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
                  }

                  _sc.getOrCreatePlayerScore(_ent.getScoreboardName(), _so).setScore(0);
               });
            }

            if (entity.isInWater()) {
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
                        "/effect give @e[type=boh:big_daddy,limit=1,sort=nearest] minecraft:dolphins_grace infinite 3 true"
                     );
               }

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
                        "/effect give @e[type=boh:big_daddy,limit=1,sort=nearest] minecraft:conduit_power infinite 1 true"
                     );
               }
            }

            if ((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player && entity instanceof BigDaddyEntity animatable) {
               animatable.setTexture("big_daddy_aggro");
            }

            if (!((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player) && entity instanceof BigDaddyEntity animatable) {
               animatable.setTexture("big_daddy");
            }
         }

         if (world.getEntitiesOfClass(LittleSisterEntity.class, AABB.ofSize(new Vec3(x, y, z), 5.0, 5.0, 5.0), e -> true).isEmpty()) {
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(50.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
               .toList()) {
               if (entityiterator instanceof LittleSisterEntity
                  && !(entityiterator instanceof LivingEntity _livEnt61 && _livEnt61.hasEffect(MobEffects.CONFUSION))
                  && entity instanceof Mob _entity) {
                  _entity.getNavigation().moveTo(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ(), 1.1);
               }
            }
         }

         if (!world.getEntitiesOfClass(LittleSisterEntity.class, AABB.ofSize(new Vec3(x, y, z), 5.0, 5.0, 5.0), e -> true).isEmpty()
            && !world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 8.0, 8.0, 8.0), e -> true).isEmpty()
            && Math.random() < 0.005) {
            if (entity instanceof BigDaddyEntity) {
               ((BigDaddyEntity)entity).setAnimation("threaten");
            }

            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 254, false, false));
            }

            if (world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:big_daddy_threaten")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:big_daddy_threaten")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }
         }

         if (!((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player)) {
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(10.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
               .toList()) {
               if (entityiterator instanceof LittleSisterEntity
                  && entityiterator instanceof LivingEntity _livEnt75
                  && _livEnt75.hasEffect(MobEffects.CONFUSION)
                  && entity instanceof LivingEntity _entity) {
                  _entity.removeEffect(MobEffects.CONFUSION);
               }
            }
         }
      }
   }
}
