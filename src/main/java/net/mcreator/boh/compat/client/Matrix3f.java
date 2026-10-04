package net.mcreator.boh.compat.client;

public final class Matrix3f {
    public float m00;
    public float m01;
    public float m02;
    public float m10;
    public float m11;
    public float m12;
    public float m20;
    public float m21;
    public float m22;

    public Matrix3f() {
        this.identity();
    }

    public Matrix3f(Matrix3f o) {
        this.set(o);
    }

    public Matrix3f identity() {
        this.m00 = this.m11 = this.m22 = 1.0F;
        this.m01 = this.m02 = this.m10 = this.m12 = this.m20 = this.m21 = 0.0F;
        return this;
    }

    public Matrix3f set(Matrix3f o) {
        this.m00 = o.m00;
        this.m01 = o.m01;
        this.m02 = o.m02;
        this.m10 = o.m10;
        this.m11 = o.m11;
        this.m12 = o.m12;
        this.m20 = o.m20;
        this.m21 = o.m21;
        this.m22 = o.m22;
        return this;
    }

    public Matrix3f mul(Matrix3f o) {
        float a00 = this.m00 * o.m00 + this.m01 * o.m10 + this.m02 * o.m20;
        float a01 = this.m00 * o.m01 + this.m01 * o.m11 + this.m02 * o.m21;
        float a02 = this.m00 * o.m02 + this.m01 * o.m12 + this.m02 * o.m22;
        float a10 = this.m10 * o.m00 + this.m11 * o.m10 + this.m12 * o.m20;
        float a11 = this.m10 * o.m01 + this.m11 * o.m11 + this.m12 * o.m21;
        float a12 = this.m10 * o.m02 + this.m11 * o.m12 + this.m12 * o.m22;
        float a20 = this.m20 * o.m00 + this.m21 * o.m10 + this.m22 * o.m20;
        float a21 = this.m20 * o.m01 + this.m21 * o.m11 + this.m22 * o.m21;
        float a22 = this.m20 * o.m02 + this.m21 * o.m12 + this.m22 * o.m22;
        this.m00 = a00;
        this.m01 = a01;
        this.m02 = a02;
        this.m10 = a10;
        this.m11 = a11;
        this.m12 = a12;
        this.m20 = a20;
        this.m21 = a21;
        this.m22 = a22;
        return this;
    }

    public Matrix3f scale(float x, float y, float z) {
        float ix = x == 0.0F ? 0.0F : 1.0F / x;
        float iy = y == 0.0F ? 0.0F : 1.0F / y;
        float iz = z == 0.0F ? 0.0F : 1.0F / z;
        this.m00 *= ix;
        this.m10 *= ix;
        this.m20 *= ix;
        this.m01 *= iy;
        this.m11 *= iy;
        this.m21 *= iy;
        this.m02 *= iz;
        this.m12 *= iz;
        this.m22 *= iz;
        return this;
    }

    public Matrix3f rotate(Quaternionf q) {
        return this.mul(q.toMatrix3());
    }
}
