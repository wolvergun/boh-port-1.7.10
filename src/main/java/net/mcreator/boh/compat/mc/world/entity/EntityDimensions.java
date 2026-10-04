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
        return this.scale(f, f);
    }

    public EntityDimensions scale(float fw, float fh) {
        return !this.fixed && (fw != 1.0F || fh != 1.0F) ? new EntityDimensions(this.width * fw, this.height * fh, false) : this;
    }

    @Override
    public String toString() {
        return "EntityDimensions w=" + this.width + ", h=" + this.height;
    }
}
