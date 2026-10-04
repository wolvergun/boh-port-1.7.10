package net.mcreator.boh.compat.mojang.blaze3d.vertex;

import java.nio.FloatBuffer;
import net.mcreator.boh.compat.client.Matrix4f;
import net.mcreator.boh.compat.mc.client.renderer.ShaderInstance;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class VertexBuffer {
    private RenderedBuffer buffer;
    private static final FloatBuffer MATRIX = BufferUtils.createFloatBuffer(16);

    public VertexBuffer(Usage usage) {
    }

    public VertexBuffer() {
    }

    public void bind() {
    }

    public static void unbind() {
    }

    public void upload(RenderedBuffer b) {
        this.buffer = b;
    }

    public void draw() {
        BufferUploader.drawWithShader(this.buffer);
    }

    public void drawWithShader(Matrix4f modelView, Matrix4f projection, ShaderInstance shader) {
        if (this.buffer != null) {
            GL11.glPushMatrix();
            MATRIX.clear();
            MATRIX.put(
                new float[]{
                    modelView.m00,
                    modelView.m10,
                    modelView.m20,
                    modelView.m30,
                    modelView.m01,
                    modelView.m11,
                    modelView.m21,
                    modelView.m31,
                    modelView.m02,
                    modelView.m12,
                    modelView.m22,
                    modelView.m32,
                    modelView.m03,
                    modelView.m13,
                    modelView.m23,
                    modelView.m33
                }
            );
            MATRIX.flip();
            GL11.glMultMatrix(MATRIX);
            BufferUploader.draw(this.buffer, shader == null ? ShaderInstance.POSITION : shader);
            GL11.glPopMatrix();
        }
    }

    public void close() {
        this.buffer = null;
    }
}
