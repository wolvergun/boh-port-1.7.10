package net.mcreator.boh.compat.mc.world.item;

import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;

/** 1.20 ArmorMaterial. */
public interface ArmorMaterial {

    int getDurabilityForType(Type type);

    int getDefenseForType(Type type);

    int getEnchantmentValue();

    SoundEvent getEquipSound();

    Ingredient getRepairIngredient();

    String getName();

    float getToughness();

    float getKnockbackResistance();
}
