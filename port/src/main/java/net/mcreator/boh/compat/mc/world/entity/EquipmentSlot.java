package net.mcreator.boh.compat.mc.world.entity;

/**
 * 1.20 equipment slots. {@code legacyIndex} is the 1.7.10 EntityLiving equipment index (0 held, 1 feet ... 4
 * head); OFFHAND has no 1.7.10 equivalent and is treated as empty.
 */
public enum EquipmentSlot {

    MAINHAND(Type.HAND, 0, 0),
    OFFHAND(Type.HAND, 1, -1),
    FEET(Type.ARMOR, 0, 1),
    LEGS(Type.ARMOR, 1, 2),
    CHEST(Type.ARMOR, 2, 3),
    HEAD(Type.ARMOR, 3, 4);

    public enum Type {
        HAND,
        ARMOR
    }

    private final Type type;
    private final int index;
    private final int legacyIndex;

    EquipmentSlot(Type type, int index, int legacyIndex) {
        this.type = type;
        this.index = index;
        this.legacyIndex = legacyIndex;
    }

    public Type getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public int getFilterFlag() {
        return ordinal();
    }

    public int legacyIndex() {
        return legacyIndex;
    }

    /** Index into EntityPlayer.inventory.armorInventory (0 boots .. 3 helmet), or -1. */
    public int armorInventoryIndex() {
        return type == Type.ARMOR ? index : -1;
    }

    public String getName() {
        return name().toLowerCase();
    }

    public boolean isArmor() {
        return type == Type.ARMOR;
    }

    public static EquipmentSlot byName(String name) {
        for (EquipmentSlot s : values()) if (s.getName().equals(name)) return s;
        throw new IllegalArgumentException("Invalid slot '" + name + "'");
    }
}
