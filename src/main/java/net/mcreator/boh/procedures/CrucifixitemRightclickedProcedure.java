package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.entity.BohAbstractArrow;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.entity.CrucifixProjectileEntity;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class CrucifixitemRightclickedProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (M.getItem(itemstack) == M.getItem(entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY)) {
                if (entity instanceof EntityLivingBase _entity) {
                    M.swing(_entity, InteractionHand.MAIN_HAND, true);
                }
            } else if (M.getItem(itemstack) == M.getItem(entity instanceof EntityLivingBase _livEntx ? M.getOffhandItem(_livEntx) : M.EMPTY)
                && entity instanceof EntityLivingBase _entity) {
                M.swing(_entity, InteractionHand.OFF_HAND, true);
            }

            World projectileLevel = M.level(entity);
            if (!M.isClientSide(projectileLevel)) {
                Entity _entityToSpawn = (new Object() {
                    public Entity getArrow(World level, Entity shooter, float damage, int knockback) {
                        BohAbstractArrow entityToSpawn = new CrucifixProjectileEntity(BohModEntities.CRUCIFIX_PROJECTILE.get(), level);
                        M.setOwner(entityToSpawn, shooter);
                        M.setBaseDamage(entityToSpawn, damage);
                        M.setKnockback(entityToSpawn, knockback);
                        M.setSilent(entityToSpawn, true);
                        return entityToSpawn;
                    }
                }).getArrow(projectileLevel, entity, 0.0F, 0);
                M.setPos(_entityToSpawn, M.getX(entity), M.getEyeY(entity) - 0.1, M.getZ(entity));
                M.shoot(_entityToSpawn, M.getLookAngle(entity).x, M.getLookAngle(entity).y, M.getLookAngle(entity).z, 2.0F, 0.0F);
                M.addFreshEntity(projectileLevel, _entityToSpawn);
            }

            if (world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.note_block.chime")),
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
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.note_block.chime")),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F,
                        false
                    );
                }
            }

            if (M.hurt(itemstack, 1, RandomSource.create(), null)) {
                M.shrink(itemstack, 1);
                M.setDamageValue(itemstack, 0);
            }

            if (entity instanceof EntityPlayer _player) {
                M.addCooldown(M.getCooldowns(_player), M.getItem(itemstack), 15);
            }
        }
    }
}
