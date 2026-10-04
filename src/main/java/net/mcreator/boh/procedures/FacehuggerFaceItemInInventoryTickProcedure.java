package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.item.enchantment.EnchantmentHelper;
import net.mcreator.boh.compat.mc.world.item.enchantment.Enchantments;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FacehuggerFaceItemInInventoryTickProcedure {
    public static void execute(World world, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BINDING_CURSE, itemstack) == 0) {
                M.enchant(itemstack, Enchantments.BINDING_CURSE, 1);
            }

            if (!(entity instanceof EntityLivingBase _livEnt4 && M.hasEffect(_livEnt4, BohModMobEffects.FACE_HUGGER_EFFECT.get()))
                && entity instanceof EntityLivingBase _entity
                && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(BohModMobEffects.FACE_HUGGER_EFFECT.get(), 6000, 0, false, false));
            }

            if (!(entity instanceof EntityLivingBase _livEnt6 && M.hasEffect(_livEnt6, BohModMobEffects.TIMER_OVERLAY.get()))
                && entity instanceof EntityLivingBase _entity
                && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(BohModMobEffects.TIMER_OVERLAY.get(), 1000, 0, false, false));
            }
        }
    }
}
