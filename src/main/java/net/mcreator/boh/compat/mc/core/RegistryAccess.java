package net.mcreator.boh.compat.mc.core;

import net.mcreator.boh.compat.mc.resources.ResourceKey;

public final class RegistryAccess {
    public static final RegistryAccess INSTANCE = new RegistryAccess();

    private RegistryAccess() {
    }

    public <T> Registry<T> registryOrThrow(ResourceKey<?> key) {
        return new Registry<>(key);
    }
}
