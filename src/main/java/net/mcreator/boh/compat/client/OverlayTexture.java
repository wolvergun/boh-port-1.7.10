package net.mcreator.boh.compat.client;

public final class OverlayTexture {
    public static final int NO_OVERLAY = pack(0, 10);

    private OverlayTexture() {
    }

    public static int pack(int u, int v) {
        return u | v << 16;
    }

    public static int pack(float whiteOverlay, boolean hurt) {
        return pack((int)(whiteOverlay * 15.0F), hurt ? 3 : 10);
    }

    public static int u(float whiteOverlay) {
        return (int)(whiteOverlay * 15.0F);
    }

    public static int v(boolean hurt) {
        return hurt ? 3 : 10;
    }

    static boolean isHurt(int packed) {
        return packed >>> 16 < 8;
    }

    static float white(int packed) {
        return (packed & 65535) / 15.0F;
    }
}
