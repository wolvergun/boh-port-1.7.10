package net.mcreator.boh.compat.mc.world.item;

import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;

public enum Type {
    HELMET(EquipmentSlot.HEAD, "helmet"),
    CHESTPLATE(EquipmentSlot.CHEST, "chestplate"),
    LEGGINGS(EquipmentSlot.LEGS, "leggings"),
    BOOTS(EquipmentSlot.FEET, "boots");

    private final EquipmentSlot slot;
    private final String name;

    private Type(EquipmentSlot slot, String name) {
        this.slot = slot;
        this.name = name;
    }

    public EquipmentSlot getSlot() {
        return this.slot;
    }

    public String getName() {
        return this.name;
    }

    public int legacyArmorType() {
        return this.ordinal();
    }
}
