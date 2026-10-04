package net.mcreator.boh.compat.client;

/** Packed light helpers. 1.7.10 brightness ints use the same block/sky nibble layout. */
public final class LightTexture {

    public static final int FULL_BRIGHT = 0xF000F0;
    public static final int FULL_SKY = 0xF00000;
    public static final int FULL_BLOCK = 0xF0;

    private LightTexture() {}

    public static int pack(int block, int sky) {
        return block << 4 | sky << 20;
    }

    public static int block(int packed) {
        return (packed & 0xFFFF) >> 4;
    }

    public static int sky(int packed) {
        return (packed >> 20) & 0xFFFF;
    }
}
