package net.mcreator.boh.compat.mc.world.entity;

import net.minecraft.entity.EnumCreatureAttribute;

/** 1.20 MobType (a class there, constants here) mapped onto {@link EnumCreatureAttribute}. */
public enum MobType {

    UNDEFINED(EnumCreatureAttribute.UNDEFINED),
    UNDEAD(EnumCreatureAttribute.UNDEAD),
    ARTHROPOD(EnumCreatureAttribute.ARTHROPOD),
    ILLAGER(EnumCreatureAttribute.UNDEFINED),
    WATER(EnumCreatureAttribute.UNDEFINED);

    private final EnumCreatureAttribute legacy;

    MobType(EnumCreatureAttribute legacy) {
        this.legacy = legacy;
    }

    public EnumCreatureAttribute toVanilla() {
        return legacy;
    }

    public static MobType of(EnumCreatureAttribute a) {
        if (a == EnumCreatureAttribute.UNDEAD) return UNDEAD;
        if (a == EnumCreatureAttribute.ARTHROPOD) return ARTHROPOD;
        return UNDEFINED;
    }
}
