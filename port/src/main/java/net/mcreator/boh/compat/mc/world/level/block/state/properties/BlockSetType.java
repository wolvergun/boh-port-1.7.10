package net.mcreator.boh.compat.mc.world.level.block.state.properties;

/** 1.20 BlockSetType: wood/stone family of doors, trapdoors, buttons, plates. */
public final class BlockSetType {

    public static final BlockSetType OAK = new BlockSetType("oak", true);
    public static final BlockSetType STONE = new BlockSetType("stone", false);
    public static final BlockSetType IRON = new BlockSetType("iron", false);

    public final String name;
    public final boolean wooden;

    private BlockSetType(String name, boolean wooden) {
        this.name = name;
        this.wooden = wooden;
    }
}
