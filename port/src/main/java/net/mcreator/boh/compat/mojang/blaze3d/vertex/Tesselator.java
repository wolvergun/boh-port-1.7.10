package net.mcreator.boh.compat.mojang.blaze3d.vertex;

/** 1.20 Tesselator: holds one BufferBuilder. */
public final class Tesselator {

    private static final Tesselator INSTANCE = new Tesselator();
    private final BufferBuilder builder = new BufferBuilder();

    public static Tesselator getInstance() {
        return INSTANCE;
    }

    public BufferBuilder getBuilder() {
        return builder;
    }

    public void end() {
        BufferUploader.drawWithShader(builder.end());
    }
}
