package net.mcreator.boh.compat.mc.world;

public enum BossBarColor {

    PINK(0xEC00B8),
    BLUE(0x00B7EC),
    RED(0xEC3500),
    GREEN(0x1DEC00),
    YELLOW(0xE9EC00),
    PURPLE(0x7B00EC),
    WHITE(0xECECEC);

    public final int rgb;

    BossBarColor(int rgb) {
        this.rgb = rgb;
    }
}
