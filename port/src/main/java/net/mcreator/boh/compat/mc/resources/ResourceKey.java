package net.mcreator.boh.compat.mc.resources;

import java.util.Objects;

import net.minecraft.util.ResourceLocation;

/** 1.20 ResourceKey: a (registry, location) pair. Registry keys use the root registry name. */
public final class ResourceKey<T> {

    private final ResourceLocation registry;
    private final ResourceLocation location;

    private ResourceKey(ResourceLocation registry, ResourceLocation location) {
        this.registry = registry;
        this.location = location;
    }

    private static final java.util.concurrent.ConcurrentHashMap<String, ResourceKey<?>> INTERNED = new java.util.concurrent.ConcurrentHashMap<>();

    /** Interned like vanilla, so keys can be compared with ==. */
    @SuppressWarnings("unchecked")
    public static <T> ResourceKey<T> create(ResourceKey<?> registryKey, ResourceLocation location) {
        String k = registryKey.location + "|" + location;
        return (ResourceKey<T>) INTERNED.computeIfAbsent(k, x -> new ResourceKey<>(registryKey.location, location));
    }

    public static <T> ResourceKey<T> createRegistryKey(ResourceLocation name) {
        return new ResourceKey<>(new ResourceLocation("minecraft", "root"), name);
    }

    public ResourceLocation location() {
        return location;
    }

    public ResourceLocation registry() {
        return registry;
    }

    public boolean isFor(ResourceKey<?> registryKey) {
        return registry.equals(registryKey.location);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ResourceKey)) return false;
        ResourceKey<?> k = (ResourceKey<?>) o;
        return registry.equals(k.registry) && location.equals(k.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registry, location);
    }

    @Override
    public String toString() {
        return "ResourceKey[" + registry + " / " + location + "]";
    }
}
