package net.mcreator.boh.compat.mc.world.entity;

public enum RemovalReason {
    KILLED(true),
    DISCARDED(true),
    UNLOADED_TO_CHUNK(false),
    UNLOADED_WITH_PLAYER(false),
    CHANGED_DIMENSION(false);

    private final boolean destroy;

    private RemovalReason(boolean destroy) {
        this.destroy = destroy;
    }

    public boolean shouldDestroy() {
        return this.destroy;
    }
}
