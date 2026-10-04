package net.mcreator.boh.compat.forge.common.brewing;

import net.mcreator.boh.compat.M;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.brewing.PotionBrewEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * Applies {@link BrewingRecipeRegistry} recipes in the 1.7.10 brewing stand, which has no recipe API.
 * Recipe ingredients report themselves as potion ingredients (see BohItem) with the effect string "+4", which turns
 * water into an awkward potion, so the stand accepts them and starts brewing; when the brew finishes, the vanilla
 * result is cancelled here and the mod's recipes produce the potions instead.
 */
public final class BrewingHooks {

    /** Potion effect string given to recipe ingredients: enough for TileEntityBrewingStand.canBrew to start. */
    public static final String INGREDIENT_EFFECT = "+4";

    private BrewingHooks() {}

    public static void install() {
        MinecraftForge.EVENT_BUS.register(new BrewingHooks());
    }

    public static boolean isIngredient(ItemStack stack) {
        if (stack == null) return false;
        for (IBrewingRecipe r : BrewingRecipeRegistry.RECIPES) if (r.isIngredient(stack)) return true;
        return false;
    }

    @SubscribeEvent
    public void onBrew(PotionBrewEvent.Pre e) {
        ItemStack ingredient = e.getItem(3);
        if (!isIngredient(ingredient)) return;
        // never let vanilla apply the placeholder "+4" to anything
        e.setCanceled(true);
        boolean brewed = false;
        for (int i = 0; i < 3; i++) {
            ItemStack in = e.getItem(i);
            if (in == null) continue;
            for (IBrewingRecipe r : BrewingRecipeRegistry.RECIPES) {
                if (!r.isInput(in) || !r.isIngredient(ingredient)) continue;
                ItemStack out = M.legacy(r.getOutput(in, ingredient));
                if (out == null) continue;
                // 1.20 recipes copy the input item (potion / splash / lingering); in 1.7.10 splash is a metadata bit
                if (in.getItem() == Items.potionitem && out.getItem() == Items.potionitem && ItemPotion.isSplash(in.getItemDamage()))
                    out.setItemDamage(out.getItemDamage() | 16384);
                e.setItem(i, out);
                brewed = true;
                break;
            }
        }
        if (!brewed) return;
        if (ingredient.getItem().hasContainerItem(ingredient)) {
            e.setItem(3, ingredient.getItem().getContainerItem(ingredient));
        } else if (--ingredient.stackSize <= 0) {
            e.setItem(3, null);
        }
    }
}
