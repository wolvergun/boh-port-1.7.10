package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.mc.world.scores.criteria.ObjectiveCriteria;
import net.mcreator.boh.compat.mc.world.scores.criteria.RenderType;
import net.mcreator.boh.entity.DemogorgonEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class DemogorgonOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if ((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer
                && M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 15.0, 15.0, 15.0), e -> true))) {
                M.putDouble(M.getPersistentData(entity), "TimerTick", M.getDouble(M.getPersistentData(entity), "TimerTick") + 1.0);
                if (M.getDouble(M.getPersistentData(entity), "TimerTick") >= 20.0) {
                    M.putDouble(M.getPersistentData(entity), "TimerTick", 0.0);
                    if (Math.random() < 0.1) {
                        M.putBoolean(M.getPersistentData(entity), "lunge", true);
                    }
                }
            }

            if (M.getBoolean(M.getPersistentData(entity), "lunge")) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:demogorgon_roar")),
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
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:demogorgon_roar")),
                            SoundSource.HOSTILE,
                            1.0F,
                            1.0F,
                            false
                        );
                    }
                }

                if (entity instanceof DemogorgonEntity) {
                    ((DemogorgonEntity)entity).setAnimation("lunge");
                }

                Scoreboard _sc = M.getScoreboard(M.level(entity));
                ScoreObjective _so = M.getObjective(_sc, "anim");
                if (_so == null) {
                    _so = M.addObjective(_sc, "anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
                }

                M.setScore(M.getOrCreatePlayerScore(_sc, M.getScoreboardName(entity), _so), 1);
                M.putBoolean(M.getPersistentData(entity), "lunge", false);
            }

            if ((new Object() {
                public int getScore(String score, Entity _ent) {
                    Scoreboard _sc = M.getScoreboard(M.level(_ent));
                    ScoreObjective _so = M.getObjective(_sc, score);
                    return _so != null ? M.getScore(M.getOrCreatePlayerScore(_sc, M.getScoreboardName(_ent), _so)) : 0;
                }
            }).getScore("anim", entity) == 1) {
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SPEED, 20, 3, false, false));
                }

                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.DAMAGE_BOOST, 20, 0, false, false));
                }

                BohMod.queueServerWork(20, () -> {
                    if (entity instanceof DemogorgonEntity) {
                        ((DemogorgonEntity)entity).setAnimation("empty");
                    }

                    Scoreboard _scx = M.getScoreboard(M.level(entity));
                    ScoreObjective _sox = M.getObjective(_scx, "anim");
                    if (_sox == null) {
                        _sox = M.addObjective(_scx, "anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
                    }

                    M.setScore(M.getOrCreatePlayerScore(_scx, M.getScoreboardName(entity), _sox), 0);
                });
            }

            if ((new Object() {
                public int getScore(String score, Entity _ent) {
                    Scoreboard _sc = M.getScoreboard(M.level(_ent));
                    ScoreObjective _so = M.getObjective(_sc, score);
                    return _so != null ? M.getScore(M.getOrCreatePlayerScore(_sc, M.getScoreboardName(_ent), _so)) : 0;
                }
            }).getScore("anim", entity) == 2) {
                M.makeStuckInBlock(entity, M.defaultBlockState(Blocks.AIR), new Vec3(0.25, 0.05, 0.25));
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 0, false, false));
                }
            }

            if (!M.getBoolean(M.getPersistentData(entity), "lines_demo")
                && (entity instanceof EntityLiving _mobEntx ? M.getTarget(_mobEntx) : null) instanceof EntityPlayer) {
                M.putBoolean(M.getPersistentData(entity), "lines_demo", true);
            }

            if (Math.random() < 0.2 && !M.getBoolean(M.getPersistentData(entity), "throlgular") && M.getBoolean(M.getPersistentData(entity), "lines_demo")) {
                if (entity instanceof DemogorgonEntity) {
                    ((DemogorgonEntity)entity).setAnimation("roar");
                }

                Scoreboard _sc = M.getScoreboard(M.level(entity));
                ScoreObjective _so = M.getObjective(_sc, "anim");
                if (_so == null) {
                    _so = M.addObjective(_sc, "anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
                }

                M.setScore(M.getOrCreatePlayerScore(_sc, M.getScoreboardName(entity), _so), 2);
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:demogorgon_roar")),
                            SoundSource.HOSTILE,
                            2.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world,
                            x,
                            y,
                            z,
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:demogorgon_roar")),
                            SoundSource.HOSTILE,
                            2.0F,
                            1.0F,
                            false
                        );
                    }
                }

                M.putBoolean(M.getPersistentData(entity), "throlgular", true);
                BohMod.queueServerWork(20, () -> {
                    Scoreboard _scx = M.getScoreboard(M.level(entity));
                    ScoreObjective _sox = M.getObjective(_scx, "anim");
                    if (_sox == null) {
                        _sox = M.addObjective(_scx, "anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
                    }

                    M.setScore(M.getOrCreatePlayerScore(_scx, M.getScoreboardName(entity), _sox), 0);
                });
            }

            if (!((entity instanceof EntityLiving _mobEntx ? M.getTarget(_mobEntx) : null) instanceof EntityPlayer)) {
                M.putBoolean(M.getPersistentData(entity), "lines_demo", false);
                M.putBoolean(M.getPersistentData(entity), "throlgular", false);
            }
        }
    }
}
