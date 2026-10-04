package net.mcreator.boh.compat.mc.core;

/** 1.20 Holder: just a value box here. */
public interface Holder<T> {

    T value();

    default T get() {
        return value();
    }

    static <T> Holder<T> direct(T value) {
        return () -> value;
    }

    /** Holder for a registry key whose value only matters by identity (damage types, dimension types). */
    final class Reference<T> implements Holder<T> {

        private final net.mcreator.boh.compat.mc.resources.ResourceKey<T> key;

        public Reference(net.mcreator.boh.compat.mc.resources.ResourceKey<T> key) {
            this.key = key;
        }

        public net.mcreator.boh.compat.mc.resources.ResourceKey<T> key() {
            return key;
        }

        public boolean is(net.mcreator.boh.compat.mc.resources.ResourceKey<?> k) {
            return key.equals(k);
        }

        @Override
        public T value() {
            return null;
        }
    }
}
