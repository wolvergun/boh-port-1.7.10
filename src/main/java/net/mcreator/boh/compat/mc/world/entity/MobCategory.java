package net.mcreator.boh.compat.mc.world.entity;

import net.minecraft.entity.EnumCreatureType;

public enum MobCategory {
    MONSTER(EnumCreatureType.monster),
    CREATURE(EnumCreatureType.creature),
    AMBIENT(EnumCreatureType.ambient),
    AXOLOTLS(EnumCreatureType.waterCreature),
    UNDERGROUND_WATER_CREATURE(EnumCreatureType.waterCreature),
    WATER_CREATURE(EnumCreatureType.waterCreature),
    WATER_AMBIENT(EnumCreatureType.waterCreature),
    MISC(null);

    private final EnumCreatureType legacy;

    private MobCategory(EnumCreatureType legacy) {
        this.legacy = legacy;
    }

    public EnumCreatureType toVanilla() {
        return this.legacy;
    }

    public String getName() {
        return this.name().toLowerCase();
    }
}
