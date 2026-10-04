package net.mcreator.boh.compat.forge.common.capabilities;

import java.util.HashMap;
import java.util.Map;

/** Forge CapabilityManager: one Capability per type name. */
public final class CapabilityManager {

    private static final Map<String, Capability<?>> CAPS = new HashMap<>();

    private CapabilityManager() {}

    @SuppressWarnings("unchecked")
    public static <T> Capability<T> get(CapabilityToken<T> token) {
        return (Capability<T>) CAPS.computeIfAbsent(token.name(), Capability::new);
    }

    @SuppressWarnings("unchecked")
    public static <T> Capability<T> named(String name) {
        return (Capability<T>) CAPS.computeIfAbsent(name, Capability::new);
    }
}
