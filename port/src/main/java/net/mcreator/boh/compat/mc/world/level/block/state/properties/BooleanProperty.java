package net.mcreator.boh.compat.mc.world.level.block.state.properties;

public class BooleanProperty extends Property<Boolean> {

    protected BooleanProperty(String name) {
        super(name, Boolean.class);
        values.add(false);
        values.add(true);
    }

    public static BooleanProperty create(String name) {
        return new BooleanProperty(name);
    }

    @Override
    public String getName(Boolean v) {
        return v.toString();
    }
}
