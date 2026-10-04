package net.mcreator.boh.compat.mc.world.entity;

public enum EquipmentSlot {
    MAINHAND(EquipmentSlot.Type.HAND, 0, 0),
    OFFHAND(EquipmentSlot.Type.HAND, 1, -1),
    FEET(EquipmentSlot.Type.ARMOR, 0, 1),
    LEGS(EquipmentSlot.Type.ARMOR, 1, 2),
    CHEST(EquipmentSlot.Type.ARMOR, 2, 3),
    HEAD(EquipmentSlot.Type.ARMOR, 3, 4);

    private final EquipmentSlot.Type type;
    private final int index;
    private final int legacyIndex;

    private EquipmentSlot(EquipmentSlot.Type type, int index, int legacyIndex) {
        this.type = type;
        this.index = index;
        this.legacyIndex = legacyIndex;
    }

    public EquipmentSlot.Type getType() {
        return this.type;
    }

    public int getIndex() {
        return this.index;
    }

    public int getFilterFlag() {
        return this.ordinal();
    }

    public int legacyIndex() {
        return this.legacyIndex;
    }

    public int armorInventoryIndex() {
        return this.type == EquipmentSlot.Type.ARMOR ? this.index : -1;
    }

    public String getName() {
        return this.name().toLowerCase();
    }

    public boolean isArmor() {
        return this.type == EquipmentSlot.Type.ARMOR;
    }

    public static EquipmentSlot byName(String name) {
        for (EquipmentSlot s : values()) {
            if (s.getName().equals(name)) {
                return s;
            }
        }

        throw new IllegalArgumentException("Invalid slot '" + name + "'");
    }

    public static enum Type {
        HAND,
        ARMOR;
    }
}
