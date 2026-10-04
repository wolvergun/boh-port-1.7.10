package net.mcreator.boh.compat.mc.world.food;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.potion.PotionEffect;

/** 1.20 FoodProperties; consumed by the compat BohItem. */
public class FoodProperties {

    final int nutrition;
    final float saturationModifier;
    final boolean isMeat, canAlwaysEat, fastFood;
    final List<Object[]> effects;

    FoodProperties(int nutrition, float sat, boolean meat, boolean always, boolean fast, List<Object[]> effects) {
        this.nutrition = nutrition;
        this.saturationModifier = sat;
        this.isMeat = meat;
        this.canAlwaysEat = always;
        this.fastFood = fast;
        this.effects = effects;
    }

    public int getNutrition() {
        return nutrition;
    }

    public float getSaturationModifier() {
        return saturationModifier;
    }

    public boolean isMeat() {
        return isMeat;
    }

    public boolean canAlwaysEat() {
        return canAlwaysEat;
    }

    public boolean isFastFood() {
        return fastFood;
    }

    /** (Supplier of PotionEffect, chance) pairs. */
    @SuppressWarnings("unchecked")
    public List<Object[]> getEffects() {
        return effects;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends net.mcreator.boh.compat.mc.world.food.Builder {}
}
