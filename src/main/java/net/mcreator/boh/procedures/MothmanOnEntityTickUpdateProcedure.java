package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.entity.BohAbstractArrow;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.mc.world.scores.criteria.ObjectiveCriteria;
import net.mcreator.boh.compat.mc.world.scores.criteria.RenderType;
import net.mcreator.boh.entity.MothmanEntity;
import net.mcreator.boh.entity.MothmanbastProjectileEntity;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class MothmanOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null && !M.isClientSide(world)) {
            if (!M.getBoolean(M.getPersistentData(entity), "lines_demo")
                && (entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer) {
                M.putBoolean(M.getPersistentData(entity), "lines_demo", true);
            }

            if (Math.random() < 0.2 && !M.getBoolean(M.getPersistentData(entity), "throlgular") && M.getBoolean(M.getPersistentData(entity), "lines_demo")) {
                if (entity instanceof MothmanEntity) {
                    ((MothmanEntity)entity).setAnimation("trigger_aggro");
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
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mothman_aggro")),
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
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mothman_aggro")),
                            SoundSource.HOSTILE,
                            2.0F,
                            1.0F,
                            false
                        );
                    }
                }

                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 10, 254, false, false));
                }

                M.putBoolean(M.getPersistentData(entity), "throlgular", true);
                BohMod.queueServerWork(65, () -> {
                    if (entity instanceof EntityLivingBase _entityx) {
                        M.removeEffect(_entityx, MobEffects.MOVEMENT_SLOWDOWN);
                    }

                    Scoreboard _scx = M.getScoreboard(M.level(entity));
                    ScoreObjective _sox = M.getObjective(_scx, "anim");
                    if (_sox == null) {
                        _sox = M.addObjective(_scx, "anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
                    }

                    M.setScore(M.getOrCreatePlayerScore(_scx, M.getScoreboardName(entity), _sox), 0);
                });
            }

            if (!((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer)) {
                M.putBoolean(M.getPersistentData(entity), "lines_demo", false);
                M.putBoolean(M.getPersistentData(entity), "throlgular", false);
            }

            if (M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 10.0, 10.0, 10.0), e -> true))
                && Math.random() < 0.1
                && (entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.bell.resonate")),
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
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.bell.resonate")),
                            SoundSource.HOSTILE,
                            1.0F,
                            1.0F,
                            false
                        );
                    }
                }

                World projectileLevel = M.level(entity);
                if (!M.isClientSide(projectileLevel)) {
                    Entity _entityToSpawn = (new Object() {
                        public Entity getArrow(World level, Entity shooter, float damage, int knockback) {
                            BohAbstractArrow entityToSpawn = new MothmanbastProjectileEntity(BohModEntities.MOTHMANBAST_PROJECTILE.get(), level);
                            M.setOwner(entityToSpawn, shooter);
                            M.setBaseDamage(entityToSpawn, damage);
                            M.setKnockback(entityToSpawn, knockback);
                            M.setSilent(entityToSpawn, true);
                            return entityToSpawn;
                        }
                    }).getArrow(projectileLevel, entity, 5.0F, 0);
                    M.setPos(_entityToSpawn, M.getX(entity), M.getEyeY(entity) - 0.1, M.getZ(entity));
                    M.shoot(_entityToSpawn, M.getLookAngle(entity).x, M.getLookAngle(entity).y, M.getLookAngle(entity).z, 3.0F, 0.0F);
                    M.addFreshEntity(projectileLevel, _entityToSpawn);
                }
            }

            if (!M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 15.0, 15.0, 15.0), e -> true))
                && Math.random() < 0.001) {
                Vec3 _center = new Vec3(x, y, z);

                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(5.0), e -> true)
                    .stream()
                    .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                    .toList()) {
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
                            "/particle minecraft:squid_ink ~ ~ ~ 0.5 .5 0.5 0 100"
                        );
                    }

                    if (entityiterator instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.BAD_OMEN, 9999999, 0, false, false));
                    }

                    if (world instanceof World) {
                        if (!M.isClientSide(world)) {
                            M.playSound(
                                world,
                                null,
                                BlockPos.containing(x, y, z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mothman_fly")),
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
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mothman_fly")),
                                SoundSource.HOSTILE,
                                1.0F,
                                1.0F,
                                false
                            );
                        }
                    }

                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
                    }

                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.WEAKNESS, 20, 254, false, false));
                    }

                    if (entity instanceof MothmanEntity) {
                        ((MothmanEntity)entity).setAnimation("fly");
                    }

                    BohMod.queueServerWork(
                        20,
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
                                    "/spreadplayers ~ ~ 30 30 false @e[type=boh:mothman,limit=1,sort=nearest]"
                                );
                            }

                            if (entity instanceof MothmanEntity) {
                                ((MothmanEntity)entity).setAnimation("land");
                            }

                            if (world instanceof World) {
                                if (!M.isClientSide(world)) {
                                    M.playSound(
                                        world,
                                        null,
                                        BlockPos.containing(x, y, z),
                                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mothman_land")),
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
                                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mothman_land")),
                                        SoundSource.HOSTILE,
                                        3.0F,
                                        1.0F,
                                        false
                                    );
                                }
                            }

                            BohMod.queueServerWork(
                                12,
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
                                            "/particle minecraft:squid_ink ~ ~ ~ 1 .1 1 0 100"
                                        );
                                    }
                                }
                            );
                        }
                    );
                }
            }
        }
    }
}
