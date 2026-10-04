package net.mcreator.boh.compat.mc.world.level.block.state.properties;

import java.util.Arrays;
import java.util.Collection;

public class EnumProperty<T extends Enum<T>> extends Property<T> {
    protected EnumProperty(String name, Class<T> clazz, Collection<T> allowed) {
        super(name, clazz);
        this.values.addAll(allowed);
    }

    public static <T extends Enum<T>> EnumProperty<T> create(String name, Class<T> clazz) {
        return new EnumProperty<>(name, clazz, Arrays.asList(clazz.getEnumConstants()));
    }

    @SafeVarargs
    public static <T extends Enum<T>> EnumProperty<T> create(String name, Class<T> clazz, T... allowed) {
        return new EnumProperty<>(name, clazz, Arrays.asList(allowed));
    }

    public String getName(T v) {
        return v.name().toLowerCase();
    }
}
