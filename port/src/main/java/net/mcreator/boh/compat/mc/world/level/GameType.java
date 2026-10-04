package net.mcreator.boh.compat.mc.world.level;

import net.minecraft.world.WorldSettings;

/** 1.20 GameType; SPECTATOR does not exist in 1.7.10 and maps to adventure. */
public enum GameType {

    SURVIVAL(WorldSettings.GameType.SURVIVAL),
    CREATIVE(WorldSettings.GameType.CREATIVE),
    ADVENTURE(WorldSettings.GameType.ADVENTURE),
    SPECTATOR(WorldSettings.GameType.ADVENTURE);

    private final WorldSettings.GameType legacy;

    GameType(WorldSettings.GameType legacy) {
        this.legacy = legacy;
    }

    public WorldSettings.GameType toVanilla() {
        return legacy;
    }

    public static GameType of(WorldSettings.GameType t) {
        if (t == WorldSettings.GameType.CREATIVE) return CREATIVE;
        if (t == WorldSettings.GameType.ADVENTURE) return ADVENTURE;
        return SURVIVAL;
    }

    public boolean isCreative() {
        return this == CREATIVE;
    }

    public boolean isSurvival() {
        return this == SURVIVAL || this == ADVENTURE;
    }

    public String getName() {
        return name().toLowerCase();
    }
}
