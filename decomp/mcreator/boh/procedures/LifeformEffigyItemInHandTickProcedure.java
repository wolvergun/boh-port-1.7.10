package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class LifeformEffigyItemInHandTickProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         if ((
               (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == itemstack.getItem()
                  || (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == itemstack.getItem()
            )
            && (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1.0F)
               < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1.0F) / 4.0F
            && !itemstack.getOrCreateTag().getBoolean("use")) {
            itemstack.getOrCreateTag().putBoolean("use", true);
            if (!(new Object() {
                  public boolean checkGamemode(Entity _ent) {
                     if (_ent instanceof ServerPlayer _serverPlayer) {
                        return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
                     } else {
                        return _ent.level().isClientSide() && _ent instanceof Player _player
                           ? Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
                              && Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE
                           : false;
                     }
                  }
               })
               .checkGamemode(entity)) {
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
                        "/particle minecraft:squid_ink ~ ~1 ~ .25 .5 .25 0 20"
                     );
               }

               if (world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:effigy_sound")),
                        SoundSource.PLAYERS,
                        0.5F,
                        1.2F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:effigy_sound")),
                        SoundSource.PLAYERS,
                        0.5F,
                        1.2F,
                        false
                     );
                  }
               }

               if (world.isClientSide()) {
                  Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack((ItemLike)BohModItems.LIFEFORM_EFFIGY.get()));
               }

               itemstack.getOrCreateTag().putBoolean("sound", true);
               if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                  _entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 255, false, false));
               }

               _ent = entity;
               _ent.teleportTo(
                  entity instanceof ServerPlayer _player && !_player.level().isClientSide()
                     ? (
                        _player.getRespawnDimension().equals(_player.level().dimension()) && _player.getRespawnPosition() != null
                           ? _player.getRespawnPosition().getX()
                           : _player.level().getLevelData().getXSpawn()
                     )
                     : 0.0,
                  entity instanceof ServerPlayer _player && !_player.level().isClientSide()
                     ? (
                        _player.getRespawnDimension().equals(_player.level().dimension()) && _player.getRespawnPosition() != null
                           ? _player.getRespawnPosition().getY()
                           : _player.level().getLevelData().getYSpawn()
                     )
                     : 0.0,
                  entity instanceof ServerPlayer _player && !_player.level().isClientSide()
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
                        entity instanceof ServerPlayer _player && !_player.level().isClientSide()
                           ? (
                              _player.getRespawnDimension().equals(_player.level().dimension()) && _player.getRespawnPosition() != null
                                 ? _player.getRespawnPosition().getX()
                                 : _player.level().getLevelData().getXSpawn()
                           )
                           : 0.0,
                        entity instanceof ServerPlayer _player && !_player.level().isClientSide()
                           ? (
                              _player.getRespawnDimension().equals(_player.level().dimension()) && _player.getRespawnPosition() != null
                                 ? _player.getRespawnPosition().getY()
                                 : _player.level().getLevelData().getYSpawn()
                           )
                           : 0.0,
                        entity instanceof ServerPlayer _player && !_player.level().isClientSide()
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

               BohMod.queueServerWork(10, () -> {
                  itemstack.getOrCreateTag().putBoolean("use", false);
                  if (entity instanceof Player _playerx) {
                     ItemStack _stktoremove = itemstack;
                     _playerx.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _playerx.inventoryMenu.getCraftSlots());
                  }
               });
            }
         }
      }
   }
}
