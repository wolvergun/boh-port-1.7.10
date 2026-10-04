package net.mcreator.boh.compat.client;

/** Stand-in for com.mojang.blaze3d.vertex.VertexConsumer (builder-style vertex emission). */
public interface VertexConsumer {

    VertexConsumer vertex(double x, double y, double z);

    VertexConsumer color(float r, float g, float b, float a);

    VertexConsumer uv(float u, float v);

    VertexConsumer overlayCoords(int overlay);

    VertexConsumer uv2(int packedLight);

    VertexConsumer normal(float x, float y, float z);

    void endVertex();

    default VertexConsumer color(int r, int g, int b, int a) {
        return color(r / 255f, g / 255f, b / 255f, a / 255f);
    }

    default VertexConsumer color(int argb) {
        return color((argb >> 16) & 255, (argb >> 8) & 255, argb & 255, (argb >>> 24) & 255);
    }

    default VertexConsumer overlayCoords(int u, int v) {
        return overlayCoords(u | v << 16);
    }

    default VertexConsumer uv2(int u, int v) {
        return uv2(u | v << 16);
    }

    default VertexConsumer vertex(Matrix4f m, float x, float y, float z) {
        return vertex(m.transformX(x, y, z), m.transformY(x, y, z), m.transformZ(x, y, z));
    }

    default VertexConsumer normal(Matrix3f m, float x, float y, float z) {
        float nx = m.m00 * x + m.m01 * y + m.m02 * z;
        float ny = m.m10 * x + m.m11 * y + m.m12 * z;
        float nz = m.m20 * x + m.m21 * y + m.m22 * z;
        float len = (float) Math.sqrt(nx * nx + ny * ny + nz * nz);
        if (len > 1.0E-5f) {
            nx /= len;
            ny /= len;
            nz /= len;
        }
        return normal(nx, ny, nz);
    }

    default void vertex(float x, float y, float z, float r, float g, float b, float a, float u, float v, int overlay,
        int light, float nx, float ny, float nz) {
        vertex(x, y, z).color(r, g, b, a).uv(u, v).overlayCoords(overlay).uv2(light).normal(nx, ny, nz).endVertex();
    }
}
