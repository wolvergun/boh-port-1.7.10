package net.mcreator.boh.compat.client;

public final class Quaternionf {
    public float x;
    public float y;
    public float z;
    public float w;

    public Quaternionf() {
        this.w = 1.0F;
    }

    public Quaternionf(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public static Quaternionf axisAngle(float ax, float ay, float az, float radians) {
        float s = (float)Math.sin(radians / 2.0F);
        return new Quaternionf(ax * s, ay * s, az * s, (float)Math.cos(radians / 2.0F));
    }

    public Quaternionf rotationX(float rad) {
        Quaternionf q = axisAngle(1.0F, 0.0F, 0.0F, rad);
        this.x = q.x;
        this.y = q.y;
        this.z = q.z;
        this.w = q.w;
        return this;
    }

    public Quaternionf rotationY(float rad) {
        Quaternionf q = axisAngle(0.0F, 1.0F, 0.0F, rad);
        this.x = q.x;
        this.y = q.y;
        this.z = q.z;
        this.w = q.w;
        return this;
    }

    public Quaternionf rotationZ(float rad) {
        Quaternionf q = axisAngle(0.0F, 0.0F, 1.0F, rad);
        this.x = q.x;
        this.y = q.y;
        this.z = q.z;
        this.w = q.w;
        return this;
    }

    public Quaternionf rotationXYZ(float ax, float ay, float az) {
        Quaternionf q = axisAngle(1.0F, 0.0F, 0.0F, ax).mul(axisAngle(0.0F, 1.0F, 0.0F, ay)).mul(axisAngle(0.0F, 0.0F, 1.0F, az));
        this.x = q.x;
        this.y = q.y;
        this.z = q.z;
        this.w = q.w;
        return this;
    }

    public Quaternionf rotateX(float rad) {
        return this.mul(axisAngle(1.0F, 0.0F, 0.0F, rad));
    }

    public Quaternionf rotateY(float rad) {
        return this.mul(axisAngle(0.0F, 1.0F, 0.0F, rad));
    }

    public Quaternionf rotateZ(float rad) {
        return this.mul(axisAngle(0.0F, 0.0F, 1.0F, rad));
    }

    public Quaternionf mul(Quaternionf q) {
        float nx = this.w * q.x + this.x * q.w + this.y * q.z - this.z * q.y;
        float ny = this.w * q.y - this.x * q.z + this.y * q.w + this.z * q.x;
        float nz = this.w * q.z + this.x * q.y - this.y * q.x + this.z * q.w;
        float nw = this.w * q.w - this.x * q.x - this.y * q.y - this.z * q.z;
        this.x = nx;
        this.y = ny;
        this.z = nz;
        this.w = nw;
        return this;
    }

    public Matrix3f toMatrix3() {
        Matrix3f m = new Matrix3f();
        float xx = this.x * this.x;
        float yy = this.y * this.y;
        float zz = this.z * this.z;
        float xy = this.x * this.y;
        float xz = this.x * this.z;
        float yz = this.y * this.z;
        float wx = this.w * this.x;
        float wy = this.w * this.y;
        float wz = this.w * this.z;
        m.m00 = 1.0F - 2.0F * (yy + zz);
        m.m01 = 2.0F * (xy - wz);
        m.m02 = 2.0F * (xz + wy);
        m.m10 = 2.0F * (xy + wz);
        m.m11 = 1.0F - 2.0F * (xx + zz);
        m.m12 = 2.0F * (yz - wx);
        m.m20 = 2.0F * (xz - wy);
        m.m21 = 2.0F * (yz + wx);
        m.m22 = 1.0F - 2.0F * (xx + yy);
        return m;
    }

    public Matrix4f toMatrix4() {
        Matrix3f r = this.toMatrix3();
        Matrix4f m = new Matrix4f();
        m.m00 = r.m00;
        m.m01 = r.m01;
        m.m02 = r.m02;
        m.m10 = r.m10;
        m.m11 = r.m11;
        m.m12 = r.m12;
        m.m20 = r.m20;
        m.m21 = r.m21;
        m.m22 = r.m22;
        return m;
    }
}
