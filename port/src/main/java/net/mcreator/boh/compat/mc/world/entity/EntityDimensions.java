package net.mcreator.boh.compat.mc.world.entity;

public class EntityDimensions {

    public final float width;
    public final float height;
    public final boolean fixed;

    public EntityDimensions(float width, float height, boolean fixed) {
        this.width = width;
        this.height = height;
        this.fixed = fixed;
    }

    public static EntityDimensions scalable(float w, float h) {
        return new EntityDimensions(w, h, false);
    }

    public static EntityDimensions fixed(float w, float h) {
        return new EntityDimensions(w, h, true);
    }

    public EntityDimensions scale(float f) {
        return scale(f, f);
    }

    public EntityDimensions scale(float fw, float fh) {
        return fixed || (fw == 1 && fh == 1) ? this : new EntityDimensions(width * fw, height * fh, false);
    }

    @Override
    public String toString() {
        return "EntityDimensions w=" + width + ", h=" + height;
    }
}
