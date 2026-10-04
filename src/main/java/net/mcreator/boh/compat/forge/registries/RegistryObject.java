package net.mcreator.boh.compat.forge.registries;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.minecraft.util.ResourceLocation;

public final class RegistryObject<T> implements Supplier<T> {
    private final ResourceLocation id;
    private final IForgeRegistry<? super T> registry;
    private Supplier<? extends T> factory;
    private T value;
    static final boolean SMOKE_TEST = Boolean.getBoolean("boh.smoketest");

    RegistryObject(ResourceLocation id, IForgeRegistry<? super T> registry, Supplier<? extends T> factory) {
        this.id = id;
        this.registry = registry;
        this.factory = factory;
    }

    @Override
    public synchronized T get() {
        if (this.value == null && this.factory != null) {
            Supplier<? extends T> f = this.factory;
            this.factory = null;
            T created = (T)f.get();
            this.value = SMOKE_TEST ? created : this.registry.register(this.id, created);
        }

        return this.value;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public Optional<ResourceKey<T>> getKey() {
        return Optional.of(ResourceKey.create(this.registry.getRegistryKey(), this.id));
    }

    public boolean isPresent() {
        return this.get() != null;
    }

    public Optional<T> getHolder() {
        return Optional.ofNullable(this.get());
    }

    public void ifPresent(Consumer<? super T> consumer) {
        T v = this.get();
        if (v != null) {
            consumer.accept(v);
        }
    }

    @Override
    public String toString() {
        return "RegistryObject{" + this.id + "}";
    }
}
