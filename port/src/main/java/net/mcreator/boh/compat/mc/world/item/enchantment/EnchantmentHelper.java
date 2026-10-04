package net.mcreator.boh.compat.mc.world.item.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

public final class EnchantmentHelper {

    private EnchantmentHelper() {}

    public static int getItemEnchantmentLevel(Enchantment e, ItemStack stack) {
        return stack == null || stack.getItem() == null ? 0 : net.minecraft.enchantment.EnchantmentHelper.getEnchantmentLevel(e.effectId, stack);
    }

    public static int getTagEnchantmentLevel(Enchantment e, ItemStack stack) {
        return getItemEnchantmentLevel(e, stack);
    }

    public static int getMobLooting(net.minecraft.entity.EntityLivingBase e) {
        return net.minecraft.enchantment.EnchantmentHelper.getLootingModifier(e);
    }
}
