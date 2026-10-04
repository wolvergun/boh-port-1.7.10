package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.GrayAlienEntity;
import net.mcreator.boh.entity.MartianDroneEntity;
import net.mcreator.boh.entity.SaucerEntity;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class SaucerOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (Math.random() < 0.005) {
                M.teleportTo(
                    entity,
                    M.getX(entity) + Mth.nextDouble(RandomSource.create(), -5.0, 5.0),
                    M.getY(entity),
                    M.getZ(entity) + Mth.nextDouble(RandomSource.create(), -5.0, 5.0)
                );
                if (entity instanceof EntityPlayerMP _serverPlayer) {
                    M.teleport(
                        M.connection(_serverPlayer),
                        M.getX(entity) + Mth.nextDouble(RandomSource.create(), -5.0, 5.0),
                        M.getY(entity),
                        M.getZ(entity) + Mth.nextDouble(RandomSource.create(), -5.0, 5.0),
                        M.getYRot(entity),
                        M.getXRot(entity)
                    );
                }
            }

            if (!M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, M.getY(entity) - 20.0, z), 4.0, 4.0, 4.0), e -> true))
                || !M.isEmpty(M.getEntitiesOfClass(world, EntityCow.class, AABB.ofSize(new Vec3(x, M.getY(entity) - 20.0, z), 4.0, 4.0, 4.0), e -> true))) {
                Vec3 _center = new Vec3(x, M.getY(entity) - Mth.nextDouble(RandomSource.create(), 15.0, 35.0), z);

                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(2.0), e -> true)
                    .stream()
                    .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                    .toList()) {
                    if (entityiterator instanceof EntityCow || entityiterator instanceof EntityPlayer) {
                        if (entity instanceof SaucerEntity) {
                            ((SaucerEntity)entity).setAnimation("abduction");
                        }

                        if (entityiterator instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                            M.addEffect(_entity, M.new_PotionEffect(MobEffects.LEVITATION, 30, 4, false, false));
                        }
                    }
                }
            }

            if (Math.random() < 0.01) {
                if (world instanceof WorldServer _level) {
                    Entity entityToSpawn = M.spawn(
                        BohModEntities.GRAY_ALIEN.get(), _level, BlockPos.containing(M.getX(entity), M.getY(entity), M.getZ(entity)), MobSpawnType.MOB_SUMMONED
                    );
                    if (entityToSpawn != null) {
                        M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                    }
                }

                BohMod.queueServerWork(
                    4,
                    () -> {
                        Vec3 _center = new Vec3(x, y, z);

                        for (Entity entityiteratorx : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(50.0), e -> true)
                            .stream()
                            .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                            .toList()) {
                            if (entityiteratorx instanceof GrayAlienEntity) {
                                M.putBoolean(M.getPersistentData(entityiteratorx), "spawn_thru_ship", true);
                            }
                        }
                    }
                );
            }

            if (Math.random() < 1.0E-4 && world instanceof WorldServer _levelx) {
                Entity entityToSpawn = M.spawn(
                    BohModEntities.FLATWOODS_MONSTER.get(),
                    _levelx,
                    BlockPos.containing(M.getX(entity), M.getY(entity), M.getZ(entity)),
                    MobSpawnType.MOB_SUMMONED
                );
                if (entityToSpawn != null) {
                    M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                }
            }

            if (Math.random() < 0.005) {
                if (world instanceof WorldServer _levelxx) {
                    Entity entityToSpawn = M.spawn(
                        BohModEntities.MARTIAN_DRONE.get(),
                        _levelxx,
                        BlockPos.containing(M.getX(entity), M.getY(entity), M.getZ(entity)),
                        MobSpawnType.MOB_SUMMONED
                    );
                    if (entityToSpawn != null) {
                        M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                    }
                }

                BohMod.queueServerWork(
                    4,
                    () -> {
                        Vec3 _center = new Vec3(x, y, z);

                        for (Entity entityiteratorx : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(50.0), e -> true)
                            .stream()
                            .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                            .toList()) {
                            if (entityiteratorx instanceof MartianDroneEntity) {
                                M.putBoolean(M.getPersistentData(entityiteratorx), "spawn_thru_ship", true);
                            }
                        }
                    }
                );
            }

            if (Math.random() < 0.01 && world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mother_ship_idle")),
                        SoundSource.HOSTILE,
                        100.0F,
                        (float)Mth.nextDouble(RandomSource.create(), -1.0, 2.0)
                    );
                } else {
                    M.playLocalSound(
                        world,
                        x,
                        y,
                        z,
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mother_ship_idle")),
                        SoundSource.HOSTILE,
                        100.0F,
                        (float)Mth.nextDouble(RandomSource.create(), -1.0, 2.0),
                        false
                    );
                }
            }

            M.putDouble(M.getPersistentData(entity), "tick_ambience", M.getDouble(M.getPersistentData(entity), "tick_ambience") + 1.0);
            if (M.getDouble(M.getPersistentData(entity), "tick_ambience") == 580.0) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mother_ship_loop")),
                            SoundSource.HOSTILE,
                            100.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world,
                            x,
                            y,
                            z,
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mother_ship_loop")),
                            SoundSource.HOSTILE,
                            100.0F,
                            1.0F,
                            false
                        );
                    }
                }

                M.putDouble(M.getPersistentData(entity), "tick_ambience", 0.0);
            }

            M.putDouble(M.getPersistentData(entity), "tick_music", M.getDouble(M.getPersistentData(entity), "tick_music") + 1.0);
            if (M.getDouble(M.getPersistentData(entity), "tick_music") == 2140.0) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:gray_ost")),
                            SoundSource.MUSIC,
                            1.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:gray_ost")), SoundSource.MUSIC, 1.0F, 1.0F, false
                        );
                    }
                }

                M.putDouble(M.getPersistentData(entity), "tick_music", 0.0);
            }
        }
    }
}
