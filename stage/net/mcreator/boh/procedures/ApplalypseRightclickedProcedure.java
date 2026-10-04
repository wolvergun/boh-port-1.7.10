package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.entity.DecoyDogEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.minecraft.entity.EntityLivingBase;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

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
                if (BohModItems.APPLALYPSE.get() == M.getItem((sourceentity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY))) {
                    if (!M.isClientSide(M.level(entity))) {
                        M.discard(entity);
                    }
                    if (world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.horse.eat")), SoundSource.PLAYERS, 1.0F, 1.0F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.horse.eat")), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                        }
                    }
                    if (world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.prepare_summon")), SoundSource.PLAYERS, 1.0F, 0.6F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.prepare_summon")), SoundSource.PLAYERS, 1.0F, 0.6F, false);
                        }
                    }
                    if (Math.random() < 0.25) {
                        if (Math.random() < 0.25) {
                            if (world instanceof WorldServer _level) {
                                Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.DEATH_HORSE.get()), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                                if (entityToSpawn != null) {
                                    M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                                }
                            }
                        } else if (Math.random() < 0.5) {
                            if (world instanceof WorldServer _level) {
                                Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.FAMINE_HORSE.get()), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                                if (entityToSpawn != null) {
                                    M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                                }
                            }
                        } else if (Math.random() < 0.75) {
                            if (world instanceof WorldServer _level) {
                                Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.PESTILENCE_HORSE.get()), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                                if (entityToSpawn != null) {
                                    M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                                }
                            }
                        } else if (world instanceof WorldServer _level) {
                            Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.WAR_HORSE.get()), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                            if (entityToSpawn != null) {
                                M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                            }
                        }
                    } else if (world instanceof WorldServer _level) {
                        Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.UNICORN.get()), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                            M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                        }
                    }
                }
            } else if (entity instanceof DecoyDogEntity && BohModItems.APPLALYPSE.get() == M.getItem((sourceentity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY))) {
                if (!M.isClientSide(M.level(entity))) {
                    M.discard(entity);
                }
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.prepare_summon")), SoundSource.PLAYERS, 1.0F, 0.6F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.prepare_summon")), SoundSource.PLAYERS, 1.0F, 0.6F, false);
                    }
                }
                if (world instanceof WorldServer _level) {
                    Entity entityToSpawn = M.spawn(EntityType.WOLF, _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                        M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                    }
                }
            }
        }
    }
}
