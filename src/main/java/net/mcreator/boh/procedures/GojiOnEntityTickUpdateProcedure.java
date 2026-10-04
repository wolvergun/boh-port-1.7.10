package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.entity.BohAbstractArrow;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.commands.arguments.Anchor;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.mc.world.scores.criteria.ObjectiveCriteria;
import net.mcreator.boh.compat.mc.world.scores.criteria.RenderType;
import net.mcreator.boh.entity.BloodSpillEntity;
import net.mcreator.boh.entity.GojiEntity;
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

public class GojiOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if ((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer
                && !M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 4.0, 4.0, 4.0), e -> true))
                && Math.random() < 0.03) {
                if (entity instanceof GojiEntity) {
                    ((GojiEntity)entity).setAnimation("stomp");
                }

                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 255, false, false));
                }

                BohMod.queueServerWork(
                    14,
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
                        14,
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

            if (!M.getBoolean(M.getPersistentData(entity), "rexy_roar")
                && (entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer) {
                M.putBoolean(M.getPersistentData(entity), "rexy_roar", true);
            }

            if (Math.random() < 0.25 && !M.getBoolean(M.getPersistentData(entity), "twitch") && M.getBoolean(M.getPersistentData(entity), "rexy_roar")) {
                if (entity instanceof GojiEntity) {
                    ((GojiEntity)entity).setAnimation("roar");
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
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:goji_roar")),
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
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:goji_roar")),
                            SoundSource.HOSTILE,
                            3.0F,
                            1.0F,
                            false
                        );
                    }
                }

                M.putBoolean(M.getPersistentData(entity), "twitch", true);
                BohMod.queueServerWork(68, () -> {
                    Scoreboard _scx = M.getScoreboard(M.level(entity));
                    ScoreObjective _sox = M.getObjective(_scx, "anim");
                    if (_sox == null) {
                        _sox = M.addObjective(_scx, "anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
                    }

                    M.setScore(M.getOrCreatePlayerScore(_scx, M.getScoreboardName(entity), _sox), 0);
                });
            }

            if (!((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer)) {
                M.putBoolean(M.getPersistentData(entity), "rexy_roar", false);
                M.putBoolean(M.getPersistentData(entity), "twitch", false);
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

            if (M.getBoolean(M.getPersistentData(entity), "shoot_goji")) {
                M.makeStuckInBlock(entity, M.defaultBlockState(Blocks.AIR), new Vec3(0.25, 0.05, 0.25));
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 10, 255, false, false));
                }
            }

            if ((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer
                && Math.random() < 0.005
                && !M.getBoolean(M.getPersistentData(entity), "shoot_goji")) {
                M.putBoolean(M.getPersistentData(entity), "shoot_goji", true);
                if (entity instanceof GojiEntity) {
                    ((GojiEntity)entity).setAnimation("spit");
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
                        "/playsound boh:goji_buildup hostile @a ~ ~ ~ 2 1"
                    );
                }

                Vec3 _center = new Vec3(x, y, z);

                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(50.0), e -> true)
                    .stream()
                    .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                    .toList()) {
                    if (entityiterator instanceof EntityPlayer) {
                        M.lookAt(entity, Anchor.EYES, new Vec3(M.getX(entityiterator), M.getY(entityiterator), M.getZ(entityiterator)));
                    }
                }

                BohMod.queueServerWork(
                    10,
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
                                "/playsound boh:goji_breath hostile @a ~ ~ ~ 0.4 1"
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
                                "/playsound minecraft:entity.llama.spit hostile @a ~ ~ ~ 0.4 0.2"
                            );
                        }

                        World projectileLevel = M.level(entity);
                        if (!M.isClientSide(projectileLevel)) {
                            Entity _entityToSpawn = (new Object() {
                                public Entity getArrow(World level, Entity shooter, float damage, int knockback) {
                                    BohAbstractArrow entityToSpawn = new BloodSpillEntity(BohModEntities.BLOOD_SPILL.get(), level);
                                    M.setOwner(entityToSpawn, shooter);
                                    M.setBaseDamage(entityToSpawn, damage);
                                    M.setKnockback(entityToSpawn, knockback);
                                    M.setSilent(entityToSpawn, true);
                                    return entityToSpawn;
                                }
                            }).getArrow(projectileLevel, entity, 5.0F, 0);
                            M.setPos(_entityToSpawn, M.getX(entity), M.getEyeY(entity) - 0.1, M.getZ(entity));
                            M.shoot(_entityToSpawn, M.getLookAngle(entity).x, M.getLookAngle(entity).y, M.getLookAngle(entity).z, 0.9F, 0.0F);
                            M.addFreshEntity(projectileLevel, _entityToSpawn);
                        }

                        BohMod.queueServerWork(
                            10,
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
                                        "/playsound minecraft:entity.llama.spit hostile @a ~ ~ ~ 0.4 0.2"
                                    );
                                }

                                World projectileLevelx = M.level(entity);
                                if (!M.isClientSide(projectileLevelx)) {
                                    Entity _entityToSpawnx = (new Object() {
                                        public Entity getArrow(World level, Entity shooter, float damage, int knockback) {
                                            BohAbstractArrow entityToSpawn = new BloodSpillEntity(BohModEntities.BLOOD_SPILL.get(), level);
                                            M.setOwner(entityToSpawn, shooter);
                                            M.setBaseDamage(entityToSpawn, damage);
                                            M.setKnockback(entityToSpawn, knockback);
                                            M.setSilent(entityToSpawn, true);
                                            return entityToSpawn;
                                        }
                                    }).getArrow(projectileLevelx, entity, 5.0F, 0);
                                    M.setPos(_entityToSpawnx, M.getX(entity), M.getEyeY(entity) - 0.1, M.getZ(entity));
                                    M.shoot(_entityToSpawnx, M.getLookAngle(entity).x, M.getLookAngle(entity).y, M.getLookAngle(entity).z, 0.9F, 0.0F);
                                    M.addFreshEntity(projectileLevelx, _entityToSpawnx);
                                }

                                BohMod.queueServerWork(
                                    10,
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
                                                "/playsound minecraft:entity.llama.spit hostile @a ~ ~ ~ 0.4 0.2"
                                            );
                                        }

                                        World projectileLevelxx = M.level(entity);
                                        if (!M.isClientSide(projectileLevelxx)) {
                                            Entity _entityToSpawnxx = (new Object() {
                                                public Entity getArrow(World level, Entity shooter, float damage, int knockback) {
                                                    BohAbstractArrow entityToSpawn = new BloodSpillEntity(BohModEntities.BLOOD_SPILL.get(), level);
                                                    M.setOwner(entityToSpawn, shooter);
                                                    M.setBaseDamage(entityToSpawn, damage);
                                                    M.setKnockback(entityToSpawn, knockback);
                                                    M.setSilent(entityToSpawn, true);
                                                    return entityToSpawn;
                                                }
                                            }).getArrow(projectileLevelxx, entity, 5.0F, 0);
                                            M.setPos(_entityToSpawnxx, M.getX(entity), M.getEyeY(entity) - 0.1, M.getZ(entity));
                                            M.shoot(_entityToSpawnxx, M.getLookAngle(entity).x, M.getLookAngle(entity).y, M.getLookAngle(entity).z, 0.9F, 0.0F);
                                            M.addFreshEntity(projectileLevelxx, _entityToSpawnxx);
                                        }

                                        M.putBoolean(M.getPersistentData(entity), "shoot_goji", false);
                                    }
                                );
                            }
                        );
                    }
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
                    "/fill ~-1 ~ ~-1 ~1 ~4 ~1 air replace #minecraft:leaves"
                );
            }

            if (!((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer)) {
                M.putBoolean(M.getPersistentData(entity), "rexy_roar", false);
                M.putBoolean(M.getPersistentData(entity), "twitch", false);
            }

            if (M.getDeltaMovement(entity).horizontalDistanceSqr() > 1.0E-6) {
                M.putDouble(M.getPersistentData(entity), "timer_step", M.getDouble(M.getPersistentData(entity), "timer_step") + 1.0);
            } else {
                M.putDouble(M.getPersistentData(entity), "timer_step", 0.0);
            }

            if (entity instanceof EntityLiving _mob && M.isAggressive(_mob)) {
                if (entity instanceof EntityLiving _mobx && M.isAggressive(_mobx) && M.getDouble(M.getPersistentData(entity), "timer_step") == 11.0) {
                    if (!M.isClientSide(world) && world instanceof World) {
                        if (!M.isClientSide(world)) {
                            M.playSound(
                                world,
                                null,
                                BlockPos.containing(x, y, z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")),
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
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")),
                                SoundSource.HOSTILE,
                                3.0F,
                                1.0F,
                                false
                            );
                        }
                    }

                    M.putDouble(M.getPersistentData(entity), "timer_step", 0.0);
                }
            } else if (M.getDouble(M.getPersistentData(entity), "timer_step") == 19.0) {
                if (!M.isClientSide(world) && world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")),
                            SoundSource.NEUTRAL,
                            3.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world,
                            x,
                            y,
                            z,
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")),
                            SoundSource.NEUTRAL,
                            3.0F,
                            1.0F,
                            false
                        );
                    }
                }

                M.putDouble(M.getPersistentData(entity), "timer_step", 0.0);
            }
        }
    }
}
