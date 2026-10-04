package net.mcreator.boh.compat.mc.data.worldgen.features;

import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.minecraft.util.ResourceLocation;

public final class FeatureUtils {
    private FeatureUtils() {
    }

    public static <T> ResourceKey<T> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(name));
    }
}
