package net.mcreator.boh.compat.mojang.blaze3d.vertex;

import net.mcreator.boh.compat.mc.client.renderer.ShaderInstance;
import net.mcreator.boh.compat.mojang.blaze3d.systems.RenderSystem;

import org.lwjgl.opengl.GL11;

/** 1.20 BufferUploader: draws a finished buffer with the current "shader" (fixed-function attributes). */
public final class BufferUploader {

    private BufferUploader() {}

    public static void drawWithShader(RenderedBuffer b) {
        draw(b, RenderSystem.shader);
    }

    public static void draw(RenderedBuffer b) {
        draw(b, RenderSystem.shader);
    }

    static void draw(RenderedBuffer b, ShaderInstance shader) {
        if (b == null || b.count == 0) return;
        boolean tex = shader.textured && b.hasUv;
        boolean col = shader.colored && b.hasColor;
        boolean texWas = GL11.glIsEnabled(GL11.GL_TEXTURE_2D);
        if (tex) GL11.glEnable(GL11.GL_TEXTURE_2D);
        else GL11.glDisable(GL11.GL_TEXTURE_2D);
        float[] c = RenderSystem.COLOR;
        GL11.glBegin(b.mode.gl);
        float[] d = b.data;
        for (int i = 0; i < b.count; i++) {
            int o = i * BufferBuilder.STRIDE;
            if (col) GL11.glColor4f(d[o + 5] * c[0], d[o + 6] * c[1], d[o + 7] * c[2], d[o + 8] * c[3]);
            if (tex) GL11.glTexCoord2f(d[o + 3], d[o + 4]);
            GL11.glVertex3f(d[o], d[o + 1], d[o + 2]);
        }
        GL11.glEnd();
        if (col) GL11.glColor4f(c[0], c[1], c[2], c[3]);
        if (texWas) GL11.glEnable(GL11.GL_TEXTURE_2D);
        else GL11.glDisable(GL11.GL_TEXTURE_2D);
    }
}
