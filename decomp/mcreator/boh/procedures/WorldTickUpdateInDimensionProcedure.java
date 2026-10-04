package net.mcreator.boh.procedures;

import java.util.Comparator;
import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.FlowersEntity;
import net.mcreator.boh.entity.NPC000Entity;
import net.mcreator.boh.init.BohModEntities;
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
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber
public class WorldTickUpdateInDimensionProcedure {
   @SubscribeEvent
   public static void onWorldTick(LevelTickEvent event) {
      if (event.phase == Phase.END) {
         execute(event, event.level);
      }
   }

   public static void execute(LevelAccessor world) {
      execute(null, world);
   }

   private static void execute(@Nullable Event event, LevelAccessor world) {
      if ((world instanceof Level _lvl ? _lvl.dimension() : (world instanceof WorldGenLevel _wgl ? _wgl.getLevel().dimension() : Level.OVERWORLD))
         == ResourceKey.create(Registries.DIMENSION, new ResourceLocation("boh:baseplate_dimension"))) {
         if (!BohModVariables.MapVariables.get(world).whistle_logic) {
            if (world.getEntitiesOfClass(FlowersEntity.class, AABB.ofSize(new Vec3(20.0, 65.0, 17.0), 100.0, 100.0, 100.0), e -> true).isEmpty()
               && !world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(20.0, 65.0, 17.0), 100.0, 100.0, 100.0), e -> true).isEmpty()) {
               BohModVariables.MapVariables.get(world).whisle_occurance_timer++;
               BohModVariables.MapVariables.get(world).syncData(world);
            }

            if (BohModVariables.MapVariables.get(world).whisle_occurance_timer == 1.0 && !world.isClientSide() && world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     new BlockPos(20, 65, 17),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:flowers_fight_ost")),
                     SoundSource.AMBIENT,
                     100.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     20.0,
                     65.0,
                     17.0,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:flowers_fight_ost")),
                     SoundSource.AMBIENT,
                     100.0F,
                     1.0F,
                     false
                  );
               }
            }

            if (BohModVariables.MapVariables.get(world).whisle_occurance_timer == 300.0) {
               BohModVariables.MapVariables.get(world).whistle_logic = true;
               BohModVariables.MapVariables.get(world).syncData(world);
               BohModVariables.MapVariables.get(world).whisle_occurance_timer = 0.0;
               BohModVariables.MapVariables.get(world).syncData(world);
               if (world instanceof ServerLevel _level) {
                  Entity entityToSpawn = ((EntityType)BohModEntities.FLOWERS.get()).spawn(_level, new BlockPos(20, 65, 17), MobSpawnType.MOB_SUMMONED);
                  if (entityToSpawn != null) {
                  }
               }

               BohMod.queueServerWork(300, () -> {
                  if (world instanceof ServerLevel _level) {
                     Entity entityToSpawn = ((EntityType)BohModEntities.NPC_000.get()).spawn(_level, new BlockPos(20, 65, 17), MobSpawnType.MOB_SUMMONED);
                     if (entityToSpawn != null) {
                     }
                  }

                  if (world instanceof ServerLevel _level) {
                     Entity entityToSpawn = ((EntityType)BohModEntities.NPC_000.get()).spawn(_level, new BlockPos(20, 65, 17), MobSpawnType.MOB_SUMMONED);
                     if (entityToSpawn != null) {
                     }
                  }
               });
            }
         }

         if (BohModVariables.MapVariables.get(world).whistle_logic) {
            BohModVariables.MapVariables.get(world).whistle_global_timer++;
            BohModVariables.MapVariables.get(world).syncData(world);
         }

         if (BohModVariables.MapVariables.get(world).whistle_global_timer == 2500.0) {
            BohModVariables.MapVariables.get(world).whistle_global_timer = 0.0;
            BohModVariables.MapVariables.get(world).syncData(world);
            BohModVariables.MapVariables.get(world).whistle_logic = false;
            BohModVariables.MapVariables.get(world).syncData(world);
            if (!world.getEntitiesOfClass(FlowersEntity.class, AABB.ofSize(new Vec3(20.0, 65.0, 17.0), 100.0, 100.0, 100.0), e -> true).isEmpty()
               && !world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(20.0, 65.0, 17.0), 100.0, 100.0, 100.0), e -> true).isEmpty()) {
               Vec3 _center = new Vec3(20.0, 65.0, 17.0);

               for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(50.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                  .toList()) {
                  if ((entityiterator instanceof FlowersEntity || entityiterator instanceof NPC000Entity) && !entityiterator.level().isClientSide()) {
                     entityiterator.discard();
                  }
               }
            }

            Vec3 _center = new Vec3(20.0, 65.0, 17.0);

            for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(50.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
               .toList()) {
               if (entityiterator instanceof Player) {
                  if (entityiterator instanceof ServerPlayer _player && !_player.level().isClientSide()) {
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

                  if (entityiterator instanceof Player _player) {
                     ItemStack _setstack = new ItemStack((ItemLike)BohModItems.WHISPERING_THORNS_HELMET.get()).copy();
                     _setstack.setCount(1);
                     ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
                  }

                  Entity _ent = entityiterator;
                  _ent.teleportTo(
                     entityiterator instanceof ServerPlayer _player && !_player.level().isClientSide()
                        ? (
                           _player.getRespawnDimension().equals(_player.level().dimension()) && _player.getRespawnPosition() != null
                              ? _player.getRespawnPosition().getX()
                              : _player.level().getLevelData().getXSpawn()
                        )
                        : 0.0,
                     entityiterator instanceof ServerPlayer _player && !_player.level().isClientSide()
                        ? (
                           _player.getRespawnDimension().equals(_player.level().dimension()) && _player.getRespawnPosition() != null
                              ? _player.getRespawnPosition().getY()
                              : _player.level().getLevelData().getYSpawn()
                        )
                        : 0.0,
                     entityiterator instanceof ServerPlayer _player && !_player.level().isClientSide()
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
                           entityiterator instanceof ServerPlayer _player && !_player.level().isClientSide()
                              ? (
                                 _player.getRespawnDimension().equals(_player.level().dimension()) && _player.getRespawnPosition() != null
                                    ? _player.getRespawnPosition().getX()
                                    : _player.level().getLevelData().getXSpawn()
                              )
                              : 0.0,
                           entityiterator instanceof ServerPlayer _player && !_player.level().isClientSide()
                              ? (
                                 _player.getRespawnDimension().equals(_player.level().dimension()) && _player.getRespawnPosition() != null
                                    ? _player.getRespawnPosition().getY()
                                    : _player.level().getLevelData().getYSpawn()
                              )
                              : 0.0,
                           entityiterator instanceof ServerPlayer _player && !_player.level().isClientSide()
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
               }
            }
         }
      }
   }
}
