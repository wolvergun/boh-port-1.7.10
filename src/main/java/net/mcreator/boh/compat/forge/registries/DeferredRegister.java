package net.mcreator.boh.compat.forge.registries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.minecraft.util.ResourceLocation;

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

    public static <T> DeferredRegister<T> create(ResourceKey<?> key, String modid) {
        return new DeferredRegister<>((IForgeRegistry<T>)ForgeRegistries.byKey(key), modid);
    }

    public <I extends T> RegistryObject<I> register(String name, Supplier<? extends I> sup) {
        RegistryObject<I> obj = new RegistryObject<>(new ResourceLocation(this.modid, name), this.registry, sup);
        this.entries.add(obj);
        return obj;
    }

    public void register(Object bus) {
        for (RegistryObject<T> e : this.entries) {
            e.get();
        }
    }

    public Collection<RegistryObject<T>> getEntries() {
        return Collections.unmodifiableList(this.entries);
    }

    public IForgeRegistry<T> getRegistry() {
        return this.registry;
    }
}
