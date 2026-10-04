package net.mcreator.boh.compat.mc.world.level.block.state.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class Property<T extends Comparable<T>> {
    private final String name;
    private final Class<T> clazz;
    protected final List<T> values = new ArrayList<>();

    protected Property(String name, Class<T> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public String getName() {
        return this.name;
    }

    public Class<T> getValueClass() {
        return this.clazz;
    }

    public Collection<T> getPossibleValues() {
        return this.values;
    }

    public int indexOf(T value) {
        int i = this.values.indexOf(value);
        return i < 0 ? 0 : i;
    }

    public T byIndex(int i) {
        return this.values.get(Math.max(0, Math.min(this.values.size() - 1, i)));
    }

    public int size() {
        return this.values.size();
    }

    public abstract String getName(T var1);

    public Optional<T> getValue(String s) {
        for (T v : this.values) {
            if (this.getName(v).equals(s)) {
                return Optional.of(v);
            }
        }

        return Optional.empty();
    }

    @Override
    public String toString() {
        return this.name;
    }
}
