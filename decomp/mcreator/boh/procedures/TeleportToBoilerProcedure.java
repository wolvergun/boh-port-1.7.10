package net.mcreator.boh.procedures;

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
import net.minecraft.world.level.Level;

public class TeleportToBoilerProcedure {
   public static void execute(Entity entity, ItemStack itemstack) {
      if (entity != null) {
         if (entity instanceof ServerPlayer _player && !_player.level().isClientSide()) {
            ResourceKey<Level> destinationType = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("boh:boiler_room_dimension"));
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

         if (entity instanceof Player _player) {
            ItemStack _stktoremove = itemstack;
            _player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
         }
      }
   }
}
