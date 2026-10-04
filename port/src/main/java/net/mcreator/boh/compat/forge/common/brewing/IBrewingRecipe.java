package net.mcreator.boh.compat.forge.common.brewing;

import net.minecraft.item.ItemStack;

/** Forge IBrewingRecipe. */
public interface IBrewingRecipe {

    boolean isInput(ItemStack input);

    boolean isIngredient(ItemStack ingredient);

    ItemStack getOutput(ItemStack input, ItemStack ingredient);
}
