package net.mcreator.boh.compat.client;

/**
 * 1.7.10 has no overlay texture; the packed value is kept so {@link TessellatorConsumer} can tint vertices
 * red the way the vanilla hurt flash does.
 */
public final class OverlayTexture {

    public static final int NO_OVERLAY = pack(0, 10);

    private OverlayTexture() {}

    public static int pack(int u, int v) {
        return u | v << 16;
    }

    public static int pack(float whiteOverlay, boolean hurt) {
        return pack((int) (whiteOverlay * 15f), hurt ? 3 : 10);
    }

    public static int u(float whiteOverlay) {
        return (int) (whiteOverlay * 15f);
    }

    public static int v(boolean hurt) {
        return hurt ? 3 : 10;
    }

    static boolean isHurt(int packed) {
        return (packed >>> 16) < 8;
    }

    static float white(int packed) {
        return (packed & 0xFFFF) / 15f;
    }
}
