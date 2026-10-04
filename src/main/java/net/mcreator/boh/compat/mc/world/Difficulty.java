package net.mcreator.boh.compat.mc.world;

import net.minecraft.world.EnumDifficulty;

public enum Difficulty {
    PEACEFUL,
    EASY,
    NORMAL,
    HARD;

    public int getId() {
        return this.ordinal();
    }

    public static Difficulty of(EnumDifficulty d) {
        return d == null ? NORMAL : values()[d.getDifficultyId()];
    }

    public EnumDifficulty toVanilla() {
        return EnumDifficulty.getDifficultyEnum(this.ordinal());
    }
}
