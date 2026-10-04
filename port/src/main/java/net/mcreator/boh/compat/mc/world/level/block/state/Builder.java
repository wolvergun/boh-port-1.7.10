package net.mcreator.boh.compat.mc.world.level.block.state;

import java.util.ArrayList;
import java.util.List;

import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.minecraft.block.Block;

/** 1.20 StateDefinition.Builder. */
public class Builder<O, S> {

    private final Block owner;
    private final List<Property<?>> properties = new ArrayList<>();

    public Builder(Block owner) {
        this.owner = owner;
    }

    public Builder<O, S> add(Property<?>... props) {
        for (Property<?> p : props) if (!properties.contains(p)) properties.add(p);
        return this;
    }

    public StateDefinition create() {
        return new StateDefinition(owner, properties);
    }
}
