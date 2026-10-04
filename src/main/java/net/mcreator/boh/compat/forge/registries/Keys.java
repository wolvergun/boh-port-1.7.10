package net.mcreator.boh.compat.forge.registries;

import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.minecraft.util.ResourceLocation;

public final class Keys {
    public static final ResourceKey<?> POI_TYPES = ResourceKey.createRegistryKey(new ResourceLocation("minecraft", "point_of_interest_type"));
    public static final ResourceKey<?> BLOCKS = Registries.BLOCK;
    public static final ResourceKey<?> ITEMS = Registries.ITEM;

    private Keys() {
    }
}
