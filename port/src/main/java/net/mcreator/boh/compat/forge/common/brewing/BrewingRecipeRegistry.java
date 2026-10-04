package net.mcreator.boh.compat.forge.common.brewing;

import java.util.ArrayList;
import java.util.List;

/** Forge BrewingRecipeRegistry; the recipes are applied by compat BrewingHooks. */
public final class BrewingRecipeRegistry {

    public static final List<IBrewingRecipe> RECIPES = new ArrayList<>();

    private BrewingRecipeRegistry() {}

    public static boolean addRecipe(IBrewingRecipe r) {
        return RECIPES.add(r);
    }
}
