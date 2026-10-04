package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class DamageToBackroomsProcedure {
   @SubscribeEvent
   public static void onEntityAttacked(LivingHurtEvent event) {
      if (event != null && event.getEntity() != null) {
         execute(event, event.getSource(), event.getEntity());
      }
   }

   public static void execute(DamageSource damagesource, Entity entity) {
      execute(null, damagesource, entity);
   }

   private static void execute(@Nullable Event event, DamageSource damagesource, Entity entity) {
      if (damagesource != null && entity != null) {
         if (entity instanceof Player
            && damagesource.is(DamageTypes.IN_WALL)
            && Math.random() < 0.005
            && entity instanceof ServerPlayer _player
            && !_player.level().isClientSide()) {
            ResourceKey<Level> destinationType = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("boh:level_0"));
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
      }
   }
}
