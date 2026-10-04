package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.network.BohModVariables;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class SaucerOnInitialEntitySpawnProcedure {
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
                  "/team add alien"
               );
         }

         BohMod.queueServerWork(
            2,
            () -> {
               Entity _entx = entity;
               _entx.teleportTo(x, y + 20.0, z);
               if (_entx instanceof ServerPlayer _serverPlayer) {
                  _serverPlayer.connection.teleport(x, y + 20.0, z, _entx.getYRot(), _entx.getXRot());
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
                        "/team modify alien friendlyFire false"
                     );
               }
            }
         );
         BohMod.queueServerWork(
            5,
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
                        "/team join alien @e[type=boh:saucer]"
                     );
               }
            }
         );
         if (world instanceof Level _level) {
            if (!_level.isClientSide()) {
               _level.playSound(
                  null,
                  BlockPos.containing(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mother_ship_loop")),
                  SoundSource.HOSTILE,
                  100.0F,
                  1.0F
               );
            } else {
               _level.playLocalSound(
                  x,
                  y,
                  z,
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mother_ship_loop")),
                  SoundSource.HOSTILE,
                  100.0F,
                  1.0F,
                  false
               );
            }
         }

         if (world instanceof Level _level) {
            if (!_level.isClientSide()) {
               _level.playSound(
                  null,
                  BlockPos.containing(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:gray_ost")),
                  SoundSource.MUSIC,
                  1.0F,
                  1.0F
               );
            } else {
               _level.playLocalSound(
                  x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:gray_ost")), SoundSource.MUSIC, 1.0F, 1.0F, false
               );
            }
         }

         BohModVariables.MapVariables.get(world).spawn_saucer = 1.0;
         BohModVariables.MapVariables.get(world).syncData(world);
      }
   }
}
