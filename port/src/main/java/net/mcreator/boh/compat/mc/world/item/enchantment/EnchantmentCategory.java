package net.mcreator.boh.compat.mc.world.item.enchantment;

import java.util.function.Predicate;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.Item;

/** 1.20 EnchantmentCategory: a predicate over items (custom ones map onto EnumEnchantmentType.all). */
public class EnchantmentCategory {

    public static final EnchantmentCategory WEAPON = new EnchantmentCategory(EnumEnchantmentType.weapon, null);
    public static final EnchantmentCategory DIGGER = new EnchantmentCategory(EnumEnchantmentType.digger, null);
    public static final EnchantmentCategory BREAKABLE = new EnchantmentCategory(EnumEnchantmentType.breakable, null);
    public static final EnchantmentCategory ARMOR = new EnchantmentCategory(EnumEnchantmentType.armor, null);
    public static final EnchantmentCategory BOW = new EnchantmentCategory(EnumEnchantmentType.bow, null);

    public final EnumEnchantmentType legacy;
    public final Predicate<Item> predicate;

    private EnchantmentCategory(EnumEnchantmentType legacy, Predicate<Item> predicate) {
        this.legacy = legacy;
        this.predicate = predicate;
    }

    public static EnchantmentCategory create(String name, Predicate<Item> predicate) {
        return new EnchantmentCategory(EnumEnchantmentType.all, predicate);
    }

    public boolean canEnchant(Item item) {
        return predicate != null ? predicate.test(item) : legacy.canEnchantItem(item);
    }
}
