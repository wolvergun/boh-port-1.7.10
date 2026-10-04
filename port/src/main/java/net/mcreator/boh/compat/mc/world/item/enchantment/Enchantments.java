package net.mcreator.boh.compat.mc.world.item.enchantment;

import net.minecraft.enchantment.Enchantment;

public final class Enchantments {

    public static final Enchantment SHARPNESS = Enchantment.sharpness;
    public static final Enchantment SMITE = Enchantment.smite;
    public static final Enchantment BANE_OF_ARTHROPODS = Enchantment.baneOfArthropods;
    public static final Enchantment KNOCKBACK = Enchantment.knockback;
    public static final Enchantment FIRE_ASPECT = Enchantment.fireAspect;
    public static final Enchantment MOB_LOOTING = Enchantment.looting;
    public static final Enchantment SWEEPING_EDGE = Enchantment.sharpness;
    public static final Enchantment BLOCK_EFFICIENCY = Enchantment.efficiency;
    public static final Enchantment SILK_TOUCH = Enchantment.silkTouch;
    public static final Enchantment UNBREAKING = Enchantment.unbreaking;
    public static final Enchantment BLOCK_FORTUNE = Enchantment.fortune;
    public static final Enchantment POWER_ARROWS = Enchantment.power;
    public static final Enchantment PUNCH_ARROWS = Enchantment.punch;
    public static final Enchantment FLAMING_ARROWS = Enchantment.flame;
    public static final Enchantment INFINITY_ARROWS = Enchantment.infinity;
    public static final Enchantment ALL_DAMAGE_PROTECTION = Enchantment.protection;
    public static final Enchantment THORNS = Enchantment.thorns;
    /** Curse of binding does not exist in 1.7.10; the port treats it as a marker enchantment id. */
    public static final Enchantment BINDING_CURSE = net.mcreator.boh.compat.item.BohEnchantment.bindingCurse();

    private Enchantments() {}
}
