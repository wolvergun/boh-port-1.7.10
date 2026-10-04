package net.mcreator.boh.compat.forge.client;

import com.google.common.collect.ImmutableMap;

import net.mcreator.boh.compat.mc.client.renderer.DimensionSpecialEffects;
import net.minecraft.util.ResourceLocation;

/** 1.20 DimensionSpecialEffectsManager; EFFECTS is read reflectively by the mod's sky procedures. */
public final class DimensionSpecialEffectsManager {

    private static ImmutableMap<ResourceLocation, DimensionSpecialEffects> EFFECTS = ImmutableMap.of();

    private DimensionSpecialEffectsManager() {}

    public static void set(ImmutableMap<ResourceLocation, DimensionSpecialEffects> effects) {
        EFFECTS = effects;
    }

    public static DimensionSpecialEffects getForType(ResourceLocation type) {
        DimensionSpecialEffects e = EFFECTS.get(type);
        return e != null ? e : DimensionSpecialEffects.OVERWORLD;
    }

    public static ImmutableMap<ResourceLocation, DimensionSpecialEffects> all() {
        return EFFECTS;
    }
}
