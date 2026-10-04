package net.mcreator.boh.compat.mojang.blaze3d.vertex;

public enum Mode {
    LINES(1),
    LINE_STRIP(3),
    DEBUG_LINES(1),
    DEBUG_LINE_STRIP(3),
    TRIANGLES(4),
    TRIANGLE_STRIP(5),
    TRIANGLE_FAN(6),
    QUADS(7);

    public final int gl;

    private Mode(int gl) {
        this.gl = gl;
    }
}
