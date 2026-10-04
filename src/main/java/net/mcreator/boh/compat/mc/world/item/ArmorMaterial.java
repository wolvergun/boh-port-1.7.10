package net.mcreator.boh.compat.mc.world.item;

import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;

public interface ArmorMaterial {
    int getDurabilityForType(Type var1);

    int getDefenseForType(Type var1);

    int getEnchantmentValue();

    SoundEvent getEquipSound();

    Ingredient getRepairIngredient();

    String getName();

    float getToughness();

    float getKnockbackResistance();
}
