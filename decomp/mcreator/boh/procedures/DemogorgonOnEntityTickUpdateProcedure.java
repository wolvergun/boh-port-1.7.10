package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.DemogorgonEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.minecraft.world.scores.criteria.ObjectiveCriteria.RenderType;
import net.minecraftforge.registries.ForgeRegistries;

public class DemogorgonOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if ((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player
            && world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 15.0, 15.0, 15.0), e -> true).isEmpty()) {
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
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:demogorgon_roar")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:demogorgon_roar")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }

            if (entity instanceof DemogorgonEntity) {
               ((DemogorgonEntity)entity).setAnimation("lunge");
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
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 3, false, false));
            }

            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20, 0, false, false));
            }

            BohMod.queueServerWork(20, () -> {
               if (entity instanceof DemogorgonEntity) {
                  ((DemogorgonEntity)entity).setAnimation("empty");
               }

               Entity _ent = entity;
               Scoreboard _scx = _ent.level().getScoreboard();
               Objective _sox = _scx.getObjective("anim");
               if (_sox == null) {
                  _sox = _scx.addObjective("anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
               }

               _scx.getOrCreatePlayerScore(_ent.getScoreboardName(), _sox).setScore(0);
            });
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
               _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 0, false, false));
            }
         }

         if (!entity.getPersistentData().getBoolean("lines_demo") && (entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player) {
            entity.getPersistentData().putBoolean("lines_demo", true);
         }

         if (Math.random() < 0.2 && !entity.getPersistentData().getBoolean("throlgular") && entity.getPersistentData().getBoolean("lines_demo")) {
            if (entity instanceof DemogorgonEntity) {
               ((DemogorgonEntity)entity).setAnimation("roar");
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
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:demogorgon_roar")),
                     SoundSource.HOSTILE,
                     2.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:demogorgon_roar")),
                     SoundSource.HOSTILE,
                     2.0F,
                     1.0F,
                     false
                  );
               }
            }

            entity.getPersistentData().putBoolean("throlgular", true);
            BohMod.queueServerWork(20, () -> {
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
      }
   }
}
