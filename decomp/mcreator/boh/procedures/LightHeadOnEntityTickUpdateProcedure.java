package net.mcreator.boh.procedures;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class LightHeadOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
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
                  "/fill ~-1 ~ ~-1 ~1 ~9 ~1 air replace #minecraft:leaves"
               );
         }

         if (entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
            entity.getPersistentData().putDouble("timer_step", entity.getPersistentData().getDouble("timer_step") + 1.0);
         } else {
            entity.getPersistentData().putDouble("timer_step", 0.0);
         }

         if (entity instanceof Mob _mob && _mob.isAggressive()) {
            if (entity instanceof Mob _mobx && _mobx.isAggressive() && entity.getPersistentData().getDouble("timer_step") == 9.0) {
               if (!world.isClientSide() && world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")),
                        SoundSource.HOSTILE,
                        2.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")),
                        SoundSource.HOSTILE,
                        2.0F,
                        1.0F,
                        false
                     );
                  }
               }

               entity.getPersistentData().putDouble("timer_step", 0.0);
            }
         } else if (entity.getPersistentData().getDouble("timer_step") == 22.0) {
            if (!world.isClientSide() && world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")),
                     SoundSource.HOSTILE,
                     2.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")), SoundSource.HOSTILE, 2.0F, 1.0F, false
                  );
               }
            }

            entity.getPersistentData().putDouble("timer_step", 0.0);
         }
      }
   }
}
