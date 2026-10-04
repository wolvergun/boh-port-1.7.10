package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.SirenHeadEntity;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class SirenheadOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if ((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityLivingBase
                && Math.random() < 0.005
                && world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y + 30.0, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sirenhead_siren")),
                        SoundSource.HOSTILE,
                        10.0F,
                        1.0F
                    );
                } else {
                    M.playLocalSound(
                        world,
                        x,
                        y + 30.0,
                        z,
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sirenhead_siren")),
                        SoundSource.HOSTILE,
                        10.0F,
                        1.0F,
                        false
                    );
                }
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
                    "/fill ~-1 ~ ~-1 ~1 ~9 ~1 air replace #minecraft:leaves"
                );
            }

            if (M.getDeltaMovement(entity).horizontalDistanceSqr() > 1.0E-6) {
                M.putDouble(M.getPersistentData(entity), "timer_step", M.getDouble(M.getPersistentData(entity), "timer_step") + 1.0);
            } else {
                M.putDouble(M.getPersistentData(entity), "timer_step", 0.0);
            }

            if (entity instanceof EntityLiving _mob && M.isAggressive(_mob)) {
                if (entity instanceof EntityLiving _mobx && M.isAggressive(_mobx) && M.getDouble(M.getPersistentData(entity), "timer_step") == 9.0) {
                    if (!M.isClientSide(world) && world instanceof World) {
                        if (!M.isClientSide(world)) {
                            M.playSound(
                                world,
                                null,
                                BlockPos.containing(x, y, z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")),
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
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")),
                                SoundSource.HOSTILE,
                                2.0F,
                                1.0F,
                                false
                            );
                        }
                    }

                    M.putDouble(M.getPersistentData(entity), "timer_step", 0.0);
                }
            } else if (M.getDouble(M.getPersistentData(entity), "timer_step") == 22.0) {
                if (!M.isClientSide(world) && world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")),
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
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_step")),
                            SoundSource.HOSTILE,
                            2.0F,
                            1.0F,
                            false
                        );
                    }
                }

                M.putDouble(M.getPersistentData(entity), "timer_step", 0.0);
            }

            if (!(entity instanceof SirenHeadEntity _datEntL18 && M.getEntityData(_datEntL18).get(SirenHeadEntity.DATA_sonicboom_logic))) {
                if (entity instanceof EntityLiving _mobx
                    && M.isAggressive(_mobx)
                    && !(entity instanceof SirenHeadEntity _datEntL20 && M.getEntityData(_datEntL20).get(SirenHeadEntity.DATA_sonicboom))
                    && !M.isEmpty(M.getEntitiesOfClass(world, EntityLivingBase.class, AABB.ofSize(new Vec3(x, y, z), 13.0, 13.0, 13.0), e -> true))
                    && Math.random() < 0.02
                    && entity instanceof SirenHeadEntity _datEntSetL) {
                    M.set(M.getEntityData(_datEntSetL), SirenHeadEntity.DATA_sonicboom, true);
                }

                if (entity instanceof SirenHeadEntity _datEntL23 && M.getEntityData(_datEntL23).get(SirenHeadEntity.DATA_sonicboom)) {
                    if (entity instanceof SirenHeadEntity _datEntSetL) {
                        M.set(M.getEntityData(_datEntSetL), SirenHeadEntity.DATA_sonicboom_logic, true);
                    }

                    BohMod.queueServerWork(
                        9,
                        () -> {
                            if (world instanceof World) {
                                if (!M.isClientSide(world)) {
                                    M.playSound(
                                        world,
                                        null,
                                        BlockPos.containing(x, y, z),
                                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sirenhead_sonicboom")),
                                        SoundSource.HOSTILE,
                                        2.0F,
                                        0.5F
                                    );
                                } else {
                                    M.playLocalSound(
                                        world,
                                        x,
                                        y,
                                        z,
                                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sirenhead_sonicboom")),
                                        SoundSource.HOSTILE,
                                        2.0F,
                                        0.5F,
                                        false
                                    );
                                }
                            }
                        }
                    );
                    if (world instanceof WorldServer _level) {
                        Entity entityToSpawn = M.spawn(BohModEntities.SONIC_BOOM.get(), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                        }
                    }

                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 75, 254, false, false));
                    }

                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.WEAKNESS, 75, 255, false, false));
                    }

                    if (entity instanceof SirenHeadEntity) {
                        ((SirenHeadEntity)entity).setAnimation("stun");
                    }

                    Vec3 _center = new Vec3(x, y, z);

                    for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(7.5), e -> true)
                        .stream()
                        .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                        .toList()) {
                        if (!(entityiterator instanceof SirenHeadEntity)) {
                            if (entityiterator instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                                M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 250, 2, false, false));
                            }

                            if (entityiterator instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                                M.addEffect(_entity, M.new_PotionEffect(MobEffects.CONFUSION, 250, 0, false, false));
                            }
                        }
                    }

                    BohMod.queueServerWork(200, () -> {
                        if (entity instanceof SirenHeadEntity _datEntSetL) {
                            M.set(M.getEntityData(_datEntSetL), SirenHeadEntity.DATA_sonicboom_logic, false);
                        }

                        if (entity instanceof SirenHeadEntity _datEntSetL) {
                            M.set(M.getEntityData(_datEntSetL), SirenHeadEntity.DATA_sonicboom, false);
                        }
                    });
                }
            }
        }
    }
}
