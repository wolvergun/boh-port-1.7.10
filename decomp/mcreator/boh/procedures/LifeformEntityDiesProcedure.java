package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.network.BohModVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.items.ItemHandlerHelper;

public class LifeformEntityDiesProcedure {
   public static void execute(LevelAccessor world, Entity sourceentity) {
      if (sourceentity != null) {
         BohModVariables.MapVariables.get(world).spawn_lifeform = false;
         BohModVariables.MapVariables.get(world).syncData(world);
         if (sourceentity.level().dimension() == ResourceKey.create(Registries.DIMENSION, new ResourceLocation("boh:level_0"))) {
            if (sourceentity instanceof ServerPlayer _player && !_player.level().isClientSide()) {
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

            Entity _ent = sourceentity;
            _ent.teleportTo(
               sourceentity instanceof ServerPlayer _player && !_player.level().isClientSide()
                  ? (
                     _player.getRespawnDimension().equals(_player.level().dimension()) && _player.getRespawnPosition() != null
                        ? _player.getRespawnPosition().getX()
                        : _player.level().getLevelData().getXSpawn()
                  )
                  : 0.0,
               sourceentity instanceof ServerPlayer _player && !_player.level().isClientSide()
                  ? (
                     _player.getRespawnDimension().equals(_player.level().dimension()) && _player.getRespawnPosition() != null
                        ? _player.getRespawnPosition().getY()
                        : _player.level().getLevelData().getYSpawn()
                  )
                  : 0.0,
               sourceentity instanceof ServerPlayer _player && !_player.level().isClientSide()
                  ? (
                     _player.getRespawnDimension().equals(_player.level().dimension()) && _player.getRespawnPosition() != null
                        ? _player.getRespawnPosition().getZ()
                        : _player.level().getLevelData().getZSpawn()
                  )
                  : 0.0
            );
            if (_ent instanceof ServerPlayer _serverPlayer) {
               _serverPlayer.connection
                  .teleport(
                     sourceentity instanceof ServerPlayer _player && !_player.level().isClientSide()
                        ? (
                           _player.getRespawnDimension().equals(_player.level().dimension()) && _player.getRespawnPosition() != null
                              ? _player.getRespawnPosition().getX()
                              : _player.level().getLevelData().getXSpawn()
                        )
                        : 0.0,
                     sourceentity instanceof ServerPlayer _player && !_player.level().isClientSide()
                        ? (
                           _player.getRespawnDimension().equals(_player.level().dimension()) && _player.getRespawnPosition() != null
                              ? _player.getRespawnPosition().getY()
                              : _player.level().getLevelData().getYSpawn()
                        )
                        : 0.0,
                     sourceentity instanceof ServerPlayer _player && !_player.level().isClientSide()
                        ? (
                           _player.getRespawnDimension().equals(_player.level().dimension()) && _player.getRespawnPosition() != null
                              ? _player.getRespawnPosition().getZ()
                              : _player.level().getLevelData().getZSpawn()
                        )
                        : 0.0,
                     _ent.getYRot(),
                     _ent.getXRot()
                  );
            }

            if (sourceentity instanceof Player _player) {
               ItemStack _setstack = new ItemStack((ItemLike)BohModItems.LIFEFORM_EFFIGY.get()).copy();
               _setstack.setCount(1);
               ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
            }
         }
      }
   }
}
