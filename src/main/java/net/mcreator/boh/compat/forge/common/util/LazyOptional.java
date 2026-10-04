package net.mcreator.boh.compat.forge.common.util;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class LazyOptional<T> {
    private static final LazyOptional<Void> EMPTY = new LazyOptional<>(null);
    private Supplier<? extends T> supplier;
    private T value;
    private boolean resolved;
    private boolean valid = true;

    private LazyOptional(Supplier<? extends T> supplier) {
        this.supplier = supplier;
        if (supplier == null) {
            this.valid = false;
        }
    }

    public static <T> LazyOptional<T> of(NonNullSupplier<T> s) {
        return s == null ? empty() : new LazyOptional<>(s::get);
    }

    public static <T> LazyOptional<T> ofObject(T v) {
        return v == null ? empty() : new LazyOptional<>(() -> v);
    }

    public static <T> LazyOptional<T> empty() {
        return (LazyOptional<T>)EMPTY;
    }

    private T get() {
        if (!this.valid) {
            return null;
        } else {
            if (!this.resolved) {
                this.value = (T)this.supplier.get();
                this.resolved = true;
            }

            return this.value;
        }
    }

    public boolean isPresent() {
        return this.get() != null;
    }

    public void ifPresent(Consumer<? super T> c) {
        T v = this.get();
        if (v != null) {
            c.accept(v);
        }
    }

    public T orElse(T other) {
        T v = this.get();
        return v != null ? v : other;
    }

    public T orElseGet(Supplier<? extends T> other) {
        T v = this.get();
        return v != null ? v : other.get();
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> ex) throws X {
        T v = this.get();
        if (v == null) {
            throw ex.get();
        } else {
            return v;
        }
    }

    public Optional<T> resolve() {
        return Optional.ofNullable(this.get());
    }

    public <U> LazyOptional<U> lazyMap(Function<? super T, ? extends U> f) {
        return this.isPresent() ? new LazyOptional(() -> (T)f.apply(this.get())) : empty();
    }

    public <U> Optional<U> map(Function<? super T, ? extends U> f) {
        return this.resolve().map(f);
    }

    public <X> LazyOptional<X> cast() {
        return (LazyOptional<X>)this;
    }

    public void invalidate() {
        this.valid = false;
    }
}
