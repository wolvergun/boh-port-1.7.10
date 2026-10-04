package net.mcreator.boh.compat.mojang.math;

public class Vector3f {
    public float x;
    public float y;
    public float z;

    public Vector3f() {
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float x() {
        return this.x;
    }

    public float y() {
        return this.y;
    }

    public float z() {
        return this.z;
    }

    public Vector3f set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vector3f mul(float f) {
        this.x *= f;
        this.y *= f;
        this.z *= f;
        return this;
    }

    public Vector3f lerp(Vector3f o, float t) {
        this.x = this.x + (o.x - this.x) * t;
        this.y = this.y + (o.y - this.y) * t;
        this.z = this.z + (o.z - this.z) * t;
        return this;
    }
}
