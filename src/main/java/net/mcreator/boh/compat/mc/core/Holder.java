package net.mcreator.boh.compat.mc.core;

import net.mcreator.boh.compat.mc.resources.ResourceKey;

public interface Holder<T> {
    T value();

    default T get() {
        return this.value();
    }

    static <T> Holder<T> direct(T value) {
        return () -> value;
    }

    public static final class Reference<T> implements Holder<T> {
        private final ResourceKey<T> key;

        public Reference(ResourceKey<T> key) {
            this.key = key;
        }

        public ResourceKey<T> key() {
            return this.key;
        }

        public boolean is(ResourceKey<?> k) {
            return this.key.equals(k);
        }

        @Override
        public T value() {
            return null;
        }
    }
}
