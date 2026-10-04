package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.KrasueEntity;
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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.minecraft.world.scores.criteria.ObjectiveCriteria.RenderType;
import net.minecraftforge.registries.ForgeRegistries;

public class KrasueOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (entity instanceof Mob _mob && _mob.isAggressive()) {
            entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * 0.5, entity.getLookAngle().y * 0.9, entity.getLookAngle().z * 0.5));
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
                     "/execute as @e[type=boh:krasue,distance=0..1] at @s facing entity @e[type=minecraft:player,tag=krasue,sort=nearest,limit=1] feet run tp @e[type=boh:krasue] ^ ^ ^0.1 ~ ~"
                  );
            }
         }

         entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * 0.2, entity.getLookAngle().y * 0.9, entity.getLookAngle().z * 0.2));
         if (!entity.getPersistentData().getBoolean("nemesis_roar") && (entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof LivingEntity) {
            entity.getPersistentData().putBoolean("nemesis_roar", true);
         }

         if (Math.random() < 0.25 && !entity.getPersistentData().getBoolean("switch") && entity.getPersistentData().getBoolean("nemesis_roar")) {
            if (entity instanceof KrasueEntity) {
               ((KrasueEntity)entity).setAnimation("scream");
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
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:krasue_scream")),
                     SoundSource.HOSTILE,
                     3.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:krasue_scream")),
                     SoundSource.HOSTILE,
                     3.0F,
                     1.0F,
                     false
                  );
               }
            }

            entity.getPersistentData().putBoolean("switch", true);
            BohMod.queueServerWork(70, () -> {
               Entity _entx = entity;
               Scoreboard _scx = _entx.level().getScoreboard();
               Objective _sox = _scx.getObjective("anim");
               if (_sox == null) {
                  _sox = _scx.addObjective("anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
               }

               _scx.getOrCreatePlayerScore(_entx.getScoreboardName(), _sox).setScore(0);
            });
         }

         if (!((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof LivingEntity)) {
            entity.getPersistentData().putBoolean("nemesis_roar", false);
            entity.getPersistentData().putBoolean("switch", false);
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

         if ((new Object() {
            public int getScore(String score, Entity _ent) {
               Scoreboard _sc = _ent.level().getScoreboard();
               Objective _so = _sc.getObjective(score);
               return _so != null ? _sc.getOrCreatePlayerScore(_ent.getScoreboardName(), _so).getScore() : 0;
            }
         }).getScore("anim", entity) == 3) {
            entity.makeStuckInBlock(Blocks.AIR.defaultBlockState(), new Vec3(0.25, 0.05, 0.25));
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 255, false, false));
            }
         }
      }
   }
}
