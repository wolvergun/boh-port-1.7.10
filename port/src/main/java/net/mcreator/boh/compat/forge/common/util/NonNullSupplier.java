package net.mcreator.boh.compat.forge.common.util;

/** Forge NonNullSupplier. */
@FunctionalInterface
public interface NonNullSupplier<T> {

    T get();
}
