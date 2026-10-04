package net.mcreator.boh.compat.forge.common.crafting;

import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;

public final class CompoundIngredient {
    private CompoundIngredient() {
    }

    public static Ingredient of(Ingredient... parts) {
        return Ingredient.union(parts);
    }
}
