package net.mcreator.boh.compat.mc.world.food;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.potion.PotionEffect;

public class Builder {
    private int nutrition;
    private float saturation;
    private boolean meat;
    private boolean alwaysEat;
    private boolean fast;
    private final List<Object[]> effects = new ArrayList<>();

    public Builder nutrition(int n) {
        this.nutrition = n;
        return this;
    }

    public Builder saturationMod(float s) {
        this.saturation = s;
        return this;
    }

    public Builder meat() {
        this.meat = true;
        return this;
    }

    public Builder alwaysEat() {
        this.alwaysEat = true;
        return this;
    }

    public Builder fast() {
        this.fast = true;
        return this;
    }

    public Builder effect(Supplier<PotionEffect> effect, float chance) {
        this.effects.add(new Object[]{effect, chance});
        return this;
    }

    public Builder effect(PotionEffect effect, float chance) {
        this.effects.add(new Object[]{(Supplier<PotionEffect>)() -> new PotionEffect(effect), chance});
        return this;
    }

    public FoodProperties build() {
        return new FoodProperties(this.nutrition, this.saturation, this.meat, this.alwaysEat, this.fast, this.effects);
    }
}
