package net.mcreator.boh.recipes.brewing;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.common.brewing.BrewingRecipeRegistry;
import net.mcreator.boh.compat.forge.common.brewing.IBrewingRecipe;
import net.mcreator.boh.compat.forge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.mcreator.boh.compat.mc.world.item.alchemy.PotionUtils;
import net.mcreator.boh.compat.mc.world.item.alchemy.Potions;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;
import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.init.BohModPotions;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BrewVampirismBrewingRecipe implements IBrewingRecipe {
    @SubscribeEvent
    public void init(FMLCommonSetupEvent event) {
        M.enqueueWork(event, () -> BrewingRecipeRegistry.addRecipe(new BrewVampirismBrewingRecipe()));
    }

    @Override
    public boolean isInput(ItemStack input) {
        Item inputItem = M.getItem(input);
        return (inputItem == Items.POTION || inputItem == Items.SPLASH_POTION || inputItem == Items.LINGERING_POTION)
            && PotionUtils.getPotion(input) == Potions.WATER;
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return Ingredient.of(M.new_ItemStack(BohModItems.VAMPIRE_BLOOD.get())).test(ingredient);
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        return this.isInput(input) && this.isIngredient(ingredient)
            ? PotionUtils.setPotion(M.new_ItemStack(M.getItem(input)), BohModPotions.VAMPIRISM_POTIONS.get())
            : M.EMPTY;
    }
}
