package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.GasterEntity;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class GasterBlockExitOnBlockRightClickedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         BohMod.queueServerWork(
            2,
            () -> {
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
                  2,
                  () -> {
                     Entity _ent = entity;
                     _ent.teleportTo(
                        entity.getPersistentData().getDouble("xposp"),
                        entity.getPersistentData().getDouble("yposp"),
                        entity.getPersistentData().getDouble("zposp")
                     );
                     if (_ent instanceof ServerPlayer _serverPlayer) {
                        _serverPlayer.connection
                           .teleport(
                              entity.getPersistentData().getDouble("xposp"),
                              entity.getPersistentData().getDouble("yposp"),
                              entity.getPersistentData().getDouble("zposp"),
                              _ent.getYRot(),
                              _ent.getXRot()
                           );
                     }
                  }
               );
            }
         );
         Vec3 _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(50.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
            .toList()) {
            if (entityiterator instanceof GasterEntity && !entityiterator.level().isClientSide()) {
               entityiterator.discard();
            }
         }
      }
   }
}
