package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class EscapeNightmareBlockEntityCollidesInTheBlockProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         if (entity instanceof ServerPlayer _player && !_player.level().isClientSide()) {
            ResourceKey<Level> destinationType = Level.OVERWORLD;
            if (_player.level().dimension() == destinationType) {
               return;
            }

            ServerLevel nextLevel = _player.server.getLevel(destinationType);
            if (nextLevel != null) {
               _player.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.WIN_GAME, 0.0F));
               _player.teleportTo(nextLevel, _player.getX(), _player.getY(), _player.getZ(), _player.getYRot(), _player.getXRot());
               _player.connection.send(new ClientboundPlayerAbilitiesPacket(_player.getAbilities()));

               for (MobEffectInstance _effectinstance : _player.getActiveEffects()) {
                  _player.connection.send(new ClientboundUpdateMobEffectPacket(_player.getId(), _effectinstance));
               }

               _player.connection.send(new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));
            }
         }

         BohMod.queueServerWork(
            10,
            () -> {
               Entity _ent = entity;
               _ent.teleportTo(
                  entity instanceof ServerPlayer _playerxxxxxx && !_playerxxxxxx.level().isClientSide()
                     ? (
                        _playerxxxxxx.getRespawnDimension().equals(_playerxxxxxx.level().dimension()) && _playerxxxxxx.getRespawnPosition() != null
                           ? _playerxxxxxx.getRespawnPosition().getX()
                           : _playerxxxxxx.level().getLevelData().getXSpawn()
                     )
                     : 0.0,
                  entity instanceof ServerPlayer _playerxx && !_playerxx.level().isClientSide()
                     ? (
                        _playerxx.getRespawnDimension().equals(_playerxx.level().dimension()) && _playerxx.getRespawnPosition() != null
                           ? _playerxx.getRespawnPosition().getY()
                           : _playerxx.level().getLevelData().getYSpawn()
                     )
                     : 0.0,
                  entity instanceof ServerPlayer _playerx && !_playerx.level().isClientSide()
                     ? (
                        _playerx.getRespawnDimension().equals(_playerx.level().dimension()) && _playerx.getRespawnPosition() != null
                           ? _playerx.getRespawnPosition().getZ()
                           : _playerx.level().getLevelData().getZSpawn()
                     )
                     : 0.0
               );
               if (_ent instanceof ServerPlayer _serverPlayer) {
                  _serverPlayer.connection
                     .teleport(
                        entity instanceof ServerPlayer _playerxxxxx && !_playerxxxxx.level().isClientSide()
                           ? (
                              _playerxxxxx.getRespawnDimension().equals(_playerxxxxx.level().dimension()) && _playerxxxxx.getRespawnPosition() != null
                                 ? _playerxxxxx.getRespawnPosition().getX()
                                 : _playerxxxxx.level().getLevelData().getXSpawn()
                           )
                           : 0.0,
                        entity instanceof ServerPlayer _playerxxxx && !_playerxxxx.level().isClientSide()
                           ? (
                              _playerxxxx.getRespawnDimension().equals(_playerxxxx.level().dimension()) && _playerxxxx.getRespawnPosition() != null
                                 ? _playerxxxx.getRespawnPosition().getY()
                                 : _playerxxxx.level().getLevelData().getYSpawn()
                           )
                           : 0.0,
                        entity instanceof ServerPlayer _playerxxx && !_playerxxx.level().isClientSide()
                           ? (
                              _playerxxx.getRespawnDimension().equals(_playerxxx.level().dimension()) && _playerxxx.getRespawnPosition() != null
                                 ? _playerxxx.getRespawnPosition().getZ()
                                 : _playerxxx.level().getLevelData().getZSpawn()
                           )
                           : 0.0,
                        _ent.getYRot(),
                        _ent.getXRot()
                     );
               }

               entity.fallDistance = 0.0F;
            }
         );
      }
   }
}
