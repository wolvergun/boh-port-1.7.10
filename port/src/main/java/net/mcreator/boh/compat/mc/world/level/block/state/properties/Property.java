package net.mcreator.boh.compat.mc.world.level.block.state.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/** 1.20 block state property with a fixed, ordered value list. */
public abstract class Property<T extends Comparable<T>> {

    private final String name;
    private final Class<T> clazz;
    protected final List<T> values = new ArrayList<>();

    protected Property(String name, Class<T> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public Class<T> getValueClass() {
        return clazz;
    }

    public Collection<T> getPossibleValues() {
        return values;
    }

    public int indexOf(T value) {
        int i = values.indexOf(value);
        return i < 0 ? 0 : i;
    }

    public T byIndex(int i) {
        return values.get(Math.max(0, Math.min(values.size() - 1, i)));
    }

    public int size() {
        return values.size();
    }

    public abstract String getName(T value);

    public Optional<T> getValue(String s) {
        for (T v : values) if (getName(v).equals(s)) return Optional.of(v);
        return Optional.empty();
    }

    @Override
    public String toString() {
        return name;
    }
}
