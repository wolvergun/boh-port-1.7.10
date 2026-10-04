package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLiving;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.mcreator.boh.compat.mc.world.scores.criteria.ObjectiveCriteria;
import net.mcreator.boh.compat.mc.world.scores.criteria.RenderType;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class GoldHostileOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 50.0, 50.0, 50.0), e -> true))) {
                if (world instanceof WorldServer _level) {
                    Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.GOLD_LOST.get()), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                        M.setYRot(entityToSpawn, M.getYRot(entity));
                        M.setYBodyRot(entityToSpawn, M.getYRot(entity));
                        M.setYHeadRot(entityToSpawn, M.getYRot(entity));
                        M.setXRot(entityToSpawn, M.getXRot(entity));
                        M.setDeltaMovement(entityToSpawn, M.getDeltaMovement(entity).x(), M.getDeltaMovement(entity).y(), M.getDeltaMovement(entity).z());
                    }
                }
                if (!M.isClientSide(M.level(entity))) {
                    M.discard(entity);
                }
            }
            if ((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer && Math.random() < 0.04) {
                Entity _ent = entity;
                Scoreboard _sc = M.getScoreboard(M.level(_ent));
                ScoreObjective _so = M.getObjective(_sc, "anim");
                if (_so == null) {
                    _so = M.addObjective(_sc, "anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
                }
                M.setScore(M.getOrCreatePlayerScore(_sc, M.getScoreboardName(_ent), _so), 1);
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 60, 255, false, false));
                }
            }
            if ((new Object() {

                public int getScore(String score, Entity _ent) {
                    Scoreboard _sc = M.getScoreboard(M.level(_ent));
                    ScoreObjective _so = M.getObjective(_sc, score);
                    return _so != null ? M.getScore(M.getOrCreatePlayerScore(_sc, M.getScoreboardName(_ent), _so)) : 0;
                }
            }).getScore("anim", entity) == 1) {
                M.putDouble(M.getPersistentData(entity), "TimerTick", M.getDouble(M.getPersistentData(entity), "TimerTick") + 1.0);
            }
            if (M.getDouble(M.getPersistentData(entity), "TimerTick") >= 20.0) {
                M.makeStuckInBlock(entity, M.defaultBlockState(Blocks.AIR), new Vec3(0.25, 0.05, 0.25));
                M.putBoolean(M.getPersistentData(entity), "spit", true);
                M.putDouble(M.getPersistentData(entity), "TimerTick", 0.0);
            }
            if (M.getBoolean(M.getPersistentData(entity), "spit")) {
                Entity _ent = entity;
                Scoreboard _sc = M.getScoreboard(M.level(_ent));
                ScoreObjective _so = M.getObjective(_sc, "anim");
                if (_so == null) {
                    _so = M.addObjective(_sc, "anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
                }
                M.setScore(M.getOrCreatePlayerScore(_sc, M.getScoreboardName(_ent), _so), 0);
                Entity _ent_r38 = entity;
                if (!M.isClientSide(M.level(_ent_r38)) && M.getServer(_ent_r38) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent_r38)), new CommandSourceStack(CommandSource.NULL, M.position(_ent_r38), M.getRotationVector(_ent_r38), M.level(_ent_r38) instanceof WorldServer ? (WorldServer) M.level(_ent_r38) : null, 4, M.getString(M.getName(_ent_r38)), M.getDisplayName(_ent_r38), M.getServer(M.level(_ent_r38)), _ent_r38), "/particle minecraft:block redstone_block ~ ~2 ~ 0.2 0 0.2 0 10");
                }
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.llama.spit")), SoundSource.HOSTILE, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.llama.spit")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                    }
                }
                if (Math.random() < 0.1) {
                    M.putBoolean(M.getPersistentData(entity), "spit", false);
                    if (world instanceof WorldServer _level) {
                        Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.UNOWN_1.get()), _level, BlockPos.containing(x, y + 2.3, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                            M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                        }
                    }
                } else if (Math.random() < 0.1) {
                    M.putBoolean(M.getPersistentData(entity), "spit", false);
                    if (world instanceof WorldServer _level) {
                        Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.UNOWN_2.get()), _level, BlockPos.containing(x, y + 2.3, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                            M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                        }
                    }
                } else if (Math.random() < 0.1) {
                    M.putBoolean(M.getPersistentData(entity), "spit", false);
                    if (world instanceof WorldServer _level) {
                        Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.UNOWN_3.get()), _level, BlockPos.containing(x, y + 2.3, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                            M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                        }
                    }
                } else if (Math.random() < 0.1) {
                    M.putBoolean(M.getPersistentData(entity), "spit", false);
                    if (world instanceof WorldServer _level) {
                        Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.UNOWN_4.get()), _level, BlockPos.containing(x, y + 2.3, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                            M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                        }
                    }
                } else if (Math.random() < 0.1) {
                    M.putBoolean(M.getPersistentData(entity), "spit", false);
                    if (world instanceof WorldServer _level) {
                        Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.UNOWN_5.get()), _level, BlockPos.containing(x, y + 2.3, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                            M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                        }
                    }
                } else if (Math.random() < 0.1) {
                    M.putBoolean(M.getPersistentData(entity), "spit", false);
                    if (world instanceof WorldServer _level) {
                        Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.UNOWN_6.get()), _level, BlockPos.containing(x, y + 2.3, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                            M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                        }
                    }
                } else if (Math.random() < 0.1) {
                    M.putBoolean(M.getPersistentData(entity), "spit", false);
                    if (world instanceof WorldServer _level) {
                        Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.UNOWN_7.get()), _level, BlockPos.containing(x, y + 2.3, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                            M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                        }
                    }
                } else if (Math.random() < 0.1) {
                    M.putBoolean(M.getPersistentData(entity), "spit", false);
                    if (world instanceof WorldServer _level) {
                        Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.UNOWN_8.get()), _level, BlockPos.containing(x, y + 2.3, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                            M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                        }
                    }
                } else if (Math.random() < 0.1) {
                    M.putBoolean(M.getPersistentData(entity), "spit", false);
                    if (world instanceof WorldServer _level) {
                        Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.UNOWN_9.get()), _level, BlockPos.containing(x, y + 2.3, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                            M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                        }
                    }
                } else if (Math.random() < 0.1) {
                    M.putBoolean(M.getPersistentData(entity), "spit", false);
                    if (world instanceof WorldServer _level) {
                        Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.UNOWN_10.get()), _level, BlockPos.containing(x, y + 2.3, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                            M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                        }
                    }
                } else if (Math.random() < 0.1) {
                    M.putBoolean(M.getPersistentData(entity), "spit", false);
                    if (world instanceof WorldServer _level) {
                        Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.UNOWN_11.get()), _level, BlockPos.containing(x, y + 2.3, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                            M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                        }
                    }
                } else if (Math.random() < 0.1) {
                    M.putBoolean(M.getPersistentData(entity), "spit", false);
                    if (world instanceof WorldServer _level) {
                        Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.UNOWN_12.get()), _level, BlockPos.containing(x, y + 2.3, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                            M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                        }
                    }
                } else if (Math.random() < 0.1) {
                    M.putBoolean(M.getPersistentData(entity), "spit", false);
                    if (world instanceof WorldServer _level) {
                        Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.UNOWN_13.get()), _level, BlockPos.containing(x, y + 2.3, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                            M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                        }
                    }
                } else if (Math.random() < 0.1) {
                    M.putBoolean(M.getPersistentData(entity), "spit", false);
                    if (world instanceof WorldServer _level) {
                        Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.UNOWN_14.get()), _level, BlockPos.containing(x, y + 2.3, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                            M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                        }
                    }
                } else {
                    M.putBoolean(M.getPersistentData(entity), "spit", false);
                    if (world instanceof WorldServer _level) {
                        Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.UNOWN_15.get()), _level, BlockPos.containing(x, y + 2.3, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                            M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                        }
                    }
                }
            }
        }
    }
}
