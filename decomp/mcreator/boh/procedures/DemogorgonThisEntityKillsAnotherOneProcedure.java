package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.minecraft.world.scores.criteria.ObjectiveCriteria.RenderType;
import net.minecraftforge.registries.ForgeRegistries;

public class DemogorgonThisEntityKillsAnotherOneProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity) {
      if (sourceentity != null) {
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

         if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
            _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 255));
         }

         Entity _ent = sourceentity;
         Scoreboard _sc = _ent.level().getScoreboard();
         Objective _so = _sc.getObjective("anim");
         if (_so == null) {
            _so = _sc.addObjective("anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
         }

         _sc.getOrCreatePlayerScore(_ent.getScoreboardName(), _so).setScore(2);
         BohMod.queueServerWork(20, () -> {
            Entity _entx = sourceentity;
            Scoreboard _scx = _entx.level().getScoreboard();
            Objective _sox = _scx.getObjective("anim");
            if (_sox == null) {
               _sox = _scx.addObjective("anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
            }

            _scx.getOrCreatePlayerScore(_entx.getScoreboardName(), _sox).setScore(0);
         });
      }
   }
}
