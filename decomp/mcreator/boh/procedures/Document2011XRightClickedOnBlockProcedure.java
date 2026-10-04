package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.init.BohModParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class Document2011XRightClickedOnBlockProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         if (world.isEmptyBlock(BlockPos.containing(x, y + 1.0, z))) {
            if (world.getBlockState(BlockPos.containing(x + 1.0, y + 1.0, z)).getBlock() == BohModBlocks.RIFT_STABILIZER.get()
               && world.getBlockState(BlockPos.containing(x - 1.0, y + 1.0, z)).getBlock() == BohModBlocks.RIFT_STABILIZER.get()
               && world.getBlockState(BlockPos.containing(x, y + 1.0, z + 1.0)).getBlock() == BohModBlocks.RIFT_STABILIZER.get()
               && world.getBlockState(BlockPos.containing(x, y + 1.0, z - 1.0)).getBlock() == BohModBlocks.RIFT_STABILIZER.get()) {
               if (world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rift_open")),
                        SoundSource.AMBIENT,
                        2.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rift_open")),
                        SoundSource.AMBIENT,
                        2.0F,
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
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:page_pickup")),
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:page_pickup")),
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F,
                        false
                     );
                  }
               }

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
                     .checkGamemode(entity)
                  && entity instanceof Player _player) {
                  ItemStack _stktoremove = itemstack;
                  _player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
               }

               world.addParticle((SimpleParticleType)BohModParticleTypes.RIFT_TEAR_PARTICLE.get(), x + 0.5, y + 2.2, z + 0.5, 0.0, 0.0, 0.0);
               BohMod.queueServerWork(38, () -> {
                  if (world instanceof ServerLevel _level) {
                     LightningBolt entityToSpawn = (LightningBolt)EntityType.LIGHTNING_BOLT.create(_level);
                     entityToSpawn.moveTo(Vec3.atBottomCenterOf(BlockPos.containing(x + 0.5, y + 1.0, z + 0.5)));
                     entityToSpawn.setVisualOnly(true);
                     _level.addFreshEntity(entityToSpawn);
                  }
               });
               BohMod.queueServerWork(
                  40,
                  () -> {
                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = ((EntityType)BohModEntities.SONIC_EXE.get())
                           .spawn(_level, BlockPos.containing(x + 0.5, y + 1.0, z + 0.5), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                        }
                     }
                  }
               );
               BohMod.queueServerWork(5, () -> {
                  if (world instanceof Level _level && !_level.isClientSide()) {
                     _level.explode(null, x + 1.0, y + 1.0, z, 1.0F, ExplosionInteraction.NONE);
                  }

                  world.destroyBlock(BlockPos.containing(x + 1.0, y + 1.0, z), false);
               });
               BohMod.queueServerWork(10, () -> {
                  if (world instanceof Level _level && !_level.isClientSide()) {
                     _level.explode(null, x - 1.0, y + 1.0, z, 1.0F, ExplosionInteraction.NONE);
                  }

                  world.destroyBlock(BlockPos.containing(x - 1.0, y + 1.0, z), false);
               });
               BohMod.queueServerWork(15, () -> {
                  if (world instanceof Level _level && !_level.isClientSide()) {
                     _level.explode(null, x, y + 1.0, z + 1.0, 1.0F, ExplosionInteraction.NONE);
                  }

                  world.destroyBlock(BlockPos.containing(x, y + 1.0, z + 1.0), false);
               });
               BohMod.queueServerWork(20, () -> {
                  if (world instanceof Level _level && !_level.isClientSide()) {
                     _level.explode(null, x, y + 1.0, z - 1.0, 1.0F, ExplosionInteraction.NONE);
                  }

                  world.destroyBlock(BlockPos.containing(x, y + 1.0, z - 1.0), false);
               });
            } else if ((
                  world.getBlockState(BlockPos.containing(x + 1.0, y + 1.0, z)).getBlock() != BohModBlocks.RIFT_STABILIZER.get()
                     || world.getBlockState(BlockPos.containing(x - 1.0, y + 1.0, z)).getBlock() != BohModBlocks.RIFT_STABILIZER.get()
                     || world.getBlockState(BlockPos.containing(x, y + 1.0, z + 1.0)).getBlock() != BohModBlocks.RIFT_STABILIZER.get()
                     || world.getBlockState(BlockPos.containing(x, y + 1.0, z - 1.0)).getBlock() != BohModBlocks.RIFT_STABILIZER.get()
               )
               && entity instanceof Player _player
               && !_player.level().isClientSide()) {
               _player.displayClientMessage(Component.literal("Place 4 Rift Stabilizers around the target block to stabilize the summoning."), true);
            }
         }
      }
   }
}
