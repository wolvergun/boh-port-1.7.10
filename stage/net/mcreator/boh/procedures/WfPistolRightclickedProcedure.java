package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.WFPistolProjectileEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.item.WfPistolItem;
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
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class WfPistolRightclickedProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            ItemStack _ist = itemstack;
            if (M.hurt(_ist, 1, RandomSource.create(), null)) {
                M.shrink(_ist, 1);
                M.setDamageValue(_ist, 0);
            }
            if (entity instanceof EntityPlayer _player) {
                M.addCooldown(M.getCooldowns(_player), M.getItem(itemstack), 20);
            }
            Entity _shootFrom = entity;
            World projectileLevel = M.level(_shootFrom);
            if (!M.isClientSide(projectileLevel)) {
                Entity _entityToSpawn = (new Object() {

                    public Entity getArrow(World level, Entity shooter, float damage, int knockback) {
                        BohAbstractArrow entityToSpawn = new WFPistolProjectileEntity((EntityType<? extends WFPistolProjectileEntity>) BohModEntities.WF_PISTOL_PROJECTILE.get(), level);
                        M.setOwner(entityToSpawn, shooter);
                        M.setBaseDamage(entityToSpawn, damage);
                        M.setKnockback(entityToSpawn, knockback);
                        M.setSilent(entityToSpawn, true);
                        return entityToSpawn;
                    }
                }).getArrow(projectileLevel, entity, 2.5F, 0);
                M.setPos(_entityToSpawn, M.getX(_shootFrom), M.getEyeY(_shootFrom) - 0.1, M.getZ(_shootFrom));
                M.shoot(_entityToSpawn, M.getLookAngle(_shootFrom).x, M.getLookAngle(_shootFrom).y, M.getLookAngle(_shootFrom).z, 3.0F, 0.0F);
                M.addFreshEntity(projectileLevel, _entityToSpawn);
            }
            if (M.getItem(itemstack) instanceof WfPistolItem) {
                M.putString(M.getOrCreateTag(itemstack), "geckoAnim", "shoot");
            }
            if (!M.isClientSide(world) && world instanceof World _level) {
                if (!M.isClientSide(_level)) {
                    M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:wf_pistol")), SoundSource.AMBIENT, 1.0F, 1.0F);
                } else {
                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:wf_pistol")), SoundSource.AMBIENT, 1.0F, 1.0F, false);
                }
            }
            Entity _ent = entity;
            M.setYRot(_ent, M.getYRot(entity));
            M.setXRot(_ent, M.getXRot(entity) - 5.0F);
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
