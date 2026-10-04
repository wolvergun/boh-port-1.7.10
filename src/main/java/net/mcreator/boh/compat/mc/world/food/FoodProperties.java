package net.mcreator.boh.compat.mc.world.food;

import java.util.List;

public class FoodProperties {
    final int nutrition;
    final float saturationModifier;
    final boolean isMeat;
    final boolean canAlwaysEat;
    final boolean fastFood;
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
        return this.nutrition;
    }

    public float getSaturationModifier() {
        return this.saturationModifier;
    }

    public boolean isMeat() {
        return this.isMeat;
    }

    public boolean canAlwaysEat() {
        return this.canAlwaysEat;
    }

    public boolean isFastFood() {
        return this.fastFood;
    }

    public List<Object[]> getEffects() {
        return this.effects;
    }

    public static FoodProperties.Builder builder() {
        return new FoodProperties.Builder();
    }

    public static class Builder extends net.mcreator.boh.compat.mc.world.food.Builder {
    }
}
