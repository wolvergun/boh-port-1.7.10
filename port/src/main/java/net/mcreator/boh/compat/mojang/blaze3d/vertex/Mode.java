package net.mcreator.boh.compat.mojang.blaze3d.vertex;

import org.lwjgl.opengl.GL11;

/** 1.20 VertexFormat.Mode. */
public enum Mode {

    LINES(GL11.GL_LINES),
    LINE_STRIP(GL11.GL_LINE_STRIP),
    DEBUG_LINES(GL11.GL_LINES),
    DEBUG_LINE_STRIP(GL11.GL_LINE_STRIP),
    TRIANGLES(GL11.GL_TRIANGLES),
    TRIANGLE_STRIP(GL11.GL_TRIANGLE_STRIP),
    TRIANGLE_FAN(GL11.GL_TRIANGLE_FAN),
    QUADS(GL11.GL_QUADS);

    public final int gl;

    Mode(int gl) {
        this.gl = gl;
    }
}
