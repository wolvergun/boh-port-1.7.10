package net.mcreator.boh.compat.forge.client.event;

public final class Window {
    private final int w;
    private final int h;

    public Window(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public int getGuiScaledWidth() {
        return this.w;
    }

    public int getGuiScaledHeight() {
        return this.h;
    }

    public int getScreenWidth() {
        return this.w;
    }

    public int getScreenHeight() {
        return this.h;
    }
}
