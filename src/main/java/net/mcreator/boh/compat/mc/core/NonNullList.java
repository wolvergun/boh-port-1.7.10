package net.mcreator.boh.compat.mc.core;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NonNullList<E> extends AbstractList<E> {
    private final List<E> delegate;
    private final E defaultValue;

    protected NonNullList(List<E> delegate, E defaultValue) {
        this.delegate = delegate;
        this.defaultValue = defaultValue;
    }

    public static <E> NonNullList<E> create() {
        return new NonNullList<>(new ArrayList<>(), null);
    }

    public static <E> NonNullList<E> withSize(int size, E fill) {
        Object[] arr = new Object[size];
        Arrays.fill(arr, fill);
        return new NonNullList<>(Arrays.asList((E[])arr), fill);
    }

    @SafeVarargs
    public static <E> NonNullList<E> of(E def, E... values) {
        return new NonNullList<>(Arrays.asList(values), def);
    }

    @Override
    public E get(int i) {
        return this.delegate.get(i);
    }

    @Override
    public E set(int i, E v) {
        return this.delegate.set(i, v == null ? this.defaultValue : v);
    }

    @Override
    public void add(int i, E v) {
        this.delegate.add(i, v);
    }

    @Override
    public E remove(int i) {
        return this.delegate.remove(i);
    }

    @Override
    public int size() {
        return this.delegate.size();
    }

    @Override
    public void clear() {
        if (this.defaultValue == null) {
            this.delegate.clear();
        } else {
            for (int i = 0; i < this.size(); i++) {
                this.set(i, this.defaultValue);
            }
        }
    }
}
