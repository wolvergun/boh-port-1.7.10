package net.mcreator.boh.compat.forge.registries;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.mcreator.boh.compat.mc.core.Holder;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.minecraft.util.ResourceLocation;

public class IForgeRegistry<T> {
    private final ResourceKey<?> key;
    private final Map<ResourceLocation, T> byName = new LinkedHashMap<>();
    private final Map<T, ResourceLocation> byValue = new IdentityHashMap<>();
    BiFunction<ResourceLocation, T, T> registrar = (n, v) -> v;
    Function<ResourceLocation, T> fallback = n -> null;
    Function<T, ResourceLocation> keyFallback = v -> null;

    public IForgeRegistry(ResourceKey<?> key) {
        this.key = key;
    }

    public ResourceKey<?> getRegistryKey() {
        return this.key;
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

    public T register(ResourceLocation name, T value) {
        T canonical = this.registrar.apply(name, value);
        if (canonical == null) {
            canonical = value;
        }

        this.byName.put(name, canonical);
        this.byValue.put(canonical, name);
        return canonical;
    }

    public T getValue(ResourceLocation name) {
        if (name == null) {
            return null;
        } else {
            T v = this.byName.get(name);
            return v != null ? v : this.fallback.apply(name);
        }
    }

    public ResourceLocation getKey(T value) {
        ResourceLocation n = this.byValue.get(value);
        return n != null ? n : this.keyFallback.apply(value);
    }

    public boolean containsKey(ResourceLocation name) {
        return this.getValue(name) != null;
    }

    public boolean containsValue(T value) {
        return this.getKey(value) != null;
    }

    public Collection<T> getValues() {
        return this.byName.values();
    }

    public Set<ResourceLocation> getKeys() {
        return this.byName.keySet();
    }

    public Set<Entry<ResourceLocation, T>> getEntries() {
        return this.byName.entrySet();
    }

    public ITagManager<T> tags() {
        return new ITagManager<>(this);
    }

    public Optional<Holder<T>> getHolder(T value) {
        return value == null ? Optional.empty() : Optional.of(Holder.direct(value));
    }
}
