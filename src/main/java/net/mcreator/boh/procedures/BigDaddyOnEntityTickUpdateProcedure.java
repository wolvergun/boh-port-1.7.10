package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.mc.world.scores.criteria.ObjectiveCriteria;
import net.mcreator.boh.compat.mc.world.scores.criteria.RenderType;
import net.mcreator.boh.entity.BigDaddyEntity;
import net.mcreator.boh.entity.LittleSisterEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BigDaddyOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (!M.isClientSide(world)) {
                if ((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer
                    && M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 8.0, 8.0, 8.0), e -> true))) {
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
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:big_daddy_melee")),
                                SoundSource.HOSTILE,
                                1.0F,
                                0.1F
                            );
                        } else {
                            M.playLocalSound(
                                world,
                                x,
                                y,
                                z,
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:big_daddy_melee")),
                                SoundSource.HOSTILE,
                                1.0F,
                                0.1F,
                                false
                            );
                        }
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
                    M.levelEvent(world, 2001, BlockPos.containing(x, y, z), M.blockStateId(M.getBlockState(world, BlockPos.containing(x, y - 1.0, z))));
                    if (entity instanceof BigDaddyEntity) {
                        ((BigDaddyEntity)entity).setAnimation("charge");
                    }

                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SPEED, 20, 5, false, false));
                    }

                    BohMod.queueServerWork(26, () -> {
                        if (entity instanceof BigDaddyEntity) {
                            ((BigDaddyEntity)entity).setAnimation("empty");
                        }

                        Scoreboard _sc = M.getScoreboard(M.level(entity));
                        ScoreObjective _so = M.getObjective(_sc, "anim");
                        if (_so == null) {
                            _so = M.addObjective(_sc, "anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
                        }

                        M.setScore(M.getOrCreatePlayerScore(_sc, M.getScoreboardName(entity), _so), 0);
                    });
                }

                if ((entity instanceof EntityLiving _mobEntx ? M.getTarget(_mobEntx) : null) instanceof EntityPlayer
                    && !M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 8.0, 8.0, 8.0), e -> true))
                    && Math.random() < 0.01) {
                    M.putBoolean(M.getPersistentData(entity), "aoe", true);
                }

                if (M.getBoolean(M.getPersistentData(entity), "aoe")) {
                    M.levelEvent(
                        world,
                        2001,
                        BlockPos.containing(Mth.nextInt(RandomSource.create(), -8, 8) + x, y, Mth.nextInt(RandomSource.create(), -8, 8) + z),
                        M.blockStateId(
                            M.getBlockState(
                                world,
                                BlockPos.containing(Mth.nextInt(RandomSource.create(), -8, 8) + x, y - 1.0, Mth.nextInt(RandomSource.create(), -8, 8) + z)
                            )
                        )
                    );
                    M.levelEvent(
                        world,
                        2001,
                        BlockPos.containing(Mth.nextInt(RandomSource.create(), -8, 8) + x, y, Mth.nextInt(RandomSource.create(), -8, 8) + z),
                        M.blockStateId(
                            M.getBlockState(
                                world,
                                BlockPos.containing(Mth.nextInt(RandomSource.create(), -8, 8) + x, y - 1.0, Mth.nextInt(RandomSource.create(), -8, 8) + z)
                            )
                        )
                    );
                    if (world instanceof World) {
                        if (!M.isClientSide(world)) {
                            M.playSound(
                                world,
                                null,
                                BlockPos.containing(x, y, z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:big_daddy_melee")),
                                SoundSource.HOSTILE,
                                1.0F,
                                0.1F
                            );
                        } else {
                            M.playLocalSound(
                                world,
                                x,
                                y,
                                z,
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:big_daddy_melee")),
                                SoundSource.HOSTILE,
                                1.0F,
                                0.1F,
                                false
                            );
                        }
                    }

                    Scoreboard _sc = M.getScoreboard(M.level(entity));
                    ScoreObjective _so = M.getObjective(_sc, "anim");
                    if (_so == null) {
                        _so = M.addObjective(_sc, "anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
                    }

                    M.setScore(M.getOrCreatePlayerScore(_sc, M.getScoreboardName(entity), _so), 2);
                    BohMod.queueServerWork(20, () -> M.putBoolean(M.getPersistentData(entity), "aoe", false));
                }

                if ((new Object() {
                    public int getScore(String score, Entity _ent) {
                        Scoreboard _sc = M.getScoreboard(M.level(_ent));
                        ScoreObjective _so = M.getObjective(_sc, score);
                        return _so != null ? M.getScore(M.getOrCreatePlayerScore(_sc, M.getScoreboardName(_ent), _so)) : 0;
                    }
                }).getScore("anim", entity) == 2) {
                    if (entity instanceof BigDaddyEntity) {
                        ((BigDaddyEntity)entity).setAnimation("shake");
                    }

                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 40, 255, false, false));
                    }

                    Vec3 _center = new Vec3(x, y, z);

                    for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(4.0), e -> true)
                        .stream()
                        .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                        .toList()) {
                        if (entityiterator instanceof EntityPlayer && entityiterator instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                            M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 140, 6, false, false));
                        }
                    }

                    BohMod.queueServerWork(25, () -> {
                        Scoreboard _sc = M.getScoreboard(M.level(entity));
                        ScoreObjective _so = M.getObjective(_sc, "anim");
                        if (_so == null) {
                            _so = M.addObjective(_sc, "anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
                        }

                        M.setScore(M.getOrCreatePlayerScore(_sc, M.getScoreboardName(entity), _so), 0);
                    });
                }

                if (M.isInWater(entity)) {
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
                            "/effect give @e[type=boh:big_daddy,limit=1,sort=nearest] minecraft:dolphins_grace infinite 3 true"
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
                            "/effect give @e[type=boh:big_daddy,limit=1,sort=nearest] minecraft:conduit_power infinite 1 true"
                        );
                    }
                }

                if ((entity instanceof EntityLiving _mobEntx ? M.getTarget(_mobEntx) : null) instanceof EntityPlayer
                    && entity instanceof BigDaddyEntity animatable) {
                    animatable.setTexture("big_daddy_aggro");
                }

                if (!((entity instanceof EntityLiving _mobEntx ? M.getTarget(_mobEntx) : null) instanceof EntityPlayer)
                    && entity instanceof BigDaddyEntity animatable) {
                    animatable.setTexture("big_daddy");
                }
            }

            if (M.isEmpty(M.getEntitiesOfClass(world, LittleSisterEntity.class, AABB.ofSize(new Vec3(x, y, z), 5.0, 5.0, 5.0), e -> true))) {
                Vec3 _center = new Vec3(x, y, z);

                for (Entity entityiteratorx : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(50.0), e -> true)
                    .stream()
                    .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                    .toList()) {
                    if (entityiteratorx instanceof LittleSisterEntity
                        && !(entityiteratorx instanceof EntityLivingBase _livEnt61 && M.hasEffect(_livEnt61, MobEffects.CONFUSION))
                        && entity instanceof EntityLiving _entity) {
                        M.moveTo(M.getNavigation(_entity), M.getX(entityiteratorx), M.getY(entityiteratorx), M.getZ(entityiteratorx), 1.1);
                    }
                }
            }

            if (!M.isEmpty(M.getEntitiesOfClass(world, LittleSisterEntity.class, AABB.ofSize(new Vec3(x, y, z), 5.0, 5.0, 5.0), e -> true))
                && !M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 8.0, 8.0, 8.0), e -> true))
                && Math.random() < 0.005) {
                if (entity instanceof BigDaddyEntity) {
                    ((BigDaddyEntity)entity).setAnimation("threaten");
                }

                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 60, 254, false, false));
                }

                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:big_daddy_threaten")),
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
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:big_daddy_threaten")),
                            SoundSource.HOSTILE,
                            1.0F,
                            1.0F,
                            false
                        );
                    }
                }
            }

            if (!((entity instanceof EntityLiving _mobEntx ? M.getTarget(_mobEntx) : null) instanceof EntityPlayer)) {
                Vec3 _center = new Vec3(x, y, z);

                for (Entity entityiteratorxx : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(10.0), e -> true)
                    .stream()
                    .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                    .toList()) {
                    if (entityiteratorxx instanceof LittleSisterEntity
                        && entityiteratorxx instanceof EntityLivingBase _livEnt75
                        && M.hasEffect(_livEnt75, MobEffects.CONFUSION)
                        && entity instanceof EntityLivingBase _entity) {
                        M.removeEffect(_entity, MobEffects.CONFUSION);
                    }
                }
            }
        }
    }
}
