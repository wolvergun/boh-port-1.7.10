package net.mcreator.boh.compat.forge.registries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.minecraft.util.ResourceLocation;

/** 1.20 DeferredRegister: queues entries and registers them when {@link #register(Object)} runs in preInit. */
public final class DeferredRegister<T> {

    private final IForgeRegistry<T> registry;
    private final String modid;
    private final List<RegistryObject<T>> entries = new ArrayList<>();

    private DeferredRegister(IForgeRegistry<T> registry, String modid) {
        this.registry = registry;
        this.modid = modid;
    }

    public static <T> DeferredRegister<T> create(IForgeRegistry<T> registry, String modid) {
        return new DeferredRegister<>(registry, modid);
    }

    @SuppressWarnings("unchecked")
    public static <T> DeferredRegister<T> create(ResourceKey<?> key, String modid) {
        return new DeferredRegister<>((IForgeRegistry<T>) ForgeRegistries.byKey(key), modid);
    }

    public <I extends T> RegistryObject<I> register(String name, Supplier<? extends I> sup) {
        RegistryObject<I> obj = new RegistryObject<>(new ResourceLocation(modid, name), registry, sup);
        entries.add((RegistryObject<T>) (RegistryObject<?>) obj);
        return obj;
    }

    /** Called with the (ignored) mod event bus; instantiates and registers everything in declaration order. */
    public void register(Object bus) {
        for (RegistryObject<T> e : entries) e.get();
    }

    public Collection<RegistryObject<T>> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public IForgeRegistry<T> getRegistry() {
        return registry;
    }
}
