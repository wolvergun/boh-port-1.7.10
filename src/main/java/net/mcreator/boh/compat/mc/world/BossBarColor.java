package net.mcreator.boh.compat.mc.world;

public enum BossBarColor {
    PINK(15466680),
    BLUE(47084),
    RED(15480064),
    GREEN(1960960),
    YELLOW(15330304),
    PURPLE(8061164),
    WHITE(15527148);

    public final int rgb;

    private BossBarColor(int rgb) {
        this.rgb = rgb;
    }
}
