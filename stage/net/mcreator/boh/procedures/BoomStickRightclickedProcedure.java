package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.ShotgunProjectileEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.item.BoomStickItem;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.mcreator.boh.compat.entity.BohAbstractArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class BoomStickRightclickedProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            ItemStack _ist = itemstack;
            if (M.hurt(_ist, 1, RandomSource.create(), null)) {
                M.shrink(_ist, 1);
                M.setDamageValue(_ist, 0);
            }
            if (entity instanceof EntityPlayer _player) {
                M.addCooldown(M.getCooldowns(_player), M.getItem(itemstack), 34);
            }
            Entity _shootFrom = entity;
            World projectileLevel = M.level(_shootFrom);
            if (!M.isClientSide(projectileLevel)) {
                Entity _entityToSpawn = (new Object() {

                    public Entity getArrow(World level, Entity shooter, float damage, int knockback) {
                        BohAbstractArrow entityToSpawn = new ShotgunProjectileEntity((EntityType<? extends ShotgunProjectileEntity>) BohModEntities.SHOTGUN_PROJECTILE.get(), level);
                        M.setOwner(entityToSpawn, shooter);
                        M.setBaseDamage(entityToSpawn, damage);
                        M.setKnockback(entityToSpawn, knockback);
                        M.setSilent(entityToSpawn, true);
                        return entityToSpawn;
                    }
                }).getArrow(projectileLevel, entity, 2.5F, 1);
                M.setPos(_entityToSpawn, M.getX(_shootFrom), M.getEyeY(_shootFrom) - 0.1, M.getZ(_shootFrom));
                M.shoot(_entityToSpawn, M.getLookAngle(_shootFrom).x, M.getLookAngle(_shootFrom).y, M.getLookAngle(_shootFrom).z, 3.0F, 10.0F);
                M.addFreshEntity(projectileLevel, _entityToSpawn);
            }
            Entity _shootFromx = entity;
            projectileLevel = M.level(_shootFromx);
            if (!M.isClientSide(projectileLevel)) {
                Entity _entityToSpawn = (new Object() {

                    public Entity getArrow(World level, Entity shooter, float damage, int knockback) {
                        BohAbstractArrow entityToSpawn = new ShotgunProjectileEntity((EntityType<? extends ShotgunProjectileEntity>) BohModEntities.SHOTGUN_PROJECTILE.get(), level);
                        M.setOwner(entityToSpawn, shooter);
                        M.setBaseDamage(entityToSpawn, damage);
                        M.setKnockback(entityToSpawn, knockback);
                        M.setSilent(entityToSpawn, true);
                        return entityToSpawn;
                    }
                }).getArrow(projectileLevel, entity, 2.5F, 1);
                M.setPos(_entityToSpawn, M.getX(_shootFromx), M.getEyeY(_shootFromx) - 0.1, M.getZ(_shootFromx));
                M.shoot(_entityToSpawn, M.getLookAngle(_shootFromx).x, M.getLookAngle(_shootFromx).y, M.getLookAngle(_shootFromx).z, 3.0F, 10.0F);
                M.addFreshEntity(projectileLevel, _entityToSpawn);
            }
            Entity _shootFromxx = entity;
            projectileLevel = M.level(_shootFromxx);
            if (!M.isClientSide(projectileLevel)) {
                Entity _entityToSpawn = (new Object() {

                    public Entity getArrow(World level, Entity shooter, float damage, int knockback) {
                        BohAbstractArrow entityToSpawn = new ShotgunProjectileEntity((EntityType<? extends ShotgunProjectileEntity>) BohModEntities.SHOTGUN_PROJECTILE.get(), level);
                        M.setOwner(entityToSpawn, shooter);
                        M.setBaseDamage(entityToSpawn, damage);
                        M.setKnockback(entityToSpawn, knockback);
                        M.setSilent(entityToSpawn, true);
                        return entityToSpawn;
                    }
                }).getArrow(projectileLevel, entity, 2.5F, 1);
                M.setPos(_entityToSpawn, M.getX(_shootFromxx), M.getEyeY(_shootFromxx) - 0.1, M.getZ(_shootFromxx));
                M.shoot(_entityToSpawn, M.getLookAngle(_shootFromxx).x, M.getLookAngle(_shootFromxx).y, M.getLookAngle(_shootFromxx).z, 3.0F, 10.0F);
                M.addFreshEntity(projectileLevel, _entityToSpawn);
            }
            Entity _shootFromxxx = entity;
            projectileLevel = M.level(_shootFromxxx);
            if (!M.isClientSide(projectileLevel)) {
                Entity _entityToSpawn = (new Object() {

                    public Entity getArrow(World level, Entity shooter, float damage, int knockback) {
                        BohAbstractArrow entityToSpawn = new ShotgunProjectileEntity((EntityType<? extends ShotgunProjectileEntity>) BohModEntities.SHOTGUN_PROJECTILE.get(), level);
                        M.setOwner(entityToSpawn, shooter);
                        M.setBaseDamage(entityToSpawn, damage);
                        M.setKnockback(entityToSpawn, knockback);
                        M.setSilent(entityToSpawn, true);
                        return entityToSpawn;
                    }
                }).getArrow(projectileLevel, entity, 2.5F, 1);
                M.setPos(_entityToSpawn, M.getX(_shootFromxxx), M.getEyeY(_shootFromxxx) - 0.1, M.getZ(_shootFromxxx));
                M.shoot(_entityToSpawn, M.getLookAngle(_shootFromxxx).x, M.getLookAngle(_shootFromxxx).y, M.getLookAngle(_shootFromxxx).z, 3.0F, 10.0F);
                M.addFreshEntity(projectileLevel, _entityToSpawn);
            }
            Entity _shootFromxxxx = entity;
            projectileLevel = M.level(_shootFromxxxx);
            if (!M.isClientSide(projectileLevel)) {
                Entity _entityToSpawn = (new Object() {

                    public Entity getArrow(World level, Entity shooter, float damage, int knockback) {
                        BohAbstractArrow entityToSpawn = new ShotgunProjectileEntity((EntityType<? extends ShotgunProjectileEntity>) BohModEntities.SHOTGUN_PROJECTILE.get(), level);
                        M.setOwner(entityToSpawn, shooter);
                        M.setBaseDamage(entityToSpawn, damage);
                        M.setKnockback(entityToSpawn, knockback);
                        M.setSilent(entityToSpawn, true);
                        return entityToSpawn;
                    }
                }).getArrow(projectileLevel, entity, 2.5F, 1);
                M.setPos(_entityToSpawn, M.getX(_shootFromxxxx), M.getEyeY(_shootFromxxxx) - 0.1, M.getZ(_shootFromxxxx));
                M.shoot(_entityToSpawn, M.getLookAngle(_shootFromxxxx).x, M.getLookAngle(_shootFromxxxx).y, M.getLookAngle(_shootFromxxxx).z, 3.0F, 10.0F);
                M.addFreshEntity(projectileLevel, _entityToSpawn);
            }
            Entity _shootFromxxxxx = entity;
            projectileLevel = M.level(_shootFromxxxxx);
            if (!M.isClientSide(projectileLevel)) {
                Entity _entityToSpawn = (new Object() {

                    public Entity getArrow(World level, Entity shooter, float damage, int knockback) {
                        BohAbstractArrow entityToSpawn = new ShotgunProjectileEntity((EntityType<? extends ShotgunProjectileEntity>) BohModEntities.SHOTGUN_PROJECTILE.get(), level);
                        M.setOwner(entityToSpawn, shooter);
                        M.setBaseDamage(entityToSpawn, damage);
                        M.setKnockback(entityToSpawn, knockback);
                        M.setSilent(entityToSpawn, true);
                        return entityToSpawn;
                    }
                }).getArrow(projectileLevel, entity, 2.5F, 1);
                M.setPos(_entityToSpawn, M.getX(_shootFromxxxxx), M.getEyeY(_shootFromxxxxx) - 0.1, M.getZ(_shootFromxxxxx));
                M.shoot(_entityToSpawn, M.getLookAngle(_shootFromxxxxx).x, M.getLookAngle(_shootFromxxxxx).y, M.getLookAngle(_shootFromxxxxx).z, 3.0F, 10.0F);
                M.addFreshEntity(projectileLevel, _entityToSpawn);
            }
            if (M.getItem(itemstack) instanceof BoomStickItem) {
                M.putString(M.getOrCreateTag(itemstack), "geckoAnim", "shoot");
            }
            if (!M.isClientSide(world)) {
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:shotgun_shoot")), SoundSource.AMBIENT, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:shotgun_shoot")), SoundSource.AMBIENT, 1.0F, 1.0F, false);
                    }
                }
                BohMod.queueServerWork(34, () -> {
                    if (world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:shotgun_cocking")), SoundSource.AMBIENT, 1.0F, 1.0F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:shotgun_cocking")), SoundSource.AMBIENT, 1.0F, 1.0F, false);
                        }
                    }
                });
            }
            M.setDeltaMovement(entity, new Vec3(M.getDeltaMovement(entity).x() - M.getLookAngle(entity).x * 1.0, M.getDeltaMovement(entity).y(), M.getDeltaMovement(entity).z() - M.getLookAngle(entity).z * 1.0));
            Entity _ent = entity;
            M.setYRot(_ent, M.getYRot(entity));
            M.setXRot(_ent, M.getXRot(entity) - 15.0F);
            M.setYBodyRot(_ent, M.getYRot(_ent));
            M.setYHeadRot(_ent, M.getYRot(_ent));
            M.set_yRotO(_ent, M.getYRot(_ent));
            M.set_xRotO(_ent, M.getXRot(_ent));
            if (_ent instanceof EntityLivingBase _entity) {
                M.set_yBodyRotO(_entity, M.getYRot(_entity));
                M.set_yHeadRotO(_entity, M.getYRot(_entity));
            }
        }
    }
}
