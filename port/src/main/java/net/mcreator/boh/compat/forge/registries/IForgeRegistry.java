package net.mcreator.boh.compat.forge.registries;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.minecraft.util.ResourceLocation;

/**
 * A name-to-object registry mirroring 1.20 IForgeRegistry. Objects registered by the mod are pushed into the
 * matching 1.7.10 registry by {@link #registrar}; lookups of other ids go through {@link #fallback}.
 */
public class IForgeRegistry<T> {

    private final ResourceKey<?> key;
    private final Map<ResourceLocation, T> byName = new LinkedHashMap<>();
    private final Map<T, ResourceLocation> byValue = new java.util.IdentityHashMap<>();
    BiFunction<ResourceLocation, T, T> registrar = (n, v) -> v;
    Function<ResourceLocation, T> fallback = n -> null;
    Function<T, ResourceLocation> keyFallback = v -> null;

    public IForgeRegistry(ResourceKey<?> key) {
        this.key = key;
    }

    public ResourceKey<?> getRegistryKey() {
        return key;
    }

    public IForgeRegistry<T> onRegister(BiFunction<ResourceLocation, T, T> registrar) {
        this.registrar = registrar;
        return this;
    }

    public IForgeRegistry<T> withFallback(Function<ResourceLocation, T> fallback, Function<T, ResourceLocation> keyFallback) {
        this.fallback = fallback;
        this.keyFallback = keyFallback;
        return this;
    }

    /** Registers {@code value}; returns the object the rest of the mod should use (the registrar may substitute one). */
    public T register(ResourceLocation name, T value) {
        T canonical = registrar.apply(name, value);
        if (canonical == null) canonical = value;
        byName.put(name, canonical);
        byValue.put(canonical, name);
        return canonical;
    }

    public T getValue(ResourceLocation name) {
        if (name == null) return null;
        T v = byName.get(name);
        return v != null ? v : fallback.apply(name);
    }

    public ResourceLocation getKey(T value) {
        ResourceLocation n = byValue.get(value);
        return n != null ? n : keyFallback.apply(value);
    }

    public boolean containsKey(ResourceLocation name) {
        return getValue(name) != null;
    }

    public boolean containsValue(T value) {
        return getKey(value) != null;
    }

    public Collection<T> getValues() {
        return byName.values();
    }

    public Set<ResourceLocation> getKeys() {
        return byName.keySet();
    }

    public Set<Map.Entry<ResourceLocation, T>> getEntries() {
        return byName.entrySet();
    }

    public ITagManager<T> tags() {
        return new ITagManager<>(this);
    }

    public java.util.Optional<net.mcreator.boh.compat.mc.core.Holder<T>> getHolder(T value) {
        return value == null ? java.util.Optional.empty() : java.util.Optional.of(net.mcreator.boh.compat.mc.core.Holder.direct(value));
    }
}
