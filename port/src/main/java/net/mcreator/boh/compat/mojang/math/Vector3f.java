package net.mcreator.boh.compat.mojang.math;

/** joml Vector3f (lightmap color adjustments). */
public class Vector3f {

    public float x, y, z;

    public Vector3f() {}

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public float z() {
        return z;
    }

    public Vector3f set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vector3f mul(float f) {
        x *= f;
        y *= f;
        z *= f;
        return this;
    }

    public Vector3f lerp(Vector3f o, float t) {
        x += (o.x - x) * t;
        y += (o.y - y) * t;
        z += (o.z - z) * t;
        return this;
    }
}
