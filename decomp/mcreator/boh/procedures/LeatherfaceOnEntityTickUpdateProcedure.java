package net.mcreator.boh.procedures;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class LeatherfaceOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         entity.getPersistentData().putDouble("timer_sound", entity.getPersistentData().getDouble("timer_sound") + 1.0);
         if (entity.getPersistentData().getDouble("timer_sound") == 14.0) {
            if (world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chainsaw_loop")),
                     SoundSource.PLAYERS,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chainsaw_loop")),
                     SoundSource.PLAYERS,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }

            entity.getPersistentData().putDouble("timer_sound", 0.0);
         }

         if (Math.random() < 0.2) {
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
                     "/execute as @s run particle minecraft:smoke ^-.2 ^1.1 ^.5 0.0 0.0 0.0 0.01 1 force"
                  );
            }
         }
      }
   }
}
