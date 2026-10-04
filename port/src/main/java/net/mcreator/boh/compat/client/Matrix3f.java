package net.mcreator.boh.compat.client;

/** Normal matrix companion of {@link Matrix4f}; only rotation and scale are tracked. */
public final class Matrix3f {

    public float m00, m01, m02;
    public float m10, m11, m12;
    public float m20, m21, m22;

    public Matrix3f() {
        identity();
    }

    public Matrix3f(Matrix3f o) {
        set(o);
    }

    public Matrix3f identity() {
        m00 = m11 = m22 = 1;
        m01 = m02 = m10 = m12 = m20 = m21 = 0;
        return this;
    }

    public Matrix3f set(Matrix3f o) {
        m00 = o.m00; m01 = o.m01; m02 = o.m02;
        m10 = o.m10; m11 = o.m11; m12 = o.m12;
        m20 = o.m20; m21 = o.m21; m22 = o.m22;
        return this;
    }

    public Matrix3f mul(Matrix3f o) {
        float a00 = m00 * o.m00 + m01 * o.m10 + m02 * o.m20;
        float a01 = m00 * o.m01 + m01 * o.m11 + m02 * o.m21;
        float a02 = m00 * o.m02 + m01 * o.m12 + m02 * o.m22;
        float a10 = m10 * o.m00 + m11 * o.m10 + m12 * o.m20;
        float a11 = m10 * o.m01 + m11 * o.m11 + m12 * o.m21;
        float a12 = m10 * o.m02 + m11 * o.m12 + m12 * o.m22;
        float a20 = m20 * o.m00 + m21 * o.m10 + m22 * o.m20;
        float a21 = m20 * o.m01 + m21 * o.m11 + m22 * o.m21;
        float a22 = m20 * o.m02 + m21 * o.m12 + m22 * o.m22;
        m00 = a00; m01 = a01; m02 = a02;
        m10 = a10; m11 = a11; m12 = a12;
        m20 = a20; m21 = a21; m22 = a22;
        return this;
    }

    public Matrix3f scale(float x, float y, float z) {
        // inverse-transpose of a scale keeps normals perpendicular; sign is what matters for lighting
        float ix = x == 0 ? 0 : 1 / x, iy = y == 0 ? 0 : 1 / y, iz = z == 0 ? 0 : 1 / z;
        m00 *= ix; m10 *= ix; m20 *= ix;
        m01 *= iy; m11 *= iy; m21 *= iy;
        m02 *= iz; m12 *= iz; m22 *= iz;
        return this;
    }

    public Matrix3f rotate(Quaternionf q) {
        return mul(q.toMatrix3());
    }
}
