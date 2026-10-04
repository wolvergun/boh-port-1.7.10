package net.mcreator.boh.compat.client;

/** Minimal row-major 4x4 float matrix used by the CPU-side {@link PoseStack}. */
public final class Matrix4f {

    public float m00, m01, m02, m03;
    public float m10, m11, m12, m13;
    public float m20, m21, m22, m23;
    public float m30, m31, m32, m33;

    public Matrix4f() {
        identity();
    }

    public Matrix4f(Matrix4f o) {
        set(o);
    }

    public Matrix4f identity() {
        m00 = m11 = m22 = m33 = 1;
        m01 = m02 = m03 = m10 = m12 = m13 = m20 = m21 = m23 = m30 = m31 = m32 = 0;
        return this;
    }

    public Matrix4f set(Matrix4f o) {
        m00 = o.m00; m01 = o.m01; m02 = o.m02; m03 = o.m03;
        m10 = o.m10; m11 = o.m11; m12 = o.m12; m13 = o.m13;
        m20 = o.m20; m21 = o.m21; m22 = o.m22; m23 = o.m23;
        m30 = o.m30; m31 = o.m31; m32 = o.m32; m33 = o.m33;
        return this;
    }

    /** this = this * o */
    public Matrix4f mul(Matrix4f o) {
        float a00 = m00 * o.m00 + m01 * o.m10 + m02 * o.m20 + m03 * o.m30;
        float a01 = m00 * o.m01 + m01 * o.m11 + m02 * o.m21 + m03 * o.m31;
        float a02 = m00 * o.m02 + m01 * o.m12 + m02 * o.m22 + m03 * o.m32;
        float a03 = m00 * o.m03 + m01 * o.m13 + m02 * o.m23 + m03 * o.m33;
        float a10 = m10 * o.m00 + m11 * o.m10 + m12 * o.m20 + m13 * o.m30;
        float a11 = m10 * o.m01 + m11 * o.m11 + m12 * o.m21 + m13 * o.m31;
        float a12 = m10 * o.m02 + m11 * o.m12 + m12 * o.m22 + m13 * o.m32;
        float a13 = m10 * o.m03 + m11 * o.m13 + m12 * o.m23 + m13 * o.m33;
        float a20 = m20 * o.m00 + m21 * o.m10 + m22 * o.m20 + m23 * o.m30;
        float a21 = m20 * o.m01 + m21 * o.m11 + m22 * o.m21 + m23 * o.m31;
        float a22 = m20 * o.m02 + m21 * o.m12 + m22 * o.m22 + m23 * o.m32;
        float a23 = m20 * o.m03 + m21 * o.m13 + m22 * o.m23 + m23 * o.m33;
        float a30 = m30 * o.m00 + m31 * o.m10 + m32 * o.m20 + m33 * o.m30;
        float a31 = m30 * o.m01 + m31 * o.m11 + m32 * o.m21 + m33 * o.m31;
        float a32 = m30 * o.m02 + m31 * o.m12 + m32 * o.m22 + m33 * o.m32;
        float a33 = m30 * o.m03 + m31 * o.m13 + m32 * o.m23 + m33 * o.m33;
        m00 = a00; m01 = a01; m02 = a02; m03 = a03;
        m10 = a10; m11 = a11; m12 = a12; m13 = a13;
        m20 = a20; m21 = a21; m22 = a22; m23 = a23;
        m30 = a30; m31 = a31; m32 = a32; m33 = a33;
        return this;
    }

    public Matrix4f translate(float x, float y, float z) {
        m03 += m00 * x + m01 * y + m02 * z;
        m13 += m10 * x + m11 * y + m12 * z;
        m23 += m20 * x + m21 * y + m22 * z;
        m33 += m30 * x + m31 * y + m32 * z;
        return this;
    }

    public Matrix4f scale(float x, float y, float z) {
        m00 *= x; m10 *= x; m20 *= x; m30 *= x;
        m01 *= y; m11 *= y; m21 *= y; m31 *= y;
        m02 *= z; m12 *= z; m22 *= z; m32 *= z;
        return this;
    }

    public Matrix4f rotate(Quaternionf q) {
        return mul(q.toMatrix4());
    }

    public float transformX(float x, float y, float z) {
        return m00 * x + m01 * y + m02 * z + m03;
    }

    public float transformY(float x, float y, float z) {
        return m10 * x + m11 * y + m12 * z + m13;
    }

    public float transformZ(float x, float y, float z) {
        return m20 * x + m21 * y + m22 * z + m23;
    }
}
