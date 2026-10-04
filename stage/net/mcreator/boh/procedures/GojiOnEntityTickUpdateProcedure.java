package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.BloodSpillEntity;
import net.mcreator.boh.entity.GojiEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.commands.arguments.Anchor;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.mcreator.boh.compat.entity.BohAbstractArrow;
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

public class GojiOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if ((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer && !M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 4.0, 4.0, 4.0), e -> true)) && Math.random() < 0.03) {
                if (entity instanceof GojiEntity) {
                    ((GojiEntity) entity).setAnimation("stomp");
                }
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 255, false, false));
                }
                BohMod.queueServerWork(14, () -> {
                    for (int index0 = 0; index0 < 10; index0++) {
                        if (world instanceof World _level) {
                            if (!M.isClientSide(_level)) {
                                M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.explode")), SoundSource.HOSTILE, 1.0F, 1.0F);
                            } else {
                                M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.explode")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                            }
                        }
                        M.levelEvent(world, 2001, BlockPos.containing(Mth.nextInt(RandomSource.create(), -3, 3) + x, y, Mth.nextInt(RandomSource.create(), -3, 3) + z), M.blockStateId(M.getBlockState(world, BlockPos.containing(x, y - 1.0, z))));
                    }
                });
                Vec3 _center = new Vec3(x, y, z);
                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(2.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                    BohMod.queueServerWork(14, () -> {
                        if (entityiterator instanceof EntityPlayer) {
                            Entity _ent_l31 = entityiterator;
                            if (!M.isClientSide(M.level(_ent_l31)) && M.getServer(_ent_l31) != null) {
                                M.performPrefixedCommand(M.getCommands(M.getServer(_ent_l31)), new CommandSourceStack(CommandSource.NULL, M.position(_ent_l31), M.getRotationVector(_ent_l31), M.level(_ent_l31) instanceof WorldServer ? (WorldServer) M.level(_ent_l31) : null, 4, M.getString(M.getName(_ent_l31)), M.getDisplayName(_ent_l31), M.getServer(M.level(_ent_l31)), _ent_l31), "/effect give @s minecraft:levitation 1 6 true");
                            }
                            M.hurt(entityiterator, M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.GENERIC)), 7.0F);
                        }
                    });
                }
            }
            if (!M.getBoolean(M.getPersistentData(entity), "rexy_roar") && (entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer) {
                M.putBoolean(M.getPersistentData(entity), "rexy_roar", true);
            }
            if (Math.random() < 0.25 && !M.getBoolean(M.getPersistentData(entity), "twitch") && M.getBoolean(M.getPersistentData(entity), "rexy_roar")) {
                if (entity instanceof GojiEntity) {
                    ((GojiEntity) entity).setAnimation("roar");
                }
                Entity _ent = entity;
                Scoreboard _sc = M.getScoreboard(M.level(_ent));
                ScoreObjective _so = M.getObjective(_sc, "anim");
                if (_so == null) {
                    _so = M.addObjective(_sc, "anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
                }
                M.setScore(M.getOrCreatePlayerScore(_sc, M.getScoreboardName(_ent), _so), 2);
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:goji_roar")), SoundSource.HOSTILE, 3.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:goji_roar")), SoundSource.HOSTILE, 3.0F, 1.0F, false);
                    }
                }
                M.putBoolean(M.getPersistentData(entity), "twitch", true);
                BohMod.queueServerWork(68, () -> {
                    Entity _entx = entity;
                    Scoreboard _scx = M.getScoreboard(M.level(_entx));
                    ScoreObjective _sox = M.getObjective(_scx, "anim");
                    if (_sox == null) {
                        _sox = M.addObjective(_scx, "anim", ObjectiveCriteria.DUMMY, Component.literal("anim"), RenderType.INTEGER);
                    }
                    M.setScore(M.getOrCreatePlayerScore(_scx, M.getScoreboardName(_entx), _sox), 0);
                });
            }
            if (!((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer)) {
                M.putBoolean(M.getPersistentData(entity), "rexy_roar", false);
                M.putBoolean(M.getPersistentData(entity), "twitch", false);
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
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 10, 255, false, false));
                }
            }
            if (M.getBoolean(M.getPersistentData(entity), "shoot_goji")) {
                M.makeStuckInBlock(entity, M.defaultBlockState(Blocks.AIR), new Vec3(0.25, 0.05, 0.25));
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 10, 255, false, false));
                }
            }
            if ((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer && Math.random() < 0.005 && !M.getBoolean(M.getPersistentData(entity), "shoot_goji")) {
                M.putBoolean(M.getPersistentData(entity), "shoot_goji", true);
                if (entity instanceof GojiEntity) {
                    ((GojiEntity) entity).setAnimation("spit");
                }
                Entity _ent = entity;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/playsound boh:goji_buildup hostile @a ~ ~ ~ 2 1");
                }
                Vec3 _center = new Vec3(x, y, z);
                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(50.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                    if (entityiterator instanceof EntityPlayer) {
                        M.lookAt(entity, Anchor.EYES, new Vec3(M.getX(entityiterator), M.getY(entityiterator), M.getZ(entityiterator)));
                    }
                }
                BohMod.queueServerWork(10, () -> {
                    Entity _entx = entity;
                    if (!M.isClientSide(M.level(_entx)) && M.getServer(_entx) != null) {
                        M.performPrefixedCommand(M.getCommands(M.getServer(_entx)), new CommandSourceStack(CommandSource.NULL, M.position(_entx), M.getRotationVector(_entx), M.level(_entx) instanceof WorldServer ? (WorldServer) M.level(_entx) : null, 4, M.getString(M.getName(_entx)), M.getDisplayName(_entx), M.getServer(M.level(_entx)), _entx), "/playsound boh:goji_breath hostile @a ~ ~ ~ 0.4 1");
                    }
                    Entity _entx_r34 = entity;
                    if (!M.isClientSide(M.level(_entx_r34)) && M.getServer(_entx_r34) != null) {
                        M.performPrefixedCommand(M.getCommands(M.getServer(_entx_r34)), new CommandSourceStack(CommandSource.NULL, M.position(_entx_r34), M.getRotationVector(_entx_r34), M.level(_entx_r34) instanceof WorldServer ? (WorldServer) M.level(_entx_r34) : null, 4, M.getString(M.getName(_entx_r34)), M.getDisplayName(_entx_r34), M.getServer(M.level(_entx_r34)), _entx_r34), "/playsound minecraft:entity.llama.spit hostile @a ~ ~ ~ 0.4 0.2");
                    }
                    Entity _entx_r35 = entity;
                    World projectileLevel = M.level(_entx_r35);
                    if (!M.isClientSide(projectileLevel)) {
                        Entity _entityToSpawn = (new Object() {

                            public Entity getArrow(World level, Entity shooter, float damage, int knockback) {
                                BohAbstractArrow entityToSpawn = new BloodSpillEntity((EntityType<? extends BloodSpillEntity>) BohModEntities.BLOOD_SPILL.get(), level);
                                M.setOwner(entityToSpawn, shooter);
                                M.setBaseDamage(entityToSpawn, damage);
                                M.setKnockback(entityToSpawn, knockback);
                                M.setSilent(entityToSpawn, true);
                                return entityToSpawn;
                            }
                        }).getArrow(projectileLevel, entity, 5.0F, 0);
                        M.setPos(_entityToSpawn, M.getX(_entx_r35), M.getEyeY(_entx_r35) - 0.1, M.getZ(_entx_r35));
                        M.shoot(_entityToSpawn, M.getLookAngle(_entx_r35).x, M.getLookAngle(_entx_r35).y, M.getLookAngle(_entx_r35).z, 0.9F, 0.0F);
                        M.addFreshEntity(projectileLevel, _entityToSpawn);
                    }
                    BohMod.queueServerWork(10, () -> {
                        Entity _entx_l32 = entity;
                        if (!M.isClientSide(M.level(_entx_l32)) && M.getServer(_entx_l32) != null) {
                            M.performPrefixedCommand(M.getCommands(M.getServer(_entx_l32)), new CommandSourceStack(CommandSource.NULL, M.position(_entx_l32), M.getRotationVector(_entx_l32), M.level(_entx_l32) instanceof WorldServer ? (WorldServer) M.level(_entx_l32) : null, 4, M.getString(M.getName(_entx_l32)), M.getDisplayName(_entx_l32), M.getServer(M.level(_entx_l32)), _entx_l32), "/playsound minecraft:entity.llama.spit hostile @a ~ ~ ~ 0.4 0.2");
                        }
                        Entity _entx_l32_r36 = entity;
                        World projectileLevelx = M.level(_entx_l32_r36);
                        if (!M.isClientSide(projectileLevelx)) {
                            Entity _entityToSpawnx = (new Object() {

                                public Entity getArrow(World level, Entity shooter, float damage, int knockback) {
                                    BohAbstractArrow entityToSpawn = new BloodSpillEntity((EntityType<? extends BloodSpillEntity>) BohModEntities.BLOOD_SPILL.get(), level);
                                    M.setOwner(entityToSpawn, shooter);
                                    M.setBaseDamage(entityToSpawn, damage);
                                    M.setKnockback(entityToSpawn, knockback);
                                    M.setSilent(entityToSpawn, true);
                                    return entityToSpawn;
                                }
                            }).getArrow(projectileLevelx, entity, 5.0F, 0);
                            M.setPos(_entityToSpawnx, M.getX(_entx_l32_r36), M.getEyeY(_entx_l32_r36) - 0.1, M.getZ(_entx_l32_r36));
                            M.shoot(_entityToSpawnx, M.getLookAngle(_entx_l32_r36).x, M.getLookAngle(_entx_l32_r36).y, M.getLookAngle(_entx_l32_r36).z, 0.9F, 0.0F);
                            M.addFreshEntity(projectileLevelx, _entityToSpawnx);
                        }
                        BohMod.queueServerWork(10, () -> {
                            Entity _entx_l33 = entity;
                            if (!M.isClientSide(M.level(_entx_l32_r36)) && M.getServer(_entx_l32_r36) != null) {
                                M.performPrefixedCommand(M.getCommands(M.getServer(_entx_l32_r36)), new CommandSourceStack(CommandSource.NULL, M.position(_entx_l32_r36), M.getRotationVector(_entx_l32_r36), M.level(_entx_l32_r36) instanceof WorldServer ? (WorldServer) M.level(_entx_l32_r36) : null, 4, M.getString(M.getName(_entx_l32_r36)), M.getDisplayName(_entx_l32_r36), M.getServer(M.level(_entx_l32_r36)), _entx_l32_r36), "/playsound minecraft:entity.llama.spit hostile @a ~ ~ ~ 0.4 0.2");
                            }
                            Entity _entx_l32_r37 = entity;
                            World projectileLevelxx = M.level(_entx_l32_r37);
                            if (!M.isClientSide(projectileLevelxx)) {
                                Entity _entityToSpawnxx = (new Object() {

                                    public Entity getArrow(World level, Entity shooter, float damage, int knockback) {
                                        BohAbstractArrow entityToSpawn = new BloodSpillEntity((EntityType<? extends BloodSpillEntity>) BohModEntities.BLOOD_SPILL.get(), level);
                                        M.setOwner(entityToSpawn, shooter);
                                        M.setBaseDamage(entityToSpawn, damage);
                                        M.setKnockback(entityToSpawn, knockback);
                                        M.setSilent(entityToSpawn, true);
                                        return entityToSpawn;
                                    }
                                }).getArrow(projectileLevelxx, entity, 5.0F, 0);
                                M.setPos(_entityToSpawnxx, M.getX(_entx_l32_r37), M.getEyeY(_entx_l32_r37) - 0.1, M.getZ(_entx_l32_r37));
                                M.shoot(_entityToSpawnxx, M.getLookAngle(_entx_l32_r37).x, M.getLookAngle(_entx_l32_r37).y, M.getLookAngle(_entx_l32_r37).z, 0.9F, 0.0F);
                                M.addFreshEntity(projectileLevelxx, _entityToSpawnxx);
                            }
                            M.putBoolean(M.getPersistentData(entity), "shoot_goji", false);
                        });
                    });
                });
            }
            Entity _ent = entity;
            if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/fill ~-1 ~ ~-1 ~1 ~4 ~1 air replace #minecraft:leaves");
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
                    if (!M.isClientSide(world) && world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")), SoundSource.HOSTILE, 3.0F, 1.0F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")), SoundSource.HOSTILE, 3.0F, 1.0F, false);
                        }
                    }
                    M.putDouble(M.getPersistentData(entity), "timer_step", 0.0);
                }
            } else if (M.getDouble(M.getPersistentData(entity), "timer_step") == 19.0) {
                if (!M.isClientSide(world) && world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")), SoundSource.NEUTRAL, 3.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")), SoundSource.NEUTRAL, 3.0F, 1.0F, false);
                    }
                }
                M.putDouble(M.getPersistentData(entity), "timer_step", 0.0);
            }
        }
    }
}
