package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class BaldiRulerToolInHandTickProcedure {
    public static void execute(Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (M.getBoolean(M.getOrCreateTag(itemstack), "ruler_clap")) {
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SPEED, 20, 2, false, false));
                }

                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.DAMAGE_BOOST, 20, 2, false, false));
                }

                if (entity instanceof EntityLivingBase _entity) {
                    M.removeEffect(_entity, MobEffects.MOVEMENT_SLOWDOWN);
                }

                if (entity instanceof EntityLivingBase _entity) {
                    M.removeEffect(_entity, MobEffects.WEAKNESS);
                }
            }

            if (!M.getBoolean(M.getOrCreateTag(itemstack), "ruler_clap")) {
                M.makeStuckInBlock(entity, M.defaultBlockState(Blocks.AIR), new Vec3(0.25, 0.05, 0.25));
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 255, false, false));
                }

                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.WEAKNESS, 20, 255, false, false));
                }
            }

            if (!(entity instanceof EntityPlayer _plrCldCheck12 && M.isOnCooldown(M.getCooldowns(_plrCldCheck12), M.getItem(itemstack)))) {
                M.putBoolean(M.getOrCreateTag(itemstack), "ruler_clap", false);
            }
        }
    }
}
