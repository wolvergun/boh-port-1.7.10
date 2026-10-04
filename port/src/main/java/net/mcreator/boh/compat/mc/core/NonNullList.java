package net.mcreator.boh.compat.mc.core;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** 1.20 NonNullList: a fixed-size list whose empty slots hold a default value. */
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

    @SuppressWarnings("unchecked")
    public static <E> NonNullList<E> withSize(int size, E fill) {
        Object[] arr = new Object[size];
        Arrays.fill(arr, fill);
        return new NonNullList<>((List<E>) Arrays.asList(arr), fill);
    }

    @SafeVarargs
    public static <E> NonNullList<E> of(E def, E... values) {
        return new NonNullList<>(Arrays.asList(values), def);
    }

    @Override
    public E get(int i) {
        return delegate.get(i);
    }

    @Override
    public E set(int i, E v) {
        return delegate.set(i, v == null ? defaultValue : v);
    }

    @Override
    public void add(int i, E v) {
        delegate.add(i, v);
    }

    @Override
    public E remove(int i) {
        return delegate.remove(i);
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public void clear() {
        if (defaultValue == null) delegate.clear();
        else for (int i = 0; i < size(); i++) set(i, defaultValue);
    }
}
