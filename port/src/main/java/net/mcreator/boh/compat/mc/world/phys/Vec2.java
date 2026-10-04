package net.mcreator.boh.compat.mc.world.phys;

/** 1.20 Vec2 (used for entity rotation vectors). */
public class Vec2 {

    public static final Vec2 ZERO = new Vec2(0, 0);

    public final float x, y;

    public Vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vec2 scale(float f) {
        return new Vec2(x * f, y * f);
    }

    public Vec2 add(Vec2 o) {
        return new Vec2(x + o.x, y + o.y);
    }
}
