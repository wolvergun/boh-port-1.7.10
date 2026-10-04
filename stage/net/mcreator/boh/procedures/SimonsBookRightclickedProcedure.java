package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.item.SimonsBookItem;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class SimonsBookRightclickedProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            ItemStack _ist = itemstack;
            if (M.hurt(_ist, 1, RandomSource.create(), null)) {
                M.shrink(_ist, 1);
                M.setDamageValue(_ist, 0);
            }
            BohMod.queueServerWork(2, () -> {
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }
                Entity _shootFrom = entity;
                World projectileLevel = M.level(_shootFrom);
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
                    }).getFireball(projectileLevel, entity, Mth.nextInt(RandomSource.create(), 0, 0), Mth.nextInt(RandomSource.create(), 0, 0), Mth.nextInt(RandomSource.create(), 0, 0));
                    M.setPos(_entityToSpawn, M.getX(_shootFrom), M.getEyeY(_shootFrom) - 0.1, M.getZ(_shootFrom));
                    M.shoot(_entityToSpawn, M.getLookAngle(_shootFrom).x, M.getLookAngle(_shootFrom).y, M.getLookAngle(_shootFrom).z, Mth.nextInt(RandomSource.create(), 1, 1), 0.0F);
                    M.addFreshEntity(projectileLevel, _entityToSpawn);
                }
                BohMod.queueServerWork(2, () -> {
                    if (world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                        }
                    }
                    Entity _shootFromx = entity;
                    World projectileLevelx = M.level(_shootFromx);
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
                        }).getFireball(projectileLevelx, entity, Mth.nextInt(RandomSource.create(), 0, 0), Mth.nextInt(RandomSource.create(), 0, 0), Mth.nextInt(RandomSource.create(), 0, 0));
                        M.setPos(_entityToSpawnx, M.getX(_shootFromx), M.getEyeY(_shootFromx) - 0.1, M.getZ(_shootFromx));
                        M.shoot(_entityToSpawnx, M.getLookAngle(_shootFromx).x, M.getLookAngle(_shootFromx).y, M.getLookAngle(_shootFromx).z, Mth.nextInt(RandomSource.create(), 1, 1), 0.0F);
                        M.addFreshEntity(projectileLevelx, _entityToSpawnx);
                    }
                    BohMod.queueServerWork(2, () -> {
                        if (world instanceof World _level) {
                            if (!M.isClientSide(_level)) {
                                M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                            } else {
                                M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                            }
                        }
                        Entity _shootFromxx = entity;
                        World projectileLevelxx = M.level(_shootFromxx);
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
                            }).getFireball(projectileLevelxx, entity, Mth.nextInt(RandomSource.create(), 0, 0), Mth.nextInt(RandomSource.create(), 0, 0), Mth.nextInt(RandomSource.create(), 0, 0));
                            M.setPos(_entityToSpawnxx, M.getX(_shootFromxx), M.getEyeY(_shootFromxx) - 0.1, M.getZ(_shootFromxx));
                            M.shoot(_entityToSpawnxx, M.getLookAngle(_shootFromxx).x, M.getLookAngle(_shootFromxx).y, M.getLookAngle(_shootFromxx).z, Mth.nextInt(RandomSource.create(), 1, 1), 0.0F);
                            M.addFreshEntity(projectileLevelxx, _entityToSpawnxx);
                        }
                        BohMod.queueServerWork(2, () -> {
                            if (world instanceof World _level) {
                                if (!M.isClientSide(_level)) {
                                    M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                } else {
                                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                }
                            }
                            Entity _shootFromxxx = entity;
                            World projectileLevelxxx = M.level(_shootFromxxx);
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
                                }).getFireball(projectileLevelxxx, entity, Mth.nextInt(RandomSource.create(), 0, 0), Mth.nextInt(RandomSource.create(), 0, 0), Mth.nextInt(RandomSource.create(), 0, 0));
                                M.setPos(_entityToSpawnxxx, M.getX(_shootFromxxx), M.getEyeY(_shootFromxxx) - 0.1, M.getZ(_shootFromxxx));
                                M.shoot(_entityToSpawnxxx, M.getLookAngle(_shootFromxxx).x, M.getLookAngle(_shootFromxxx).y, M.getLookAngle(_shootFromxxx).z, Mth.nextInt(RandomSource.create(), 1, 1), 0.0F);
                                M.addFreshEntity(projectileLevelxxx, _entityToSpawnxxx);
                            }
                            BohMod.queueServerWork(20, () -> {
                                Vec3 _center = new Vec3(x, y, z);
                                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(20.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                                    if (entityiterator instanceof EntitySmallFireball && !M.isClientSide(M.level(entityiterator))) {
                                        M.discard(entityiterator);
                                    }
                                }
                            });
                        });
                    });
                });
            });
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
