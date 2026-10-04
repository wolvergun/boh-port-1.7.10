package net.mcreator.boh.compat.mc.world.level;

public enum GameType {
    SURVIVAL(net.minecraft.world.WorldSettings.GameType.SURVIVAL),
    CREATIVE(net.minecraft.world.WorldSettings.GameType.CREATIVE),
    ADVENTURE(net.minecraft.world.WorldSettings.GameType.ADVENTURE),
    SPECTATOR(net.minecraft.world.WorldSettings.GameType.ADVENTURE);

    private final net.minecraft.world.WorldSettings.GameType legacy;

    private GameType(net.minecraft.world.WorldSettings.GameType legacy) {
        this.legacy = legacy;
    }

    public net.minecraft.world.WorldSettings.GameType toVanilla() {
        return this.legacy;
    }

    public static GameType of(net.minecraft.world.WorldSettings.GameType t) {
        if (t == net.minecraft.world.WorldSettings.GameType.CREATIVE) {
            return CREATIVE;
        } else {
            return t == net.minecraft.world.WorldSettings.GameType.ADVENTURE ? ADVENTURE : SURVIVAL;
        }
    }

    public boolean isCreative() {
        return this == CREATIVE;
    }

    public boolean isSurvival() {
        return this == SURVIVAL || this == ADVENTURE;
    }

    public String getName() {
        return this.name().toLowerCase();
    }
}
