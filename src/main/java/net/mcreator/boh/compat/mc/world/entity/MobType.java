package net.mcreator.boh.compat.mc.world.entity;

import net.minecraft.entity.EnumCreatureAttribute;

public enum MobType {
    UNDEFINED(EnumCreatureAttribute.UNDEFINED),
    UNDEAD(EnumCreatureAttribute.UNDEAD),
    ARTHROPOD(EnumCreatureAttribute.ARTHROPOD),
    ILLAGER(EnumCreatureAttribute.UNDEFINED),
    WATER(EnumCreatureAttribute.UNDEFINED);

    private final EnumCreatureAttribute legacy;

    private MobType(EnumCreatureAttribute legacy) {
        this.legacy = legacy;
    }

    public EnumCreatureAttribute toVanilla() {
        return this.legacy;
    }

    public static MobType of(EnumCreatureAttribute a) {
        if (a == EnumCreatureAttribute.UNDEAD) {
            return UNDEAD;
        } else {
            return a == EnumCreatureAttribute.ARTHROPOD ? ARTHROPOD : UNDEFINED;
        }
    }
}
