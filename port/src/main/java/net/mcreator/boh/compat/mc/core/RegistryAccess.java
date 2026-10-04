package net.mcreator.boh.compat.mc.core;

import net.mcreator.boh.compat.mc.resources.ResourceKey;

/** 1.20 RegistryAccess: hands out name-only registries for keyed lookups. */
public final class RegistryAccess {

    public static final RegistryAccess INSTANCE = new RegistryAccess();

    private RegistryAccess() {}

    public <T> Registry<T> registryOrThrow(ResourceKey<?> key) {
        return new Registry<>(key);
    }
}
