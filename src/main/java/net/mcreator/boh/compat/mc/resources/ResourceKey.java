package net.mcreator.boh.compat.mc.resources;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.util.ResourceLocation;

public final class ResourceKey<T> {
    private final ResourceLocation registry;
    private final ResourceLocation location;
    private static final ConcurrentHashMap<String, ResourceKey<?>> INTERNED = new ConcurrentHashMap<>();

    private ResourceKey(ResourceLocation registry, ResourceLocation location) {
        this.registry = registry;
        this.location = location;
    }

    public static <T> ResourceKey<T> create(ResourceKey<?> registryKey, ResourceLocation location) {
        String k = registryKey.location + "|" + location;
        return (ResourceKey<T>)INTERNED.computeIfAbsent(k, x -> new ResourceKey(registryKey.location, location));
    }

    public static <T> ResourceKey<T> createRegistryKey(ResourceLocation name) {
        return new ResourceKey<>(new ResourceLocation("minecraft", "root"), name);
    }

    public ResourceLocation location() {
        return this.location;
    }

    public ResourceLocation registry() {
        return this.registry;
    }

    public boolean isFor(ResourceKey<?> registryKey) {
        return this.registry.equals(registryKey.location);
    }

    @Override
    public boolean equals(Object o) {
        return !(o instanceof ResourceKey<?> k) ? false : this.registry.equals(k.registry) && this.location.equals(k.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.registry, this.location);
    }

    @Override
    public String toString() {
        return "ResourceKey[" + this.registry + " / " + this.location + "]";
    }
}
