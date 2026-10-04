package net.mcreator.boh.compat.mc.world.food;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.potion.PotionEffect;

/** 1.20 FoodProperties.Builder. */
public class Builder {

    private int nutrition;
    private float saturation;
    private boolean meat, alwaysEat, fast;
    private final List<Object[]> effects = new ArrayList<>();

    public Builder nutrition(int n) {
        nutrition = n;
        return this;
    }

    public Builder saturationMod(float s) {
        saturation = s;
        return this;
    }

    public Builder meat() {
        meat = true;
        return this;
    }

    public Builder alwaysEat() {
        alwaysEat = true;
        return this;
    }

    public Builder fast() {
        fast = true;
        return this;
    }

    public Builder effect(Supplier<PotionEffect> effect, float chance) {
        effects.add(new Object[] { effect, chance });
        return this;
    }

    public Builder effect(PotionEffect effect, float chance) {
        effects.add(new Object[] { (Supplier<PotionEffect>) () -> new PotionEffect(effect), chance });
        return this;
    }

    public FoodProperties build() {
        return new FoodProperties(nutrition, saturation, meat, alwaysEat, fast, effects);
    }
}
