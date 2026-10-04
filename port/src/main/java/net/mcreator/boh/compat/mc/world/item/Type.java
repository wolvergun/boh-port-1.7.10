package net.mcreator.boh.compat.mc.world.item;

import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;

/** 1.20 ArmorItem.Type. */
public enum Type {

    HELMET(EquipmentSlot.HEAD, "helmet"),
    CHESTPLATE(EquipmentSlot.CHEST, "chestplate"),
    LEGGINGS(EquipmentSlot.LEGS, "leggings"),
    BOOTS(EquipmentSlot.FEET, "boots");

    private final EquipmentSlot slot;
    private final String name;

    Type(EquipmentSlot slot, String name) {
        this.slot = slot;
        this.name = name;
    }

    public EquipmentSlot getSlot() {
        return slot;
    }

    public String getName() {
        return name;
    }

    /** 1.7.10 armorType index (0 helmet .. 3 boots). */
    public int legacyArmorType() {
        return ordinal();
    }
}
