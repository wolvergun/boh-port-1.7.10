package net.mcreator.boh.compat.forge.common.util;

@FunctionalInterface
public interface NonNullSupplier<T> {
    T get();
}
