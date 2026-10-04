package net.mcreator.boh.compat.forge.registries;

import java.util.function.Consumer;

import cpw.mods.fml.common.eventhandler.Event;

/** Forge RegisterEvent; only POI registration uses it and 1.7.10 has no POI system, so values are just collected. */
public class RegisterEvent extends Event {

    @FunctionalInterface
    public interface RegisterHelper<T> {

        void register(Object name, T value);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T> void register(Object registryKey, Consumer<RegisterHelper<T>> consumer) {
        consumer.accept((name, value) -> {});
    }
}
