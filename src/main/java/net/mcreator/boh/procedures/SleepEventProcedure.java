package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundGameEventPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundLevelEventPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.world.Dimensions;
import net.mcreator.boh.entity.EyelessJackEntity;
import net.mcreator.boh.entity.JeffTheKillerEntity;
import net.mcreator.boh.entity.RakeEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;

public class SleepEventProcedure {
    @SubscribeEvent
    public void onPlayerInBed(PlayerSleepInBedEvent event) {
        execute(event, M.level(M.getEntity(event)), M.getX(M.getPos(event)), M.getY(M.getPos(event)), M.getZ(M.getPos(event)), M.getEntity(event));
    }

    public static void execute(World world, double x, double y, double z, Entity entity) {
        execute(null, world, x, y, z, entity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity) {
        if (entity != null
            && !M.isClientSide(world)
            && !(entity instanceof EntityLivingBase _livEnt1 && M.hasEffect(_livEnt1, BohModMobEffects.SAFE_AND_SOUND.get()))
            && Math.random() < 0.1
            && Math.random() < 0.1) {
            if (Math.random() < 0.8) {
                if (M.isEmpty(M.getEntitiesOfClass(world, JeffTheKillerEntity.class, AABB.ofSize(new Vec3(x, y, z), 900.0, 900.0, 900.0), e -> true))
                    && (!(world instanceof World) || !M.isDay(world))) {
                    if (entity instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("§l§4 GO TO SLEEP"), true);
                    }

                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.BLINDNESS, 120, 0, false, false));
                    }

                    if (world instanceof World) {
                        if (!M.isClientSide(world)) {
                            M.playSound(
                                world,
                                null,
                                BlockPos.containing(x, y, z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:jeff_sleep")),
                                SoundSource.HOSTILE,
                                1.0F,
                                1.0F
                            );
                        } else {
                            M.playLocalSound(
                                world,
                                x,
                                y,
                                z,
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:jeff_sleep")),
                                SoundSource.HOSTILE,
                                1.0F,
                                1.0F,
                                false
                            );
                        }
                    }

                    if (M.isEmpty(M.getEntitiesOfClass(world, JeffTheKillerEntity.class, AABB.ofSize(new Vec3(x, y, z), 50.0, 50.0, 50.0), e -> true))) {
                        if (world instanceof WorldServer _level) {
                            Entity entityToSpawn = M.spawn(
                                BohModEntities.JEFF_THE_KILLER.get(), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED
                            );
                            if (entityToSpawn != null) {
                                M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                            }
                        }
                    } else if (!M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
                        M.performPrefixedCommand(
                            M.getCommands(M.getServer(entity)),
                            new CommandSourceStack(
                                CommandSource.NULL,
                                M.position(entity),
                                M.getRotationVector(entity),
                                M.level(entity) instanceof WorldServer ? (WorldServer)M.level(entity) : null,
                                4,
                                M.getString(M.getName(entity)),
                                M.getDisplayName(entity),
                                M.getServer(M.level(entity)),
                                entity
                            ),
                            "/tp @e[type=boh:jeff_the_killer,limit=1,distance=0..50] @p"
                        );
                    }

                    if (!M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
                        M.performPrefixedCommand(
                            M.getCommands(M.getServer(entity)),
                            new CommandSourceStack(
                                CommandSource.NULL,
                                M.position(entity),
                                M.getRotationVector(entity),
                                M.level(entity) instanceof WorldServer ? (WorldServer)M.level(entity) : null,
                                4,
                                M.getString(M.getName(entity)),
                                M.getDisplayName(entity),
                                M.getServer(M.level(entity)),
                                entity
                            ),
                            "/execute at @p run spreadplayers ~ ~ 10 10 false @e[type=boh:jeff_the_killer]"
                        );
                    }
                }
            } else if (Math.random() < 0.64) {
                if (M.isEmpty(M.getEntitiesOfClass(world, RakeEntity.class, AABB.ofSize(new Vec3(x, y, z), 900.0, 900.0, 900.0), e -> true))
                    && (!(world instanceof World) || !M.isDay(world))) {
                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.BLINDNESS, 900, 0, false, false));
                    }

                    if (!M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
                        M.performPrefixedCommand(
                            M.getCommands(M.getServer(entity)),
                            new CommandSourceStack(
                                CommandSource.NULL,
                                M.position(entity),
                                M.getRotationVector(entity),
                                M.level(entity) instanceof WorldServer ? (WorldServer)M.level(entity) : null,
                                4,
                                M.getString(M.getName(entity)),
                                M.getDisplayName(entity),
                                M.getServer(M.level(entity)),
                                entity
                            ),
                            "effect give @s kurolib:sleep 0 45"
                        );
                    }

                    if (M.isEmpty(M.getEntitiesOfClass(world, RakeEntity.class, AABB.ofSize(new Vec3(x, y, z), 50.0, 50.0, 50.0), e -> true))) {
                        if (world instanceof WorldServer _levelx) {
                            Entity entityToSpawn = M.spawn(BohModEntities.RAKE.get(), _levelx, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                            if (entityToSpawn != null) {
                                M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                            }
                        }
                    } else if (!M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
                        M.performPrefixedCommand(
                            M.getCommands(M.getServer(entity)),
                            new CommandSourceStack(
                                CommandSource.NULL,
                                M.position(entity),
                                M.getRotationVector(entity),
                                M.level(entity) instanceof WorldServer ? (WorldServer)M.level(entity) : null,
                                4,
                                M.getString(M.getName(entity)),
                                M.getDisplayName(entity),
                                M.getServer(M.level(entity)),
                                entity
                            ),
                            "/tp @e[type=boh:rake,limit=1,distance=0..50] @p"
                        );
                    }
                }
            } else if (Math.random() < 0.48) {
                if (M.isEmpty(M.getEntitiesOfClass(world, EyelessJackEntity.class, AABB.ofSize(new Vec3(x, y, z), 900.0, 900.0, 900.0), e -> true))
                    && (!(world instanceof World) || !M.isDay(world))) {
                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.BLINDNESS, 900, 0, false, false));
                    }

                    if (!M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
                        M.performPrefixedCommand(
                            M.getCommands(M.getServer(entity)),
                            new CommandSourceStack(
                                CommandSource.NULL,
                                M.position(entity),
                                M.getRotationVector(entity),
                                M.level(entity) instanceof WorldServer ? (WorldServer)M.level(entity) : null,
                                4,
                                M.getString(M.getName(entity)),
                                M.getDisplayName(entity),
                                M.getServer(M.level(entity)),
                                entity
                            ),
                            "effect give @s kurolib:sleep 0 45"
                        );
                    }

                    if (M.isEmpty(M.getEntitiesOfClass(world, EyelessJackEntity.class, AABB.ofSize(new Vec3(x, y, z), 50.0, 50.0, 50.0), e -> true))) {
                        if (world instanceof WorldServer _levelxx) {
                            Entity entityToSpawn = M.spawn(BohModEntities.EYELESS_JACK.get(), _levelxx, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                            if (entityToSpawn != null) {
                                M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                            }
                        }
                    } else if (!M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
                        M.performPrefixedCommand(
                            M.getCommands(M.getServer(entity)),
                            new CommandSourceStack(
                                CommandSource.NULL,
                                M.position(entity),
                                M.getRotationVector(entity),
                                M.level(entity) instanceof WorldServer ? (WorldServer)M.level(entity) : null,
                                4,
                                M.getString(M.getName(entity)),
                                M.getDisplayName(entity),
                                M.getServer(M.level(entity)),
                                entity
                            ),
                            "/tp @e[type=boh:eyeless_jack,limit=1,distance=0..50] @p"
                        );
                    }

                    BohMod.queueServerWork(
                        40,
                        () -> {
                            if (!M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
                                M.performPrefixedCommand(
                                    M.getCommands(M.getServer(entity)),
                                    new CommandSourceStack(
                                        CommandSource.NULL,
                                        M.position(entity),
                                        M.getRotationVector(entity),
                                        M.level(entity) instanceof WorldServer ? (WorldServer)M.level(entity) : null,
                                        4,
                                        M.getString(M.getName(entity)),
                                        M.getDisplayName(entity),
                                        M.getServer(M.level(entity)),
                                        entity
                                    ),
                                    "/spreadplayers ~ ~ 30 30 false @e[type=boh:eyeless_jack,limit=1,sort=nearest]"
                                );
                            }

                            if (!M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
                                M.performPrefixedCommand(
                                    M.getCommands(M.getServer(entity)),
                                    new CommandSourceStack(
                                        CommandSource.NULL,
                                        M.position(entity),
                                        M.getRotationVector(entity),
                                        M.level(entity) instanceof WorldServer ? (WorldServer)M.level(entity) : null,
                                        4,
                                        M.getString(M.getName(entity)),
                                        M.getDisplayName(entity),
                                        M.getServer(M.level(entity)),
                                        entity
                                    ),
                                    "/attribute @p minecraft:generic.max_health base set 16"
                                );
                            }
                        }
                    );
                }
            } else if (Math.random() < 0.32) {
                if ((!(world instanceof World) || !M.isDay(world)) && entity instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player))) {
                    ResourceKey<World> destinationType = Dimensions.dimensionKey(new ResourceLocation("boh:boiler_room_dimension"));
                    if (M.dimension(M.level(_player)) == destinationType) {
                        return;
                    }

                    WorldServer nextLevel = M.getLevel(M.server(_player), destinationType);
                    if (nextLevel != null) {
                        M.connection(_player).send(new ClientboundGameEventPacket(4, 0.0F));
                        M.teleportTo(_player, nextLevel, M.getX(_player), M.getY(_player), M.getZ(_player), M.getYRot(_player), M.getXRot(_player));
                        M.connection(_player).send(new ClientboundPlayerAbilitiesPacket(M.getAbilities(_player)));

                        for (PotionEffect _effectinstance : M.getActiveEffects(_player)) {
                            M.connection(_player).send(new ClientboundUpdateMobEffectPacket(M.getId(_player), _effectinstance));
                        }

                        M.connection(_player).send(new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));
                    }
                }
            } else if (Math.random() < 0.16) {
                if (!M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
                    M.performPrefixedCommand(
                        M.getCommands(M.getServer(entity)),
                        new CommandSourceStack(
                            CommandSource.NULL,
                            M.position(entity),
                            M.getRotationVector(entity),
                            M.level(entity) instanceof WorldServer ? (WorldServer)M.level(entity) : null,
                            4,
                            M.getString(M.getName(entity)),
                            M.getDisplayName(entity),
                            M.getServer(M.level(entity)),
                            entity
                        ),
                        "effect give @s kurolib:sleep 0 45"
                    );
                }

                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.BLINDNESS, 900, 0, false, false));
                }

                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(BohModMobEffects.HYSTM_EFFECT.get(), 900, 0, false, false));
                }
            } else if (Math.random() < 0.3) {
                if (!M.isEmpty(M.getEntitiesOfClass(world, EntityBat.class, AABB.ofSize(new Vec3(x, y, z), 50.0, 50.0, 50.0), e -> true))
                    && world instanceof WorldServer _levelxxx) {
                    Entity entityToSpawn = M.spawn(
                        BohModEntities.RUSSIAN_SLEEP_EXPERIMENT.get(), _levelxxx, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED
                    );
                    if (entityToSpawn != null) {
                        M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                    }
                }
            } else if (Math.random() < 0.3 && world instanceof WorldServer _levelxxxx) {
                Entity entityToSpawn = M.spawn(BohModEntities.LAUGHING_JACK.get(), _levelxxxx, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                if (entityToSpawn != null) {
                    M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                }
            }
        }
    }
}
