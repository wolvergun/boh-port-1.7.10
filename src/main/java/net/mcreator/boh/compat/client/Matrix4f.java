package net.mcreator.boh.compat.client;

public final class Matrix4f {
    public float m00;
    public float m01;
    public float m02;
    public float m03;
    public float m10;
    public float m11;
    public float m12;
    public float m13;
    public float m20;
    public float m21;
    public float m22;
    public float m23;
    public float m30;
    public float m31;
    public float m32;
    public float m33;

    public Matrix4f() {
        this.identity();
    }

    public Matrix4f(Matrix4f o) {
        this.set(o);
    }

    public Matrix4f identity() {
        this.m00 = this.m11 = this.m22 = this.m33 = 1.0F;
        this.m01 = this.m02 = this.m03 = this.m10 = this.m12 = this.m13 = this.m20 = this.m21 = this.m23 = this.m30 = this.m31 = this.m32 = 0.0F;
        return this;
    }

    public Matrix4f set(Matrix4f o) {
        this.m00 = o.m00;
        this.m01 = o.m01;
        this.m02 = o.m02;
        this.m03 = o.m03;
        this.m10 = o.m10;
        this.m11 = o.m11;
        this.m12 = o.m12;
        this.m13 = o.m13;
        this.m20 = o.m20;
        this.m21 = o.m21;
        this.m22 = o.m22;
        this.m23 = o.m23;
        this.m30 = o.m30;
        this.m31 = o.m31;
        this.m32 = o.m32;
        this.m33 = o.m33;
        return this;
    }

    public Matrix4f mul(Matrix4f o) {
        float a00 = this.m00 * o.m00 + this.m01 * o.m10 + this.m02 * o.m20 + this.m03 * o.m30;
        float a01 = this.m00 * o.m01 + this.m01 * o.m11 + this.m02 * o.m21 + this.m03 * o.m31;
        float a02 = this.m00 * o.m02 + this.m01 * o.m12 + this.m02 * o.m22 + this.m03 * o.m32;
        float a03 = this.m00 * o.m03 + this.m01 * o.m13 + this.m02 * o.m23 + this.m03 * o.m33;
        float a10 = this.m10 * o.m00 + this.m11 * o.m10 + this.m12 * o.m20 + this.m13 * o.m30;
        float a11 = this.m10 * o.m01 + this.m11 * o.m11 + this.m12 * o.m21 + this.m13 * o.m31;
        float a12 = this.m10 * o.m02 + this.m11 * o.m12 + this.m12 * o.m22 + this.m13 * o.m32;
        float a13 = this.m10 * o.m03 + this.m11 * o.m13 + this.m12 * o.m23 + this.m13 * o.m33;
        float a20 = this.m20 * o.m00 + this.m21 * o.m10 + this.m22 * o.m20 + this.m23 * o.m30;
        float a21 = this.m20 * o.m01 + this.m21 * o.m11 + this.m22 * o.m21 + this.m23 * o.m31;
        float a22 = this.m20 * o.m02 + this.m21 * o.m12 + this.m22 * o.m22 + this.m23 * o.m32;
        float a23 = this.m20 * o.m03 + this.m21 * o.m13 + this.m22 * o.m23 + this.m23 * o.m33;
        float a30 = this.m30 * o.m00 + this.m31 * o.m10 + this.m32 * o.m20 + this.m33 * o.m30;
        float a31 = this.m30 * o.m01 + this.m31 * o.m11 + this.m32 * o.m21 + this.m33 * o.m31;
        float a32 = this.m30 * o.m02 + this.m31 * o.m12 + this.m32 * o.m22 + this.m33 * o.m32;
        float a33 = this.m30 * o.m03 + this.m31 * o.m13 + this.m32 * o.m23 + this.m33 * o.m33;
        this.m00 = a00;
        this.m01 = a01;
        this.m02 = a02;
        this.m03 = a03;
        this.m10 = a10;
        this.m11 = a11;
        this.m12 = a12;
        this.m13 = a13;
        this.m20 = a20;
        this.m21 = a21;
        this.m22 = a22;
        this.m23 = a23;
        this.m30 = a30;
        this.m31 = a31;
        this.m32 = a32;
        this.m33 = a33;
        return this;
    }

    public Matrix4f translate(float x, float y, float z) {
        this.m03 = this.m03 + (this.m00 * x + this.m01 * y + this.m02 * z);
        this.m13 = this.m13 + (this.m10 * x + this.m11 * y + this.m12 * z);
        this.m23 = this.m23 + (this.m20 * x + this.m21 * y + this.m22 * z);
        this.m33 = this.m33 + (this.m30 * x + this.m31 * y + this.m32 * z);
        return this;
    }

    public Matrix4f scale(float x, float y, float z) {
        this.m00 *= x;
        this.m10 *= x;
        this.m20 *= x;
        this.m30 *= x;
        this.m01 *= y;
        this.m11 *= y;
        this.m21 *= y;
        this.m31 *= y;
        this.m02 *= z;
        this.m12 *= z;
        this.m22 *= z;
        this.m32 *= z;
        return this;
    }

    public Matrix4f rotate(Quaternionf q) {
        return this.mul(q.toMatrix4());
    }

    public float transformX(float x, float y, float z) {
        return this.m00 * x + this.m01 * y + this.m02 * z + this.m03;
    }

    public float transformY(float x, float y, float z) {
        return this.m10 * x + this.m11 * y + this.m12 * z + this.m13;
    }

    public float transformZ(float x, float y, float z) {
        return this.m20 * x + this.m21 * y + this.m22 * z + this.m23;
    }
}
