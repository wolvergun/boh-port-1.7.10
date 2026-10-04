package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.entity.BohAbstractArrow;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.ShotgunProjectileEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.item.BoomStickItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BoomStickRightclickedProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (M.hurt(itemstack, 1, RandomSource.create(), null)) {
                M.shrink(itemstack, 1);
                M.setDamageValue(itemstack, 0);
            }

            if (entity instanceof EntityPlayer _player) {
                M.addCooldown(M.getCooldowns(_player), M.getItem(itemstack), 34);
            }

            World projectileLevel = M.level(entity);
            if (!M.isClientSide(projectileLevel)) {
                Entity _entityToSpawn = (new Object() {
                    public Entity getArrow(World level, Entity shooter, float damage, int knockback) {
                        BohAbstractArrow entityToSpawn = new ShotgunProjectileEntity(BohModEntities.SHOTGUN_PROJECTILE.get(), level);
                        M.setOwner(entityToSpawn, shooter);
                        M.setBaseDamage(entityToSpawn, damage);
                        M.setKnockback(entityToSpawn, knockback);
                        M.setSilent(entityToSpawn, true);
                        return entityToSpawn;
                    }
                }).getArrow(projectileLevel, entity, 2.5F, 1);
                M.setPos(_entityToSpawn, M.getX(entity), M.getEyeY(entity) - 0.1, M.getZ(entity));
                M.shoot(_entityToSpawn, M.getLookAngle(entity).x, M.getLookAngle(entity).y, M.getLookAngle(entity).z, 3.0F, 10.0F);
                M.addFreshEntity(projectileLevel, _entityToSpawn);
            }

            projectileLevel = M.level(entity);
            if (!M.isClientSide(projectileLevel)) {
                Entity _entityToSpawn = (new Object() {
                    public Entity getArrow(World level, Entity shooter, float damage, int knockback) {
                        BohAbstractArrow entityToSpawn = new ShotgunProjectileEntity(BohModEntities.SHOTGUN_PROJECTILE.get(), level);
                        M.setOwner(entityToSpawn, shooter);
                        M.setBaseDamage(entityToSpawn, damage);
                        M.setKnockback(entityToSpawn, knockback);
                        M.setSilent(entityToSpawn, true);
                        return entityToSpawn;
                    }
                }).getArrow(projectileLevel, entity, 2.5F, 1);
                M.setPos(_entityToSpawn, M.getX(entity), M.getEyeY(entity) - 0.1, M.getZ(entity));
                M.shoot(_entityToSpawn, M.getLookAngle(entity).x, M.getLookAngle(entity).y, M.getLookAngle(entity).z, 3.0F, 10.0F);
                M.addFreshEntity(projectileLevel, _entityToSpawn);
            }

            projectileLevel = M.level(entity);
            if (!M.isClientSide(projectileLevel)) {
                Entity _entityToSpawn = (new Object() {
                    public Entity getArrow(World level, Entity shooter, float damage, int knockback) {
                        BohAbstractArrow entityToSpawn = new ShotgunProjectileEntity(BohModEntities.SHOTGUN_PROJECTILE.get(), level);
                        M.setOwner(entityToSpawn, shooter);
                        M.setBaseDamage(entityToSpawn, damage);
                        M.setKnockback(entityToSpawn, knockback);
                        M.setSilent(entityToSpawn, true);
                        return entityToSpawn;
                    }
                }).getArrow(projectileLevel, entity, 2.5F, 1);
                M.setPos(_entityToSpawn, M.getX(entity), M.getEyeY(entity) - 0.1, M.getZ(entity));
                M.shoot(_entityToSpawn, M.getLookAngle(entity).x, M.getLookAngle(entity).y, M.getLookAngle(entity).z, 3.0F, 10.0F);
                M.addFreshEntity(projectileLevel, _entityToSpawn);
            }

            projectileLevel = M.level(entity);
            if (!M.isClientSide(projectileLevel)) {
                Entity _entityToSpawn = (new Object() {
                    public Entity getArrow(World level, Entity shooter, float damage, int knockback) {
                        BohAbstractArrow entityToSpawn = new ShotgunProjectileEntity(BohModEntities.SHOTGUN_PROJECTILE.get(), level);
                        M.setOwner(entityToSpawn, shooter);
                        M.setBaseDamage(entityToSpawn, damage);
                        M.setKnockback(entityToSpawn, knockback);
                        M.setSilent(entityToSpawn, true);
                        return entityToSpawn;
                    }
                }).getArrow(projectileLevel, entity, 2.5F, 1);
                M.setPos(_entityToSpawn, M.getX(entity), M.getEyeY(entity) - 0.1, M.getZ(entity));
                M.shoot(_entityToSpawn, M.getLookAngle(entity).x, M.getLookAngle(entity).y, M.getLookAngle(entity).z, 3.0F, 10.0F);
                M.addFreshEntity(projectileLevel, _entityToSpawn);
            }

            projectileLevel = M.level(entity);
            if (!M.isClientSide(projectileLevel)) {
                Entity _entityToSpawn = (new Object() {
                    public Entity getArrow(World level, Entity shooter, float damage, int knockback) {
                        BohAbstractArrow entityToSpawn = new ShotgunProjectileEntity(BohModEntities.SHOTGUN_PROJECTILE.get(), level);
                        M.setOwner(entityToSpawn, shooter);
                        M.setBaseDamage(entityToSpawn, damage);
                        M.setKnockback(entityToSpawn, knockback);
                        M.setSilent(entityToSpawn, true);
                        return entityToSpawn;
                    }
                }).getArrow(projectileLevel, entity, 2.5F, 1);
                M.setPos(_entityToSpawn, M.getX(entity), M.getEyeY(entity) - 0.1, M.getZ(entity));
                M.shoot(_entityToSpawn, M.getLookAngle(entity).x, M.getLookAngle(entity).y, M.getLookAngle(entity).z, 3.0F, 10.0F);
                M.addFreshEntity(projectileLevel, _entityToSpawn);
            }

            projectileLevel = M.level(entity);
            if (!M.isClientSide(projectileLevel)) {
                Entity _entityToSpawn = (new Object() {
                    public Entity getArrow(World level, Entity shooter, float damage, int knockback) {
                        BohAbstractArrow entityToSpawn = new ShotgunProjectileEntity(BohModEntities.SHOTGUN_PROJECTILE.get(), level);
                        M.setOwner(entityToSpawn, shooter);
                        M.setBaseDamage(entityToSpawn, damage);
                        M.setKnockback(entityToSpawn, knockback);
                        M.setSilent(entityToSpawn, true);
                        return entityToSpawn;
                    }
                }).getArrow(projectileLevel, entity, 2.5F, 1);
                M.setPos(_entityToSpawn, M.getX(entity), M.getEyeY(entity) - 0.1, M.getZ(entity));
                M.shoot(_entityToSpawn, M.getLookAngle(entity).x, M.getLookAngle(entity).y, M.getLookAngle(entity).z, 3.0F, 10.0F);
                M.addFreshEntity(projectileLevel, _entityToSpawn);
            }

            if (M.getItem(itemstack) instanceof BoomStickItem) {
                M.putString(M.getOrCreateTag(itemstack), "geckoAnim", "shoot");
            }

            if (!M.isClientSide(world)) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:shotgun_shoot")),
                            SoundSource.AMBIENT,
                            1.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world,
                            x,
                            y,
                            z,
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:shotgun_shoot")),
                            SoundSource.AMBIENT,
                            1.0F,
                            1.0F,
                            false
                        );
                    }
                }

                BohMod.queueServerWork(
                    34,
                    () -> {
                        if (world instanceof World) {
                            if (!M.isClientSide(world)) {
                                M.playSound(
                                    world,
                                    null,
                                    BlockPos.containing(x, y, z),
                                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:shotgun_cocking")),
                                    SoundSource.AMBIENT,
                                    1.0F,
                                    1.0F
                                );
                            } else {
                                M.playLocalSound(
                                    world,
                                    x,
                                    y,
                                    z,
                                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:shotgun_cocking")),
                                    SoundSource.AMBIENT,
                                    1.0F,
                                    1.0F,
                                    false
                                );
                            }
                        }
                    }
                );
            }

            M.setDeltaMovement(
                entity,
                new Vec3(
                    M.getDeltaMovement(entity).x() - M.getLookAngle(entity).x * 1.0,
                    M.getDeltaMovement(entity).y(),
                    M.getDeltaMovement(entity).z() - M.getLookAngle(entity).z * 1.0
                )
            );
            M.setYRot(entity, M.getYRot(entity));
            M.setXRot(entity, M.getXRot(entity) - 15.0F);
            M.setYBodyRot(entity, M.getYRot(entity));
            M.setYHeadRot(entity, M.getYRot(entity));
            M.set_yRotO(entity, M.getYRot(entity));
            M.set_xRotO(entity, M.getXRot(entity));
            if (entity instanceof EntityLivingBase _entity) {
                M.set_yBodyRotO(_entity, M.getYRot(_entity));
                M.set_yHeadRotO(_entity, M.getYRot(_entity));
            }
        }
    }
}
