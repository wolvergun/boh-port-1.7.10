package net.mcreator.boh.compat.client;

/** Stand-in for org.joml.Quaternionf, enough for the rotations the mod builds. */
public final class Quaternionf {

    public float x, y, z, w;

    public Quaternionf() {
        w = 1;
    }

    public Quaternionf(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public static Quaternionf axisAngle(float ax, float ay, float az, float radians) {
        float s = (float) Math.sin(radians / 2);
        return new Quaternionf(ax * s, ay * s, az * s, (float) Math.cos(radians / 2));
    }

    public Quaternionf rotationX(float rad) {
        Quaternionf q = axisAngle(1, 0, 0, rad);
        x = q.x; y = q.y; z = q.z; w = q.w;
        return this;
    }

    public Quaternionf rotationY(float rad) {
        Quaternionf q = axisAngle(0, 1, 0, rad);
        x = q.x; y = q.y; z = q.z; w = q.w;
        return this;
    }

    public Quaternionf rotationZ(float rad) {
        Quaternionf q = axisAngle(0, 0, 1, rad);
        x = q.x; y = q.y; z = q.z; w = q.w;
        return this;
    }

    public Quaternionf rotationXYZ(float ax, float ay, float az) {
        Quaternionf q = axisAngle(1, 0, 0, ax).mul(axisAngle(0, 1, 0, ay)).mul(axisAngle(0, 0, 1, az));
        x = q.x; y = q.y; z = q.z; w = q.w;
        return this;
    }

    public Quaternionf rotateX(float rad) {
        return mul(axisAngle(1, 0, 0, rad));
    }

    public Quaternionf rotateY(float rad) {
        return mul(axisAngle(0, 1, 0, rad));
    }

    public Quaternionf rotateZ(float rad) {
        return mul(axisAngle(0, 0, 1, rad));
    }

    public Quaternionf mul(Quaternionf q) {
        float nx = w * q.x + x * q.w + y * q.z - z * q.y;
        float ny = w * q.y - x * q.z + y * q.w + z * q.x;
        float nz = w * q.z + x * q.y - y * q.x + z * q.w;
        float nw = w * q.w - x * q.x - y * q.y - z * q.z;
        x = nx; y = ny; z = nz; w = nw;
        return this;
    }

    public Matrix3f toMatrix3() {
        Matrix3f m = new Matrix3f();
        float xx = x * x, yy = y * y, zz = z * z, xy = x * y, xz = x * z, yz = y * z, wx = w * x, wy = w * y, wz = w * z;
        m.m00 = 1 - 2 * (yy + zz); m.m01 = 2 * (xy - wz);     m.m02 = 2 * (xz + wy);
        m.m10 = 2 * (xy + wz);     m.m11 = 1 - 2 * (xx + zz); m.m12 = 2 * (yz - wx);
        m.m20 = 2 * (xz - wy);     m.m21 = 2 * (yz + wx);     m.m22 = 1 - 2 * (xx + yy);
        return m;
    }

    public Matrix4f toMatrix4() {
        Matrix3f r = toMatrix3();
        Matrix4f m = new Matrix4f();
        m.m00 = r.m00; m.m01 = r.m01; m.m02 = r.m02;
        m.m10 = r.m10; m.m11 = r.m11; m.m12 = r.m12;
        m.m20 = r.m20; m.m21 = r.m21; m.m22 = r.m22;
        return m;
    }
}
