package net.mcreator.boh.compat.mc.client;

/** 1.20 Gui / BossHealthOverlay queries used by the sky renderers. */
public final class BossOverlay {

    public static final BossOverlay INSTANCE = new BossOverlay();

    public BossOverlay getBossOverlay() {
        return this;
    }

    public boolean shouldCreateWorldFog() {
        return false;
    }

    public boolean shouldDarkenScreen() {
        return false;
    }

    public boolean shouldPlayMusic() {
        return false;
    }
}
