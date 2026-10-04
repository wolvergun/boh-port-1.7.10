package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec2;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.mc.world.scores.criteria.ObjectiveCriteria;
import net.mcreator.boh.compat.mc.world.scores.criteria.RenderType;
import net.mcreator.boh.entity.MXEntity;
import net.mcreator.boh.network.BohModVariables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class MXOnEntityTickUpdateProcedure {
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
                    if (entity instanceof MXEntity) {
                        ((MXEntity)entity).setAnimation("charge");
                    }

                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SPEED, 20, 5, false, false));
                    }

                    BohMod.queueServerWork(26, () -> {
                        if (entity instanceof MXEntity) {
                            ((MXEntity)entity).setAnimation("empty");
                        }

                        Scoreboard _sc = M.getScoreboard(M.level(entity));
                        ScoreObjective _sox = M.getObjective(_sc, "anim");
                        if (_sox == null) {
                            _sox = M.addObjective(_sc, "anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
                        }

                        M.setScore(M.getOrCreatePlayerScore(_sc, M.getScoreboardName(entity), _sox), 0);
                    });
                }

                if ((entity instanceof EntityLiving _mobEntx ? M.getTarget(_mobEntx) : null) instanceof EntityPlayer
                    && !M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 8.0, 8.0, 8.0), e -> true))
                    && Math.random() < 0.01) {
                    M.putBoolean(M.getPersistentData(entity), "aoe", true);
                }
            }

            if ((entity instanceof EntityLiving _mobEntx ? M.getTarget(_mobEntx) : null) instanceof EntityPlayer
                && !M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 4.0, 4.0, 4.0), e -> true))
                && Math.random() < 0.03) {
                if (entity instanceof MXEntity) {
                    ((MXEntity)entity).setAnimation("stomp");
                }

                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 255, false, false));
                }

                BohMod.queueServerWork(
                    25,
                    () -> {
                        for (int index0 = 0; index0 < 10; index0++) {
                            if (world instanceof World) {
                                if (!M.isClientSide(world)) {
                                    M.playSound(
                                        world,
                                        null,
                                        BlockPos.containing(x, y, z),
                                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.explode")),
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
                                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.explode")),
                                        SoundSource.HOSTILE,
                                        1.0F,
                                        1.0F,
                                        false
                                    );
                                }
                            }

                            M.levelEvent(
                                world,
                                2001,
                                BlockPos.containing(Mth.nextInt(RandomSource.create(), -3, 3) + x, y, Mth.nextInt(RandomSource.create(), -3, 3) + z),
                                M.blockStateId(M.getBlockState(world, BlockPos.containing(x, y - 1.0, z)))
                            );
                        }
                    }
                );
                Vec3 _center = new Vec3(x, y, z);

                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(2.0), e -> true)
                    .stream()
                    .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                    .toList()) {
                    BohMod.queueServerWork(
                        25,
                        () -> {
                            if (entityiterator instanceof EntityPlayer) {
                                if (!M.isClientSide(M.level(entityiterator)) && M.getServer(entityiterator) != null) {
                                    M.performPrefixedCommand(
                                        M.getCommands(M.getServer(entityiterator)),
                                        new CommandSourceStack(
                                            CommandSource.NULL,
                                            M.position(entityiterator),
                                            M.getRotationVector(entityiterator),
                                            M.level(entityiterator) instanceof WorldServer ? (WorldServer)M.level(entityiterator) : null,
                                            4,
                                            M.getString(M.getName(entityiterator)),
                                            M.getDisplayName(entityiterator),
                                            M.getServer(M.level(entityiterator)),
                                            entityiterator
                                        ),
                                        "/effect give @s minecraft:levitation 1 6 true"
                                    );
                                }

                                M.hurt(
                                    entityiterator,
                                    M.new_DamageSource(
                                        M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.GENERIC)
                                    ),
                                    7.0F
                                );
                            }
                        }
                    );
                }
            }

            if ((entity instanceof EntityLiving _mobEntx ? M.getTarget(_mobEntx) : null) instanceof EntityPlayer) {
                Vec3 _center = new Vec3(x, y, z);

                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(5.0), e -> true)
                    .stream()
                    .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                    .toList()) {
                    if (entityiterator instanceof EntityPlayer) {
                        boolean _setval = true;
                        M.getCapability(entityiterator, BohModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                            capability.chase_mx = _setval;
                            capability.syncPlayerVariables(entityiterator);
                        });
                    }
                }
            }

            if (!((entity instanceof EntityLiving _mobEntx ? M.getTarget(_mobEntx) : null) instanceof EntityPlayer) && world instanceof WorldServer _level) {
                M.performPrefixedCommand(
                    M.getCommands(M.getServer(_level)),
                    M.withSuppressedOutput(
                        new CommandSourceStack(
                            CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), M.getServer(_level), null
                        )
                    ),
                    "/stopsound @a music boh:chase_mx"
                );
            }
        }
    }
}
