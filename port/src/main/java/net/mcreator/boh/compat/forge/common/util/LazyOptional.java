package net.mcreator.boh.compat.forge.common.util;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/** Forge LazyOptional: a lazily-resolved, invalidatable capability value. */
public final class LazyOptional<T> {

    private static final LazyOptional<Void> EMPTY = new LazyOptional<>(null);

    private Supplier<? extends T> supplier;
    private T value;
    private boolean resolved, valid = true;

    private LazyOptional(Supplier<? extends T> supplier) {
        this.supplier = supplier;
        if (supplier == null) valid = false;
    }

    public static <T> LazyOptional<T> of(NonNullSupplier<T> s) {
        return s == null ? empty() : new LazyOptional<>(s::get);
    }

    public static <T> LazyOptional<T> ofObject(T v) {
        return v == null ? empty() : new LazyOptional<>(() -> v);
    }

    @SuppressWarnings("unchecked")
    public static <T> LazyOptional<T> empty() {
        return (LazyOptional<T>) EMPTY;
    }

    private T get() {
        if (!valid) return null;
        if (!resolved) {
            value = supplier.get();
            resolved = true;
        }
        return value;
    }

    public boolean isPresent() {
        return get() != null;
    }

    public void ifPresent(Consumer<? super T> c) {
        T v = get();
        if (v != null) c.accept(v);
    }

    public T orElse(T other) {
        T v = get();
        return v != null ? v : other;
    }

    public T orElseGet(Supplier<? extends T> other) {
        T v = get();
        return v != null ? v : other.get();
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> ex) throws X {
        T v = get();
        if (v == null) throw ex.get();
        return v;
    }

    public Optional<T> resolve() {
        return Optional.ofNullable(get());
    }

    public <U> LazyOptional<U> lazyMap(Function<? super T, ? extends U> f) {
        return isPresent() ? new LazyOptional<>(() -> f.apply(get())) : empty();
    }

    public <U> Optional<U> map(Function<? super T, ? extends U> f) {
        return resolve().map(f);
    }

    @SuppressWarnings("unchecked")
    public <X> LazyOptional<X> cast() {
        return (LazyOptional<X>) this;
    }

    public void invalidate() {
        valid = false;
    }
}
