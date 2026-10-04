package net.mcreator.boh.compat.mc.world.item;

import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;

/** 1.20 Tier (tool material). */
public interface Tier {

    int getUses();

    float getSpeed();

    float getAttackDamageBonus();

    int getLevel();

    int getEnchantmentValue();

    Ingredient getRepairIngredient();
}
