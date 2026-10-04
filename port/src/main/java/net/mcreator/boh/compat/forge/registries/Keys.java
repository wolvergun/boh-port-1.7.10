package net.mcreator.boh.compat.forge.registries;

import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.resources.ResourceKey;

/** Forge ForgeRegistries.Keys. */
public final class Keys {

    public static final ResourceKey<?> POI_TYPES = ResourceKey.createRegistryKey(new net.minecraft.util.ResourceLocation("minecraft", "point_of_interest_type"));
    public static final ResourceKey<?> BLOCKS = Registries.BLOCK;
    public static final ResourceKey<?> ITEMS = Registries.ITEM;

    private Keys() {}
}
