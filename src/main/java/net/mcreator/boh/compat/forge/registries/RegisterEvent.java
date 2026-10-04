package net.mcreator.boh.compat.forge.registries;

import cpw.mods.fml.common.eventhandler.Event;
import java.util.function.Consumer;

public class RegisterEvent extends Event {
    public <T> void register(Object registryKey, Consumer<RegisterEvent.RegisterHelper<T>> consumer) {
        consumer.accept((name, value) -> {});
    }

    @FunctionalInterface
    public interface RegisterHelper<T> {
        void register(Object var1, T var2);
    }
}
