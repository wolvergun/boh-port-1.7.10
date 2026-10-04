package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.PhantomBBEntity;
import net.mcreator.boh.entity.PhantomChicaEntity;
import net.mcreator.boh.entity.PhantomFoxyEntity;
import net.mcreator.boh.entity.PhantomFreddyEntity;
import net.mcreator.boh.entity.PhantomMangleEntity;
import net.mcreator.boh.entity.PhantomPuppetEntity;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class MassacreAxeRightclickedProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (entity instanceof EntityPlayer _player) {
                M.addCooldown(M.getCooldowns(_player), M.getItem(itemstack), 300);
            }

            if (M.hurt(itemstack, 5, RandomSource.create(), null)) {
                M.shrink(itemstack, 1);
                M.setDamageValue(itemstack, 0);
            }

            if (M.getDouble(M.getOrCreateTag(itemstack), "summon_animatronic") < 5.0) {
                M.putDouble(M.getOrCreateTag(itemstack), "summon_animatronic", M.getDouble(M.getOrCreateTag(itemstack), "summon_animatronic") + 1.0);
            } else {
                M.putDouble(M.getOrCreateTag(itemstack), "summon_animatronic", 0.0);
            }

            if (M.getDouble(M.getOrCreateTag(itemstack), "summon_animatronic") == 0.0) {
                if (world instanceof WorldServer _level) {
                    Entity entityToSpawn = M.spawn(BohModEntities.PHANTOM_FREDDY.get(), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                        M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                    }
                }

                BohMod.queueServerWork(
                    2,
                    () -> {
                        Vec3 _center = new Vec3(x, y, z);

                        for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(10.0), e -> true)
                            .stream()
                            .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                            .toList()) {
                            if (entityiterator instanceof PhantomFreddyEntity
                                && !(entity instanceof EntityTameable _tamEnt && M.isTame(_tamEnt))
                                && entityiterator instanceof EntityTameable _toTame
                                && entity instanceof EntityPlayer _owner) {
                                M.tame(_toTame, _owner);
                            }
                        }
                    }
                );
            } else if (M.getDouble(M.getOrCreateTag(itemstack), "summon_animatronic") == 1.0) {
                if (world instanceof WorldServer _levelx) {
                    Entity entityToSpawn = M.spawn(BohModEntities.PHANTOM_CHICA.get(), _levelx, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                        M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                    }
                }

                BohMod.queueServerWork(
                    2,
                    () -> {
                        Vec3 _center = new Vec3(x, y, z);

                        for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(10.0), e -> true)
                            .stream()
                            .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                            .toList()) {
                            if (entityiterator instanceof PhantomChicaEntity
                                && !(entity instanceof EntityTameable _tamEnt && M.isTame(_tamEnt))
                                && entityiterator instanceof EntityTameable _toTame
                                && entity instanceof EntityPlayer _owner) {
                                M.tame(_toTame, _owner);
                            }
                        }
                    }
                );
            } else if (M.getDouble(M.getOrCreateTag(itemstack), "summon_animatronic") == 2.0) {
                if (world instanceof WorldServer _levelxx) {
                    Entity entityToSpawn = M.spawn(BohModEntities.PHANTOM_BB.get(), _levelxx, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                        M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                    }
                }

                BohMod.queueServerWork(
                    2,
                    () -> {
                        Vec3 _center = new Vec3(x, y, z);

                        for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(10.0), e -> true)
                            .stream()
                            .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                            .toList()) {
                            if (entityiterator instanceof PhantomBBEntity
                                && !(entity instanceof EntityTameable _tamEnt && M.isTame(_tamEnt))
                                && entityiterator instanceof EntityTameable _toTame
                                && entity instanceof EntityPlayer _owner) {
                                M.tame(_toTame, _owner);
                            }
                        }
                    }
                );
            } else if (M.getDouble(M.getOrCreateTag(itemstack), "summon_animatronic") == 3.0) {
                if (world instanceof WorldServer _levelxxx) {
                    Entity entityToSpawn = M.spawn(BohModEntities.PHANTOM_FOXY.get(), _levelxxx, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                        M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                    }
                }

                BohMod.queueServerWork(
                    2,
                    () -> {
                        Vec3 _center = new Vec3(x, y, z);

                        for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(10.0), e -> true)
                            .stream()
                            .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                            .toList()) {
                            if (entityiterator instanceof PhantomFoxyEntity
                                && !(entity instanceof EntityTameable _tamEnt && M.isTame(_tamEnt))
                                && entityiterator instanceof EntityTameable _toTame
                                && entity instanceof EntityPlayer _owner) {
                                M.tame(_toTame, _owner);
                            }
                        }
                    }
                );
            } else if (M.getDouble(M.getOrCreateTag(itemstack), "summon_animatronic") == 4.0) {
                if (world instanceof WorldServer _levelxxxx) {
                    Entity entityToSpawn = M.spawn(BohModEntities.PHANTOM_MANGLE.get(), _levelxxxx, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                        M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                    }
                }

                BohMod.queueServerWork(
                    2,
                    () -> {
                        Vec3 _center = new Vec3(x, y, z);

                        for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(10.0), e -> true)
                            .stream()
                            .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                            .toList()) {
                            if (entityiterator instanceof PhantomMangleEntity
                                && !(entity instanceof EntityTameable _tamEnt && M.isTame(_tamEnt))
                                && entityiterator instanceof EntityTameable _toTame
                                && entity instanceof EntityPlayer _owner) {
                                M.tame(_toTame, _owner);
                            }
                        }
                    }
                );
            } else if (M.getDouble(M.getOrCreateTag(itemstack), "summon_animatronic") == 5.0) {
                if (world instanceof WorldServer _levelxxxxx) {
                    Entity entityToSpawn = M.spawn(BohModEntities.PHANTOM_PUPPET.get(), _levelxxxxx, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                        M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                    }
                }

                BohMod.queueServerWork(
                    2,
                    () -> {
                        Vec3 _center = new Vec3(x, y, z);

                        for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(10.0), e -> true)
                            .stream()
                            .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                            .toList()) {
                            if (entityiterator instanceof PhantomPuppetEntity
                                && !(entity instanceof EntityTameable _tamEnt && M.isTame(_tamEnt))
                                && entityiterator instanceof EntityTameable _toTame
                                && entity instanceof EntityPlayer _owner) {
                                M.tame(_toTame, _owner);
                            }
                        }
                    }
                );
            }
        }
    }
}
