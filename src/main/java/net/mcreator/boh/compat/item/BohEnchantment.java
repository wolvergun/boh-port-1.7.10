package net.mcreator.boh.compat.item;

import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.mcreator.boh.compat.mc.world.item.enchantment.EnchantmentCategory;
import net.mcreator.boh.compat.mc.world.item.enchantment.Rarity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class BohEnchantment extends Enchantment {
    private static BohEnchantment binding;
    private final EnchantmentCategory category;

    public BohEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
        super(nextId(), rarity.weight, category.legacy);
        this.category = category;
    }

    private static int nextId() {
        for (int i = 100; i < Enchantment.enchantmentsList.length; i++) {
            if (Enchantment.enchantmentsList[i] == null) {
                return i;
            }
        }

        for (int ix = 0; ix < Enchantment.enchantmentsList.length; ix++) {
            if (Enchantment.enchantmentsList[ix] == null) {
                return ix;
            }
        }

        throw new IllegalStateException("No free enchantment ids");
    }

    static synchronized Enchantment bindingCurseInternal() {
        if (binding == null) {
            binding = new BohEnchantment(Rarity.VERY_RARE, EnchantmentCategory.ARMOR);
            binding.setName("boh.binding_curse");
        }

        return binding;
    }

    public static Enchantment bindingCurse() {
        return bindingCurseInternal();
    }

    public int getMaxLevel() {
        return 1;
    }

    public int getMinCost(int level) {
        return 1 + level * 10;
    }

    public int getMaxCost(int level) {
        return this.getMinCost(level) + 5;
    }

    protected boolean checkCompatibility(Enchantment other) {
        return this != other;
    }

    public boolean canEnchant(ItemStack stack) {
        return stack != null && this.category.canEnchant(stack.getItem());
    }

    public void doPostAttack(EntityLivingBase attacker, Entity target, int level) {
    }

    public void doPostHurt(EntityLivingBase target, Entity attacker, int level) {
    }

    public boolean isTreasureOnly() {
        return false;
    }

    public boolean isCurse() {
        return false;
    }

    public boolean isTradeable() {
        return true;
    }

    public boolean isDiscoverable() {
        return true;
    }

    public int getMinEnchantability(int level) {
        return this.getMinCost(level);
    }

    public int getMaxEnchantability(int level) {
        return this.getMaxCost(level);
    }

    public boolean canApplyTogether(Enchantment other) {
        return this.checkCompatibility(other);
    }

    public boolean canApply(ItemStack stack) {
        return this.canEnchant(stack);
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return !this.isTreasureOnly() && this.canEnchant(stack);
    }

    public boolean isAllowedOnBooks() {
        return this.isDiscoverable();
    }

    public void func_151368_a(EntityLivingBase attacker, Entity target, int level) {
        this.doPostAttack(attacker, target, level);
    }

    public void func_151367_b(EntityLivingBase target, Entity attacker, int level) {
        this.doPostHurt(target, attacker, level);
    }
}
