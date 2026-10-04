package net.mcreator.boh.procedures;

import java.util.Comparator;
import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.BenDrownedEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class TeleportbenProcedure {
   @SubscribeEvent
   public static void onPlayerTick(PlayerTickEvent event) {
      if (event.phase == Phase.END) {
         execute(event, event.player.level(), event.player.getX(), event.player.getY(), event.player.getZ(), event.player);
      }
   }

   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      execute(null, world, x, y, z, entity);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if ((new Object() {
                  public boolean checkGamemode(Entity _ent) {
                     if (_ent instanceof ServerPlayer _serverPlayer) {
                        return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.SURVIVAL;
                     } else {
                        return _ent.level().isClientSide() && _ent instanceof Player _player
                           ? Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
                              && Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.SURVIVAL
                           : false;
                     }
                  }
               })
               .checkGamemode(entity)
            && world.getEntitiesOfClass(BenDrownedEntity.class, AABB.ofSize(new Vec3(x, y, z), 20.0, 20.0, 20.0), e -> true).isEmpty()) {
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(10.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
               .toList()) {
               if (entityiterator instanceof BenDrownedEntity
                  && !entityiterator.getPersistentData().getBoolean("inWater")
                  && Math.random() < 0.1
                  && Math.random() < 0.1) {
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
                           "/scoreboard players set @e[type=boh:ben_drowned,distance=0..20,limit=1] anim 1"
                        );
                  }

                  BohMod.queueServerWork(
                     10,
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
                                 "/execute at @p[gamemode=survival] rotated ~ 1 run spreadplayers ~ ~ 1 2 false @e[type=boh:ben_drowned,limit=1]"
                              );
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
                                 "/execute at @e[type=boh:ben_drowned,distance=0..5] run playsound boh:ben_laughing hostile @p ~ ~ ~"
                              );
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
                                 "/scoreboard players set @e[type=boh:ben_drowned,distance=0..20,limit=1] anim 2"
                              );
                        }

                        BohMod.queueServerWork(
                           10,
                           () -> {
                              Entity _entxx = entity;
                              if (!_entxx.level().isClientSide() && _entxx.getServer() != null) {
                                 _entxx.getServer()
                                    .getCommands()
                                    .performPrefixedCommand(
                                       new CommandSourceStack(
                                          CommandSource.NULL,
                                          _entxx.position(),
                                          _entxx.getRotationVector(),
                                          _entxx.level() instanceof ServerLevel ? (ServerLevel)_entxx.level() : null,
                                          4,
                                          _entxx.getName().getString(),
                                          _entxx.getDisplayName(),
                                          _entxx.level().getServer(),
                                          _entxx
                                       ),
                                       "/scoreboard players set @e[type=boh:ben_drowned,distance=0..20,limit=1] anim 0"
                                    );
                              }
                           }
                        );
                     }
                  );
               }
            }
         }
      }
   }
}
