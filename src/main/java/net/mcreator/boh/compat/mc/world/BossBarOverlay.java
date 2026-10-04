package net.mcreator.boh.compat.mc.world;

public enum BossBarOverlay {
    PROGRESS(0),
    NOTCHED_6(6),
    NOTCHED_10(10),
    NOTCHED_12(12),
    NOTCHED_20(20);

    public final int notches;

    private BossBarOverlay(int notches) {
        this.notches = notches;
    }
}
