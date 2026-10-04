package net.mcreator.boh.compat.mc.world.item;

import net.minecraft.item.EnumRarity;

public enum Rarity {

    COMMON(EnumRarity.common),
    UNCOMMON(EnumRarity.uncommon),
    RARE(EnumRarity.rare),
    EPIC(EnumRarity.epic);

    private final EnumRarity legacy;

    Rarity(EnumRarity legacy) {
        this.legacy = legacy;
    }

    public EnumRarity toVanilla() {
        return legacy;
    }
}
