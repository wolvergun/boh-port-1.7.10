package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.EyelessJackEntity;
import net.mcreator.boh.entity.JeffTheKillerEntity;
import net.mcreator.boh.entity.RakeEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.init.BohModMobEffects;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundGameEventPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundLevelEventPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.minecraft.entity.EntityLivingBase;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class SleepEventProcedure {

    @SubscribeEvent
    public void onPlayerInBed(PlayerSleepInBedEvent event) {
        execute(event, M.level(M.getEntity(event)), M.getX(M.getPos(event)), M.getY(M.getPos(event)), M.getZ(M.getPos(event)), M.getEntity(event));
    }

    public static void execute(World world, double x, double y, double z, Entity entity) {
        execute(null, world, x, y, z, entity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (!M.isClientSide(world) && !(entity instanceof EntityLivingBase _livEnt1 && M.hasEffect(_livEnt1, (Potion) BohModMobEffects.SAFE_AND_SOUND.get())) && Math.random() < 0.1 && Math.random() < 0.1) {
                if (Math.random() < 0.8) {
                    if (M.isEmpty(M.getEntitiesOfClass(world, JeffTheKillerEntity.class, AABB.ofSize(new Vec3(x, y, z), 900.0, 900.0, 900.0), e -> true)) && !(world instanceof World _lvl3 && M.isDay(_lvl3))) {
                        if (entity instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                            M.displayClientMessage(_player, Component.literal("§l§4 GO TO SLEEP"), true);
                        }
                        if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                            M.addEffect(_entity, M.new_PotionEffect(MobEffects.BLINDNESS, 120, 0, false, false));
                        }
                        if (world instanceof World _level) {
                            if (!M.isClientSide(_level)) {
                                M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:jeff_sleep")), SoundSource.HOSTILE, 1.0F, 1.0F);
                            } else {
                                M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:jeff_sleep")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                            }
                        }
                        if (M.isEmpty(M.getEntitiesOfClass(world, JeffTheKillerEntity.class, AABB.ofSize(new Vec3(x, y, z), 50.0, 50.0, 50.0), e -> true))) {
                            if (world instanceof WorldServer _level) {
                                Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.JEFF_THE_KILLER.get()), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                                if (entityToSpawn != null) {
                                    M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                                }
                            }
                        } else {
                            Entity _ent = entity;
                            if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                                M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/tp @e[type=boh:jeff_the_killer,limit=1,distance=0..50] @p");
                            }
                        }
                        Entity _ent = entity;
                        if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                            M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/execute at @p run spreadplayers ~ ~ 10 10 false @e[type=boh:jeff_the_killer]");
                        }
                    }
                } else if (Math.random() < 0.64) {
                    if (M.isEmpty(M.getEntitiesOfClass(world, RakeEntity.class, AABB.ofSize(new Vec3(x, y, z), 900.0, 900.0, 900.0), e -> true)) && !(world instanceof World _lvl12 && M.isDay(_lvl12))) {
                        if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                            M.addEffect(_entity, M.new_PotionEffect(MobEffects.BLINDNESS, 900, 0, false, false));
                        }
                        Entity _ent = entity;
                        if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                            M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "effect give @s kurolib:sleep 0 45");
                        }
                        if (M.isEmpty(M.getEntitiesOfClass(world, RakeEntity.class, AABB.ofSize(new Vec3(x, y, z), 50.0, 50.0, 50.0), e -> true))) {
                            if (world instanceof WorldServer _level) {
                                Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.RAKE.get()), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                                if (entityToSpawn != null) {
                                    M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                                }
                            }
                        } else {
                            _ent = entity;
                            if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                                M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/tp @e[type=boh:rake,limit=1,distance=0..50] @p");
                            }
                        }
                    }
                } else if (Math.random() < 0.48) {
                    if (M.isEmpty(M.getEntitiesOfClass(world, EyelessJackEntity.class, AABB.ofSize(new Vec3(x, y, z), 900.0, 900.0, 900.0), e -> true)) && !(world instanceof World _lvl19 && M.isDay(_lvl19))) {
                        if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                            M.addEffect(_entity, M.new_PotionEffect(MobEffects.BLINDNESS, 900, 0, false, false));
                        }
                        Entity _ent = entity;
                        if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                            M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "effect give @s kurolib:sleep 0 45");
                        }
                        if (M.isEmpty(M.getEntitiesOfClass(world, EyelessJackEntity.class, AABB.ofSize(new Vec3(x, y, z), 50.0, 50.0, 50.0), e -> true))) {
                            if (world instanceof WorldServer _level) {
                                Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.EYELESS_JACK.get()), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                                if (entityToSpawn != null) {
                                    M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                                }
                            }
                        } else {
                            _ent = entity;
                            if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                                M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/tp @e[type=boh:eyeless_jack,limit=1,distance=0..50] @p");
                            }
                        }
                        BohMod.queueServerWork(40, () -> {
                            Entity _entx = entity;
                            if (!M.isClientSide(M.level(_entx)) && M.getServer(_entx) != null) {
                                M.performPrefixedCommand(M.getCommands(M.getServer(_entx)), new CommandSourceStack(CommandSource.NULL, M.position(_entx), M.getRotationVector(_entx), M.level(_entx) instanceof WorldServer ? (WorldServer) M.level(_entx) : null, 4, M.getString(M.getName(_entx)), M.getDisplayName(_entx), M.getServer(M.level(_entx)), _entx), "/spreadplayers ~ ~ 30 30 false @e[type=boh:eyeless_jack,limit=1,sort=nearest]");
                            }
                            Entity _entx_r56 = entity;
                            if (!M.isClientSide(M.level(_entx_r56)) && M.getServer(_entx_r56) != null) {
                                M.performPrefixedCommand(M.getCommands(M.getServer(_entx_r56)), new CommandSourceStack(CommandSource.NULL, M.position(_entx_r56), M.getRotationVector(_entx_r56), M.level(_entx_r56) instanceof WorldServer ? (WorldServer) M.level(_entx_r56) : null, 4, M.getString(M.getName(_entx_r56)), M.getDisplayName(_entx_r56), M.getServer(M.level(_entx_r56)), _entx_r56), "/attribute @p minecraft:generic.max_health base set 16");
                            }
                        });
                    }
                } else if (Math.random() < 0.32) {
                    if (!(world instanceof World _lvl28 && M.isDay(_lvl28)) && entity instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player))) {
                        ResourceKey<World> destinationType = net.mcreator.boh.compat.world.Dimensions.dimensionKey( new ResourceLocation("boh:boiler_room_dimension"));
                        if (M.dimension(M.level(_player)) == destinationType) {
                            return;
                        }
                        WorldServer nextLevel = M.getLevel(M.server(_player), destinationType);
                        if (nextLevel != null) {
                            M.connection(_player).send(new ClientboundGameEventPacket(ClientboundGameEventPacket.WIN_GAME, 0.0F));
                            M.teleportTo(_player, nextLevel, M.getX(_player), M.getY(_player), M.getZ(_player), M.getYRot(_player), M.getXRot(_player));
                            M.connection(_player).send(new ClientboundPlayerAbilitiesPacket(M.getAbilities(_player)));
                            for (PotionEffect _effectinstance : M.getActiveEffects(_player)) {
                                M.connection(_player).send(new ClientboundUpdateMobEffectPacket(M.getId(_player), _effectinstance));
                            }
                            M.connection(_player).send(new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));
                        }
                    }
                } else if (Math.random() < 0.16) {
                    Entity _ent = entity;
                    if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                        M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "effect give @s kurolib:sleep 0 45");
                    }
                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.BLINDNESS, 900, 0, false, false));
                    }
                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect((Potion) BohModMobEffects.HYSTM_EFFECT.get(), 900, 0, false, false));
                    }
                } else if (Math.random() < 0.3) {
                    if (!M.isEmpty(M.getEntitiesOfClass(world, EntityBat.class, AABB.ofSize(new Vec3(x, y, z), 50.0, 50.0, 50.0), e -> true)) && world instanceof WorldServer _level) {
                        Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.RUSSIAN_SLEEP_EXPERIMENT.get()), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                            M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                        }
                    }
                } else if (Math.random() < 0.3 && world instanceof WorldServer _level) {
                    Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.LAUGHING_JACK.get()), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                        M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                    }
                }
            }
        }
    }
}
