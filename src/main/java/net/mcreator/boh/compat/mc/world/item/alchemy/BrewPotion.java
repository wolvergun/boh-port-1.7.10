package net.mcreator.boh.compat.mc.world.item.alchemy;

import java.util.Arrays;
import java.util.List;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class BrewPotion {
    private final List<PotionEffect> effects;
    private ResourceLocation id;

    public BrewPotion(PotionEffect... effects) {
        this.effects = Arrays.asList(effects);
    }

    public BrewPotion(String name, PotionEffect... effects) {
        this(effects);
    }

    public List<PotionEffect> getEffects() {
        return this.effects;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public void setRegistryName(ResourceLocation id) {
        this.id = id;
    }
}
