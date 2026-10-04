package net.mcreator.boh.compat.mc.world.level.block.state.properties;

public class BooleanProperty extends Property<Boolean> {
    protected BooleanProperty(String name) {
        super(name, Boolean.class);
        this.values.add(false);
        this.values.add(true);
    }

    public static BooleanProperty create(String name) {
        return new BooleanProperty(name);
    }

    public String getName(Boolean v) {
        return v.toString();
    }
}
