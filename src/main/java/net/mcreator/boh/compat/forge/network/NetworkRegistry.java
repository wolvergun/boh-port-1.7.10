package net.mcreator.boh.compat.forge.network;

import java.util.function.Predicate;
import java.util.function.Supplier;
import net.mcreator.boh.compat.forge.network.simple.SimpleChannel;
import net.minecraft.util.ResourceLocation;

public final class NetworkRegistry {
    private NetworkRegistry() {
    }

    public static SimpleChannel newSimpleChannel(ResourceLocation name, Supplier<String> version, Predicate<String> client, Predicate<String> server) {
        return new SimpleChannel(name.getResourceDomain() + "_" + name.getResourcePath());
    }
}
