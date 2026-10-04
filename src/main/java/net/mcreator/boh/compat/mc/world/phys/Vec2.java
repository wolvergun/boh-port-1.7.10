package net.mcreator.boh.compat.mc.world.phys;

public class Vec2 {
    public static final Vec2 ZERO = new Vec2(0.0F, 0.0F);
    public final float x;
    public final float y;

    public Vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vec2 scale(float f) {
        return new Vec2(this.x * f, this.y * f);
    }

    public Vec2 add(Vec2 o) {
        return new Vec2(this.x + o.x, this.y + o.y);
    }
}
