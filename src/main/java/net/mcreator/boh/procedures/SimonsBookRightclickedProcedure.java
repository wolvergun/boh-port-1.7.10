package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.item.SimonsBookItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SimonsBookRightclickedProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (M.hurt(itemstack, 1, RandomSource.create(), null)) {
                M.shrink(itemstack, 1);
                M.setDamageValue(itemstack, 0);
            }

            BohMod.queueServerWork(
                2,
                () -> {
                    if (world instanceof World) {
                        if (!M.isClientSide(world)) {
                            M.playSound(
                                world,
                                null,
                                BlockPos.containing(x, y, z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")),
                                SoundSource.NEUTRAL,
                                1.0F,
                                1.0F
                            );
                        } else {
                            M.playLocalSound(
                                world,
                                x,
                                y,
                                z,
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")),
                                SoundSource.NEUTRAL,
                                1.0F,
                                1.0F,
                                false
                            );
                        }
                    }

                    World projectileLevel = M.level(entity);
                    if (!M.isClientSide(projectileLevel)) {
                        Entity _entityToSpawn = (new Object() {
                                public Entity getFireball(World level, Entity shooter, double ax, double ay, double az) {
                                    EntityFireball entityToSpawn = M.new_EntitySmallFireball(EntityType.SMALL_FIREBALL, level);
                                    M.setOwner(entityToSpawn, shooter);
                                    M.set_xPower(entityToSpawn, ax);
                                    M.set_yPower(entityToSpawn, ay);
                                    M.set_zPower(entityToSpawn, az);
                                    return entityToSpawn;
                                }
                            })
                            .getFireball(
                                projectileLevel,
                                entity,
                                Mth.nextInt(RandomSource.create(), 0, 0),
                                Mth.nextInt(RandomSource.create(), 0, 0),
                                Mth.nextInt(RandomSource.create(), 0, 0)
                            );
                        M.setPos(_entityToSpawn, M.getX(entity), M.getEyeY(entity) - 0.1, M.getZ(entity));
                        M.shoot(
                            _entityToSpawn,
                            M.getLookAngle(entity).x,
                            M.getLookAngle(entity).y,
                            M.getLookAngle(entity).z,
                            Mth.nextInt(RandomSource.create(), 1, 1),
                            0.0F
                        );
                        M.addFreshEntity(projectileLevel, _entityToSpawn);
                    }

                    BohMod.queueServerWork(
                        2,
                        () -> {
                            if (world instanceof World) {
                                if (!M.isClientSide(world)) {
                                    M.playSound(
                                        world,
                                        null,
                                        BlockPos.containing(x, y, z),
                                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")),
                                        SoundSource.NEUTRAL,
                                        1.0F,
                                        1.0F
                                    );
                                } else {
                                    M.playLocalSound(
                                        world,
                                        x,
                                        y,
                                        z,
                                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")),
                                        SoundSource.NEUTRAL,
                                        1.0F,
                                        1.0F,
                                        false
                                    );
                                }
                            }

                            World projectileLevelx = M.level(entity);
                            if (!M.isClientSide(projectileLevelx)) {
                                Entity _entityToSpawnx = (new Object() {
                                        public Entity getFireball(World level, Entity shooter, double ax, double ay, double az) {
                                            EntityFireball entityToSpawn = M.new_EntitySmallFireball(EntityType.SMALL_FIREBALL, level);
                                            M.setOwner(entityToSpawn, shooter);
                                            M.set_xPower(entityToSpawn, ax);
                                            M.set_yPower(entityToSpawn, ay);
                                            M.set_zPower(entityToSpawn, az);
                                            return entityToSpawn;
                                        }
                                    })
                                    .getFireball(
                                        projectileLevelx,
                                        entity,
                                        Mth.nextInt(RandomSource.create(), 0, 0),
                                        Mth.nextInt(RandomSource.create(), 0, 0),
                                        Mth.nextInt(RandomSource.create(), 0, 0)
                                    );
                                M.setPos(_entityToSpawnx, M.getX(entity), M.getEyeY(entity) - 0.1, M.getZ(entity));
                                M.shoot(
                                    _entityToSpawnx,
                                    M.getLookAngle(entity).x,
                                    M.getLookAngle(entity).y,
                                    M.getLookAngle(entity).z,
                                    Mth.nextInt(RandomSource.create(), 1, 1),
                                    0.0F
                                );
                                M.addFreshEntity(projectileLevelx, _entityToSpawnx);
                            }

                            BohMod.queueServerWork(
                                2,
                                () -> {
                                    if (world instanceof World) {
                                        if (!M.isClientSide(world)) {
                                            M.playSound(
                                                world,
                                                null,
                                                BlockPos.containing(x, y, z),
                                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")),
                                                SoundSource.NEUTRAL,
                                                1.0F,
                                                1.0F
                                            );
                                        } else {
                                            M.playLocalSound(
                                                world,
                                                x,
                                                y,
                                                z,
                                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")),
                                                SoundSource.NEUTRAL,
                                                1.0F,
                                                1.0F,
                                                false
                                            );
                                        }
                                    }

                                    World projectileLevelxx = M.level(entity);
                                    if (!M.isClientSide(projectileLevelxx)) {
                                        Entity _entityToSpawnxx = (new Object() {
                                                public Entity getFireball(World level, Entity shooter, double ax, double ay, double az) {
                                                    EntityFireball entityToSpawn = M.new_EntitySmallFireball(EntityType.SMALL_FIREBALL, level);
                                                    M.setOwner(entityToSpawn, shooter);
                                                    M.set_xPower(entityToSpawn, ax);
                                                    M.set_yPower(entityToSpawn, ay);
                                                    M.set_zPower(entityToSpawn, az);
                                                    return entityToSpawn;
                                                }
                                            })
                                            .getFireball(
                                                projectileLevelxx,
                                                entity,
                                                Mth.nextInt(RandomSource.create(), 0, 0),
                                                Mth.nextInt(RandomSource.create(), 0, 0),
                                                Mth.nextInt(RandomSource.create(), 0, 0)
                                            );
                                        M.setPos(_entityToSpawnxx, M.getX(entity), M.getEyeY(entity) - 0.1, M.getZ(entity));
                                        M.shoot(
                                            _entityToSpawnxx,
                                            M.getLookAngle(entity).x,
                                            M.getLookAngle(entity).y,
                                            M.getLookAngle(entity).z,
                                            Mth.nextInt(RandomSource.create(), 1, 1),
                                            0.0F
                                        );
                                        M.addFreshEntity(projectileLevelxx, _entityToSpawnxx);
                                    }

                                    BohMod.queueServerWork(
                                        2,
                                        () -> {
                                            if (world instanceof World) {
                                                if (!M.isClientSide(world)) {
                                                    M.playSound(
                                                        world,
                                                        null,
                                                        BlockPos.containing(x, y, z),
                                                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")),
                                                        SoundSource.NEUTRAL,
                                                        1.0F,
                                                        1.0F
                                                    );
                                                } else {
                                                    M.playLocalSound(
                                                        world,
                                                        x,
                                                        y,
                                                        z,
                                                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")),
                                                        SoundSource.NEUTRAL,
                                                        1.0F,
                                                        1.0F,
                                                        false
                                                    );
                                                }
                                            }

                                            World projectileLevelxxx = M.level(entity);
                                            if (!M.isClientSide(projectileLevelxxx)) {
                                                Entity _entityToSpawnxxx = (new Object() {
                                                        public Entity getFireball(World level, Entity shooter, double ax, double ay, double az) {
                                                            EntityFireball entityToSpawn = M.new_EntitySmallFireball(EntityType.SMALL_FIREBALL, level);
                                                            M.setOwner(entityToSpawn, shooter);
                                                            M.set_xPower(entityToSpawn, ax);
                                                            M.set_yPower(entityToSpawn, ay);
                                                            M.set_zPower(entityToSpawn, az);
                                                            return entityToSpawn;
                                                        }
                                                    })
                                                    .getFireball(
                                                        projectileLevelxxx,
                                                        entity,
                                                        Mth.nextInt(RandomSource.create(), 0, 0),
                                                        Mth.nextInt(RandomSource.create(), 0, 0),
                                                        Mth.nextInt(RandomSource.create(), 0, 0)
                                                    );
                                                M.setPos(_entityToSpawnxxx, M.getX(entity), M.getEyeY(entity) - 0.1, M.getZ(entity));
                                                M.shoot(
                                                    _entityToSpawnxxx,
                                                    M.getLookAngle(entity).x,
                                                    M.getLookAngle(entity).y,
                                                    M.getLookAngle(entity).z,
                                                    Mth.nextInt(RandomSource.create(), 1, 1),
                                                    0.0F
                                                );
                                                M.addFreshEntity(projectileLevelxxx, _entityToSpawnxxx);
                                            }

                                            BohMod.queueServerWork(
                                                20,
                                                () -> {
                                                    Vec3 _center = new Vec3(x, y, z);

                                                    for (Entity entityiterator : M.getEntitiesOfClass(
                                                            world, Entity.class, new AABB(_center, _center).inflate(20.0), e -> true
                                                        )
                                                        .stream()
                                                        .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                                                        .toList()) {
                                                        if (entityiterator instanceof EntitySmallFireball && !M.isClientSide(M.level(entityiterator))) {
                                                            M.discard(entityiterator);
                                                        }
                                                    }
                                                }
                                            );
                                        }
                                    );
                                }
                            );
                        }
                    );
                }
            );
            if (M.getItem(itemstack) instanceof SimonsBookItem) {
                M.putString(M.getOrCreateTag(itemstack), "geckoAnim", "fire");
            }

            if (entity instanceof EntityPlayer _player) {
                M.addCooldown(M.getCooldowns(_player), M.getItem(itemstack), 100);
            }

            BohMod.queueServerWork(20, () -> {
                if (M.getItem(itemstack) instanceof SimonsBookItem) {
                    M.putString(M.getOrCreateTag(itemstack), "geckoAnim", "empty");
                }
            });
        }
    }
}
