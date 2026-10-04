package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.entity.DecoyDogEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

public class ApplalypseRightclickedProcedure {
    @SubscribeEvent
    public void onRightClickEntity(EntityInteractEvent event) {
        if (M.getHand(event) == M.getUsedItemHand(M.getEntity(event))) {
            execute(event, M.getLevel(event), M.getX(M.getPos(event)), M.getY(M.getPos(event)), M.getZ(M.getPos(event)), M.getTarget(event), M.getEntity(event));
        }
    }

    public static void execute(World world, double x, double y, double z, Entity entity, Entity sourceentity) {
        execute(null, world, x, y, z, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null) {
            if (entity instanceof EntityHorse) {
                if (BohModItems.APPLALYPSE.get() == M.getItem(sourceentity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY)) {
                    if (!M.isClientSide(M.level(entity))) {
                        M.discard(entity);
                    }

                    if (world instanceof World) {
                        if (!M.isClientSide(world)) {
                            M.playSound(
                                world,
                                null,
                                BlockPos.containing(x, y, z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.horse.eat")),
                                SoundSource.PLAYERS,
                                1.0F,
                                1.0F
                            );
                        } else {
                            M.playLocalSound(
                                world,
                                x,
                                y,
                                z,
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.horse.eat")),
                                SoundSource.PLAYERS,
                                1.0F,
                                1.0F,
                                false
                            );
                        }
                    }

                    if (world instanceof World) {
                        if (!M.isClientSide(world)) {
                            M.playSound(
                                world,
                                null,
                                BlockPos.containing(x, y, z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.prepare_summon")),
                                SoundSource.PLAYERS,
                                1.0F,
                                0.6F
                            );
                        } else {
                            M.playLocalSound(
                                world,
                                x,
                                y,
                                z,
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.prepare_summon")),
                                SoundSource.PLAYERS,
                                1.0F,
                                0.6F,
                                false
                            );
                        }
                    }

                    if (Math.random() < 0.25) {
                        if (Math.random() < 0.25) {
                            if (world instanceof WorldServer _level) {
                                Entity entityToSpawn = M.spawn(
                                    BohModEntities.DEATH_HORSE.get(), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED
                                );
                                if (entityToSpawn != null) {
                                    M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                                }
                            }
                        } else if (Math.random() < 0.5) {
                            if (world instanceof WorldServer _levelx) {
                                Entity entityToSpawn = M.spawn(
                                    BohModEntities.FAMINE_HORSE.get(), _levelx, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED
                                );
                                if (entityToSpawn != null) {
                                    M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                                }
                            }
                        } else if (Math.random() < 0.75) {
                            if (world instanceof WorldServer _levelxx) {
                                Entity entityToSpawn = M.spawn(
                                    BohModEntities.PESTILENCE_HORSE.get(), _levelxx, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED
                                );
                                if (entityToSpawn != null) {
                                    M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                                }
                            }
                        } else if (world instanceof WorldServer _levelxxx) {
                            Entity entityToSpawn = M.spawn(BohModEntities.WAR_HORSE.get(), _levelxxx, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                            if (entityToSpawn != null) {
                                M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                            }
                        }
                    } else if (world instanceof WorldServer _levelxxxx) {
                        Entity entityToSpawn = M.spawn(BohModEntities.UNICORN.get(), _levelxxxx, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                            M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                        }
                    }
                }
            } else if (entity instanceof DecoyDogEntity
                && BohModItems.APPLALYPSE.get() == M.getItem(sourceentity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY)) {
                if (!M.isClientSide(M.level(entity))) {
                    M.discard(entity);
                }

                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.prepare_summon")),
                            SoundSource.PLAYERS,
                            1.0F,
                            0.6F
                        );
                    } else {
                        M.playLocalSound(
                            world,
                            x,
                            y,
                            z,
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.prepare_summon")),
                            SoundSource.PLAYERS,
                            1.0F,
                            0.6F,
                            false
                        );
                    }
                }

                if (world instanceof WorldServer _levelxxxxx) {
                    Entity entityToSpawn = M.spawn(EntityType.WOLF, _levelxxxxx, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                        M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                    }
                }
            }
        }
    }
}
