package net.mcreator.boh.procedures;

import java.util.Comparator;
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
import net.mcreator.boh.entity.SuicideMouseEntity;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SuicideMouseOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (!M.getBoolean(M.getPersistentData(entity), "nemesis_roar")
                && (entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityLivingBase) {
                M.putBoolean(M.getPersistentData(entity), "nemesis_roar", true);
            }

            if (Math.random() < 0.3 && !M.getBoolean(M.getPersistentData(entity), "switch") && M.getBoolean(M.getPersistentData(entity), "nemesis_roar")) {
                if (entity instanceof SuicideMouseEntity animatable) {
                    animatable.setTexture("suicide_mouse_aggro");
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
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:suicidemouse_jumpscare")),
                            SoundSource.HOSTILE,
                            10.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world,
                            x,
                            y,
                            z,
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:suicidemouse_jumpscare")),
                            SoundSource.HOSTILE,
                            10.0F,
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
                Vec3 _center = new Vec3(x, y, z);

                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(8.0), e -> true)
                    .stream()
                    .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                    .toList()) {
                    if (entityiterator instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(BohModMobEffects.JUMPSCARE_SUICIDE_MOUSE.get(), 60, 0, false, false));
                    }
                }
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
