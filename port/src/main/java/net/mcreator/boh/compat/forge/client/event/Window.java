package net.mcreator.boh.compat.forge.client.event;

/** Scaled GUI size snapshot (1.20 Window). */
public final class Window {

    private final int w, h;

    public Window(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public int getGuiScaledWidth() {
        return w;
    }

    public int getGuiScaledHeight() {
        return h;
    }

    public int getScreenWidth() {
        return w;
    }

    public int getScreenHeight() {
        return h;
    }
}
