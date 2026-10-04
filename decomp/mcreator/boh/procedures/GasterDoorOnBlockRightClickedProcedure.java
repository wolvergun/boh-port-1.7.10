package net.mcreator.boh.procedures;

import com.google.common.collect.UnmodifiableIterator;
import java.util.Map.Entry;
import net.mcreator.boh.BohMod;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class GasterDoorOnBlockRightClickedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         BohMod.queueServerWork(2, () -> {
            entity.getPersistentData().putDouble("xposp", entity.getX());
            entity.getPersistentData().putDouble("yposp", entity.getY());
            entity.getPersistentData().putDouble("zposp", entity.getZ());
            BohMod.queueServerWork(2, () -> {
               if (entity instanceof ServerPlayer _player && !_player.level().isClientSide()) {
                  ResourceKey<Level> destinationType = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("boh:gaster_dimension"));
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
            });
         });
         BlockPos _bp = BlockPos.containing(x, y, z);
         BlockState _bs = Blocks.AIR.defaultBlockState();
         BlockState _bso = world.getBlockState(_bp);
         UnmodifiableIterator var11 = _bso.getValues().entrySet().iterator();

         while (var11.hasNext()) {
            Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var11.next();
            Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
            if (_property != null && _bs.getValue(_property) != null) {
               try {
                  _bs = (BlockState)_bs.setValue(_property, entry.getValue());
               } catch (Exception var16) {
               }
            }
         }

         world.setBlock(_bp, _bs, 3);
         _bp = BlockPos.containing(x, y + 1.0, z);
         _bs = Blocks.AIR.defaultBlockState();
         _bso = world.getBlockState(_bp);
         var11 = _bso.getValues().entrySet().iterator();

         while (var11.hasNext()) {
            Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var11.next();
            Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
            if (_property != null && _bs.getValue(_property) != null) {
               try {
                  _bs = (BlockState)_bs.setValue(_property, entry.getValue());
               } catch (Exception var15) {
               }
            }
         }

         world.setBlock(_bp, _bs, 3);
      }
   }
}
