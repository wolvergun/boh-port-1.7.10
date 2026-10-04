package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.init.BohModParticleTypes;
import net.minecraft.client.Minecraft;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.particles.SimpleParticleType;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.level.GameType;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.ExplosionInteraction;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class DocumentScissormanRightClickedOnBlockProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (M.isEmptyBlock(world, BlockPos.containing(x, y + 1.0, z))) {
                if (M.getBlock(M.getBlockState(world, BlockPos.containing(x + 1.0, y + 1.0, z))) == BohModBlocks.RIFT_STABILIZER.get() && M.getBlock(M.getBlockState(world, BlockPos.containing(x - 1.0, y + 1.0, z))) == BohModBlocks.RIFT_STABILIZER.get() && M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z + 1.0))) == BohModBlocks.RIFT_STABILIZER.get() && M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z - 1.0))) == BohModBlocks.RIFT_STABILIZER.get()) {
                    if (world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:page_pickup")), SoundSource.PLAYERS, 1.0F, 1.0F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:page_pickup")), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                        }
                    }
                    if (world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rift_open")), SoundSource.AMBIENT, 2.0F, 1.0F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rift_open")), SoundSource.AMBIENT, 2.0F, 1.0F, false);
                        }
                    }
                    if (!(new Object() {

                        public boolean checkGamemode(Entity _ent) {
                            if (_ent instanceof EntityPlayerMP _serverPlayer) {
                                return M.getGameModeForPlayer(M.gameMode(_serverPlayer)) == GameType.CREATIVE;
                            } else {
                                return M.isClientSide(M.level(_ent)) && _ent instanceof EntityPlayer _player ? M.getPlayerInfo(M.getConnection(Minecraft.getMinecraft()), M.getId(M.getGameProfile(_player))) != null && M.getGameMode(M.getPlayerInfo(M.getConnection(Minecraft.getMinecraft()), M.getId(M.getGameProfile(_player)))) == GameType.CREATIVE : false;
                            }
                        }
                    }).checkGamemode(entity) && entity instanceof EntityPlayer _player) {
                        ItemStack _stktoremove = itemstack;
                        M.clearOrCountMatchingItems(M.getInventory(_player), p -> M.getItem(_stktoremove) == M.getItem(p), 1, M.getCraftSlots(M.inventoryMenu(_player)));
                    }
                    M.addParticle(world, (SimpleParticleType) BohModParticleTypes.RIFT_TEAR_PARTICLE.get(), x + 0.5, y + 2.2, z + 0.5, 0.0, 0.0, 0.0);
                    BohMod.queueServerWork(38, () -> {
                        if (world instanceof WorldServer _level) {
                            EntityLightningBolt entityToSpawn = (EntityLightningBolt) M.create(EntityType.LIGHTNING_BOLT, _level);
                            M.moveTo(entityToSpawn, Vec3.atBottomCenterOf(BlockPos.containing(x + 0.5, y + 1.0, z + 0.5)));
                            M.setVisualOnly(entityToSpawn, true);
                            M.addFreshEntity(_level, entityToSpawn);
                        }
                    });
                    BohMod.queueServerWork(40, () -> {
                        if (world instanceof WorldServer _level) {
                            Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.SCISSORMAN.get()), _level, BlockPos.containing(x + 0.5, y + 1.0, z + 0.5), MobSpawnType.MOB_SUMMONED);
                            if (entityToSpawn != null) {
                            }
                        }
                    });
                    BohMod.queueServerWork(5, () -> {
                        if (world instanceof World _level && !M.isClientSide(_level)) {
                            M.explode(_level, null, x + 1.0, y + 1.0, z, 1.0F, ExplosionInteraction.NONE);
                        }
                        M.destroyBlock(world, BlockPos.containing(x + 1.0, y + 1.0, z), false);
                    });
                    BohMod.queueServerWork(10, () -> {
                        if (world instanceof World _level && !M.isClientSide(_level)) {
                            M.explode(_level, null, x - 1.0, y + 1.0, z, 1.0F, ExplosionInteraction.NONE);
                        }
                        M.destroyBlock(world, BlockPos.containing(x - 1.0, y + 1.0, z), false);
                    });
                    BohMod.queueServerWork(15, () -> {
                        if (world instanceof World _level && !M.isClientSide(_level)) {
                            M.explode(_level, null, x, y + 1.0, z + 1.0, 1.0F, ExplosionInteraction.NONE);
                        }
                        M.destroyBlock(world, BlockPos.containing(x, y + 1.0, z + 1.0), false);
                    });
                    BohMod.queueServerWork(20, () -> {
                        if (world instanceof World _level && !M.isClientSide(_level)) {
                            M.explode(_level, null, x, y + 1.0, z - 1.0, 1.0F, ExplosionInteraction.NONE);
                        }
                        M.destroyBlock(world, BlockPos.containing(x, y + 1.0, z - 1.0), false);
                    });
                } else if ((M.getBlock(M.getBlockState(world, BlockPos.containing(x + 1.0, y + 1.0, z))) != BohModBlocks.RIFT_STABILIZER.get() || M.getBlock(M.getBlockState(world, BlockPos.containing(x - 1.0, y + 1.0, z))) != BohModBlocks.RIFT_STABILIZER.get() || M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z + 1.0))) != BohModBlocks.RIFT_STABILIZER.get() || M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z - 1.0))) != BohModBlocks.RIFT_STABILIZER.get()) && entity instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                    M.displayClientMessage(_player, Component.literal("Place 4 Rift Stabilizers around the target block to stabilize the summoning."), true);
                }
            }
        }
    }
}
