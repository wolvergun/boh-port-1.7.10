package net.mcreator.boh.compat.mojang.blaze3d.vertex;

public final class Tesselator {
    private static final Tesselator INSTANCE = new Tesselator();
    private final BufferBuilder builder = new BufferBuilder();

    public static Tesselator getInstance() {
        return INSTANCE;
    }

    public BufferBuilder getBuilder() {
        return this.builder;
    }

    public void end() {
        BufferUploader.drawWithShader(this.builder.end());
    }
}
