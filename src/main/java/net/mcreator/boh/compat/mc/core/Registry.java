package net.mcreator.boh.compat.mc.core;

import java.util.Optional;
import net.mcreator.boh.compat.mc.resources.ResourceKey;

public final class Registry<T> {
    private final ResourceKey<?> key;

    Registry(ResourceKey<?> key) {
        this.key = key;
    }

    public ResourceKey<?> key() {
        return this.key;
    }

    public Holder.Reference<T> getHolderOrThrow(ResourceKey<T> k) {
        return new Holder.Reference<>(k);
    }

    public Optional<Holder.Reference<T>> getHolder(ResourceKey<T> k) {
        return Optional.of(new Holder.Reference<>(k));
    }

    public T getOrThrow(ResourceKey<T> k) {
        return null;
    }

    public T get(ResourceKey<T> k) {
        return null;
    }
}
