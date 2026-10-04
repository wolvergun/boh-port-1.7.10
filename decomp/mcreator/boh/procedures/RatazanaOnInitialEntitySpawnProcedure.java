package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.RatazanaEntity;
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

public class RatazanaOnInitialEntitySpawnProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (entity instanceof RatazanaEntity) {
            ((RatazanaEntity)entity).setAnimation("spawn");
         }

         if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
            _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 254, false, false));
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
                  "/execute as @e[type=boh:rat,limit=1,distance=0..2] run spreadplayers ~ ~ 3 3 false @e[type=boh:ratazana,limit=1,distance=0..2]"
               );
         }

         for (int index0 = 0; index0 < 10; index0++) {
            BohMod.queueServerWork(
               2,
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
                           "/particle minecraft:block minecraft:dirt ~ ~ ~ .1 0 .1 0 20 "
                        );
                  }

                  if (world instanceof Level _level) {
                     if (!_level.isClientSide()) {
                        _level.playSound(
                           null,
                           BlockPos.containing(x, y, z),
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.grass.break")),
                           SoundSource.HOSTILE,
                           1.0F,
                           1.0F
                        );
                     } else {
                        _level.playLocalSound(
                           x,
                           y,
                           z,
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.grass.break")),
                           SoundSource.HOSTILE,
                           1.0F,
                           1.0F,
                           false
                        );
                     }
                  }
               }
            );
         }
      }
   }
}
