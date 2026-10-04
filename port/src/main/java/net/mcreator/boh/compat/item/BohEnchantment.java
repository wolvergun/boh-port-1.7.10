package net.mcreator.boh.compat.item;

import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.mcreator.boh.compat.mc.world.item.enchantment.EnchantmentCategory;
import net.mcreator.boh.compat.mc.world.item.enchantment.Rarity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

/** Compat base for mod enchantments (1.20 Enchantment hooks on a 1.7.10 {@link Enchantment}). */
public class BohEnchantment extends Enchantment {

    private static BohEnchantment binding;
    private final EnchantmentCategory category;

    public BohEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
        super(nextId(), rarity.weight, category.legacy);
        this.category = category;
    }

    private static int nextId() {
        for (int i = 100; i < Enchantment.enchantmentsList.length; i++) if (Enchantment.enchantmentsList[i] == null) return i;
        for (int i = 0; i < Enchantment.enchantmentsList.length; i++) if (Enchantment.enchantmentsList[i] == null) return i;
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

    // 1.20 hooks

    public int getMaxLevel() {
        return 1;
    }

    public int getMinCost(int level) {
        return 1 + level * 10;
    }

    public int getMaxCost(int level) {
        return getMinCost(level) + 5;
    }

    protected boolean checkCompatibility(Enchantment other) {
        return this != other;
    }

    public boolean canEnchant(ItemStack stack) {
        return stack != null && category.canEnchant(stack.getItem());
    }

    public void doPostAttack(EntityLivingBase attacker, Entity target, int level) {}

    public void doPostHurt(EntityLivingBase target, Entity attacker, int level) {}

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

    // 1.7.10 bridge

    @Override
    public int getMinEnchantability(int level) {
        return getMinCost(level);
    }

    @Override
    public int getMaxEnchantability(int level) {
        return getMaxCost(level);
    }

    @Override
    public boolean canApplyTogether(Enchantment other) {
        return checkCompatibility(other);
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return canEnchant(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return !isTreasureOnly() && canEnchant(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return isDiscoverable();
    }

    @Override
    public void func_151368_a(EntityLivingBase attacker, Entity target, int level) {
        doPostAttack(attacker, target, level);
    }

    @Override
    public void func_151367_b(EntityLivingBase target, Entity attacker, int level) {
        doPostHurt(target, attacker, level);
    }
}
