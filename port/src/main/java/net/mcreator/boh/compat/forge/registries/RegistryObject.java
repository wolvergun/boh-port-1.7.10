package net.mcreator.boh.compat.forge.registries;

import java.util.Optional;
import java.util.function.Supplier;

import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.minecraft.util.ResourceLocation;

/** Lazy handle to a registered object (1.20 RegistryObject). */
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

    /** Creates and registers the object the first time it is needed (registration always runs in preInit). */
    @Override
    @SuppressWarnings("unchecked")
    public synchronized T get() {
        if (value == null && factory != null) {
            Supplier<? extends T> f = factory;
            factory = null;
            T created = f.get();
            // offline smoke test (tools/SmokeTest.java): construct without touching FML registries
            value = SMOKE_TEST ? created : (T) ((IForgeRegistry<Object>) registry).register(id, created);
        }
        return value;
    }

    public ResourceLocation getId() {
        return id;
    }

    public Optional<ResourceKey<T>> getKey() {
        return Optional.of(ResourceKey.create(registry.getRegistryKey(), id));
    }

    public boolean isPresent() {
        return get() != null;
    }

    public Optional<T> getHolder() {
        return Optional.ofNullable(get());
    }

    public void ifPresent(java.util.function.Consumer<? super T> consumer) {
        T v = get();
        if (v != null) consumer.accept(v);
    }

    @Override
    public String toString() {
        return "RegistryObject{" + id + "}";
    }
}
