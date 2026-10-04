package net.mcreator.boh.compat.client;

public final class LightTexture {
    public static final int FULL_BRIGHT = 15728880;
    public static final int FULL_SKY = 15728640;
    public static final int FULL_BLOCK = 240;

    private LightTexture() {
    }

    public static int pack(int block, int sky) {
        return block << 4 | sky << 20;
    }

    public static int block(int packed) {
        return (packed & 65535) >> 4;
    }

    public static int sky(int packed) {
        return packed >> 20 & 65535;
    }
}
