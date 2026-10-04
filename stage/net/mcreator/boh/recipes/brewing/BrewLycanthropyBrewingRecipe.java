package net.mcreator.boh.recipes.brewing;

import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.init.BohModPotions;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.mcreator.boh.compat.mc.world.item.alchemy.BrewPotion;
import net.mcreator.boh.compat.mc.world.item.alchemy.PotionUtils;
import net.mcreator.boh.compat.mc.world.item.alchemy.Potions;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;
import net.mcreator.boh.compat.forge.common.brewing.BrewingRecipeRegistry;
import net.mcreator.boh.compat.forge.common.brewing.IBrewingRecipe;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.forge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.mcreator.boh.compat.M;

public class BrewLycanthropyBrewingRecipe implements IBrewingRecipe {

    @SubscribeEvent
    public void init(FMLCommonSetupEvent event) {
        M.enqueueWork(event, () -> BrewingRecipeRegistry.addRecipe(new BrewLycanthropyBrewingRecipe()));
    }

    public boolean isInput(ItemStack input) {
        Item inputItem = M.getItem(input);
        return (inputItem == Items.POTION || inputItem == Items.SPLASH_POTION || inputItem == Items.LINGERING_POTION) && PotionUtils.getPotion(input) == Potions.WATER;
    }

    public boolean isIngredient(ItemStack ingredient) {
        return Ingredient.of(new ItemStack[] { M.new_ItemStack(BohModItems.WEREWOLF_TEETH.get()) }).test(ingredient);
    }

    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        return this.isInput(input) && this.isIngredient(ingredient) ? PotionUtils.setPotion(M.new_ItemStack(M.getItem(input)), (BrewPotion) BohModPotions.LYCANTHROPY_POTIONS.get()) : M.EMPTY;
    }
}
