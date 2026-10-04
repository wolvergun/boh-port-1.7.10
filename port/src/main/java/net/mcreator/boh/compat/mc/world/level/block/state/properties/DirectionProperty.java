package net.mcreator.boh.compat.mc.world.level.block.state.properties;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

import net.mcreator.boh.compat.mc.core.Direction;

public class DirectionProperty extends EnumProperty<Direction> {

    protected DirectionProperty(String name, Collection<Direction> values) {
        super(name, Direction.class, values);
    }

    public static DirectionProperty create(String name, Direction... values) {
        return new DirectionProperty(name, Arrays.asList(values));
    }

    public static DirectionProperty create(String name, Predicate<Direction> filter) {
        return new DirectionProperty(name, Arrays.stream(Direction.values()).filter(filter).collect(java.util.stream.Collectors.toList()));
    }

    public static DirectionProperty create(String name) {
        return create(name, d -> true);
    }
}
