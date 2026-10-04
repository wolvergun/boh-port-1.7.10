package net.mcreator.boh.compat.mc.world.level.block.state.properties;

public final class WoodType {
    public static final WoodType OAK = new WoodType("oak");
    public final String name;

    private WoodType(String name) {
        this.name = name;
    }
}
