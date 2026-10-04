package net.mcreator.boh.compat.mc.core;

import net.mcreator.boh.compat.mc.resources.ResourceKey;

/** 1.20 Registry view: only keyed holder lookups are needed by the mod. */
public final class Registry<T> {

    private final ResourceKey<?> key;

    Registry(ResourceKey<?> key) {
        this.key = key;
    }

    public ResourceKey<?> key() {
        return key;
    }

    public Holder.Reference<T> getHolderOrThrow(ResourceKey<T> k) {
        return new Holder.Reference<>(k);
    }

    public java.util.Optional<Holder.Reference<T>> getHolder(ResourceKey<T> k) {
        return java.util.Optional.of(new Holder.Reference<>(k));
    }

    public T getOrThrow(ResourceKey<T> k) {
        return null;
    }

    public T get(ResourceKey<T> k) {
        return null;
    }
}
