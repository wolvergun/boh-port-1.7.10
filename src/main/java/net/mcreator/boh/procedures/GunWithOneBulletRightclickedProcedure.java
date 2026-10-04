package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.entity.BohAbstractArrow;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.entity.WFPistolProjectileEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.item.GunWithOneBulletItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GunWithOneBulletRightclickedProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (entity instanceof EntityPlayer _player) {
                M.addCooldown(M.getCooldowns(_player), M.getItem(itemstack), 1000);
            }

            if (M.hurt(itemstack, 1, RandomSource.create(), null)) {
                M.shrink(itemstack, 1);
                M.setDamageValue(itemstack, 0);
            }

            World projectileLevel = M.level(entity);
            if (!M.isClientSide(projectileLevel)) {
                Entity _entityToSpawn = (new Object() {
                    public Entity getArrow(World level, Entity shooter, float damage, int knockback) {
                        BohAbstractArrow entityToSpawn = new WFPistolProjectileEntity(BohModEntities.WF_PISTOL_PROJECTILE.get(), level);
                        M.setOwner(entityToSpawn, shooter);
                        M.setBaseDamage(entityToSpawn, damage);
                        M.setKnockback(entityToSpawn, knockback);
                        M.setSilent(entityToSpawn, true);
                        return entityToSpawn;
                    }
                }).getArrow(projectileLevel, entity, 10.0F, 0);
                M.setPos(_entityToSpawn, M.getX(entity), M.getEyeY(entity) - 0.1, M.getZ(entity));
                M.shoot(_entityToSpawn, M.getLookAngle(entity).x, M.getLookAngle(entity).y, M.getLookAngle(entity).z, 6.0F, 0.0F);
                M.addFreshEntity(projectileLevel, _entityToSpawn);
            }

            if (M.getItem(itemstack) instanceof GunWithOneBulletItem) {
                M.putString(M.getOrCreateTag(itemstack), "geckoAnim", "shoot");
            }

            if (world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:agwob_shoot")),
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F
                    );
                } else {
                    M.playLocalSound(
                        world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:agwob_shoot")), SoundSource.PLAYERS, 1.0F, 1.0F, false
                    );
                }
            }
        }
    }
}
