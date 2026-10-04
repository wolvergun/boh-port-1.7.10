package net.mcreator.boh.compat.client;

public interface VertexConsumer {
    VertexConsumer vertex(double var1, double var3, double var5);

    VertexConsumer color(float var1, float var2, float var3, float var4);

    VertexConsumer uv(float var1, float var2);

    VertexConsumer overlayCoords(int var1);

    VertexConsumer uv2(int var1);

    VertexConsumer normal(float var1, float var2, float var3);

    void endVertex();

    default VertexConsumer color(int r, int g, int b, int a) {
        return this.color(r / 255.0F, g / 255.0F, b / 255.0F, a / 255.0F);
    }

    default VertexConsumer color(int argb) {
        return this.color(argb >> 16 & 0xFF, argb >> 8 & 0xFF, argb & 0xFF, argb >>> 24 & 0xFF);
    }

    default VertexConsumer overlayCoords(int u, int v) {
        return this.overlayCoords(u | v << 16);
    }

    default VertexConsumer uv2(int u, int v) {
        return this.uv2(u | v << 16);
    }

    default VertexConsumer vertex(Matrix4f m, float x, float y, float z) {
        return this.vertex(m.transformX(x, y, z), m.transformY(x, y, z), m.transformZ(x, y, z));
    }

    default VertexConsumer normal(Matrix3f m, float x, float y, float z) {
        float nx = m.m00 * x + m.m01 * y + m.m02 * z;
        float ny = m.m10 * x + m.m11 * y + m.m12 * z;
        float nz = m.m20 * x + m.m21 * y + m.m22 * z;
        float len = (float)Math.sqrt(nx * nx + ny * ny + nz * nz);
        if (len > 1.0E-5F) {
            nx /= len;
            ny /= len;
            nz /= len;
        }

        return this.normal(nx, ny, nz);
    }

    default void vertex(float x, float y, float z, float r, float g, float b, float a, float u, float v, int overlay, int light, float nx, float ny, float nz) {
        this.vertex(x, y, z).color(r, g, b, a).uv(u, v).overlayCoords(overlay).uv2(light).normal(nx, ny, nz).endVertex();
    }
}
