package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.mc.world.scores.criteria.ObjectiveCriteria;
import net.mcreator.boh.compat.mc.world.scores.criteria.RenderType;
import net.mcreator.boh.entity.KrasueEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class KrasueOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof EntityLiving _mob && M.isAggressive(_mob)) {
                M.setDeltaMovement(entity, new Vec3(M.getLookAngle(entity).x * 0.5, M.getLookAngle(entity).y * 0.9, M.getLookAngle(entity).z * 0.5));
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
                        "/execute as @e[type=boh:krasue,distance=0..1] at @s facing entity @e[type=minecraft:player,tag=krasue,sort=nearest,limit=1] feet run tp @e[type=boh:krasue] ^ ^ ^0.1 ~ ~"
                    );
                }
            }

            M.setDeltaMovement(entity, new Vec3(M.getLookAngle(entity).x * 0.2, M.getLookAngle(entity).y * 0.9, M.getLookAngle(entity).z * 0.2));
            if (!M.getBoolean(M.getPersistentData(entity), "nemesis_roar")
                && (entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityLivingBase) {
                M.putBoolean(M.getPersistentData(entity), "nemesis_roar", true);
            }

            if (Math.random() < 0.25 && !M.getBoolean(M.getPersistentData(entity), "switch") && M.getBoolean(M.getPersistentData(entity), "nemesis_roar")) {
                if (entity instanceof KrasueEntity) {
                    ((KrasueEntity)entity).setAnimation("scream");
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
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:krasue_scream")),
                            SoundSource.HOSTILE,
                            3.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world,
                            x,
                            y,
                            z,
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:krasue_scream")),
                            SoundSource.HOSTILE,
                            3.0F,
                            1.0F,
                            false
                        );
                    }
                }

                M.putBoolean(M.getPersistentData(entity), "switch", true);
                BohMod.queueServerWork(70, () -> {
                    Scoreboard _scx = M.getScoreboard(M.level(entity));
                    ScoreObjective _sox = M.getObjective(_scx, "anim");
                    if (_sox == null) {
                        _sox = M.addObjective(_scx, "anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
                    }

                    M.setScore(M.getOrCreatePlayerScore(_scx, M.getScoreboardName(entity), _sox), 0);
                });
            }

            if (!((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityLivingBase)) {
                M.putBoolean(M.getPersistentData(entity), "nemesis_roar", false);
                M.putBoolean(M.getPersistentData(entity), "switch", false);
            }

            if ((new Object() {
                public int getScore(String score, Entity _ent) {
                    Scoreboard _scx = M.getScoreboard(M.level(_ent));
                    ScoreObjective _sox = M.getObjective(_scx, score);
                    return _sox != null ? M.getScore(M.getOrCreatePlayerScore(_scx, M.getScoreboardName(_ent), _sox)) : 0;
                }
            }).getScore("anim", entity) == 2) {
                M.makeStuckInBlock(entity, M.defaultBlockState(Blocks.AIR), new Vec3(0.25, 0.05, 0.25));
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 10, 255, false, false));
                }
            }

            if ((new Object() {
                public int getScore(String score, Entity _ent) {
                    Scoreboard _sc = M.getScoreboard(M.level(_ent));
                    ScoreObjective _so = M.getObjective(_sc, score);
                    return _so != null ? M.getScore(M.getOrCreatePlayerScore(_sc, M.getScoreboardName(_ent), _so)) : 0;
                }
            }).getScore("anim", entity) == 3) {
                M.makeStuckInBlock(entity, M.defaultBlockState(Blocks.AIR), new Vec3(0.25, 0.05, 0.25));
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 60, 255, false, false));
                }
            }
        }
    }
}
