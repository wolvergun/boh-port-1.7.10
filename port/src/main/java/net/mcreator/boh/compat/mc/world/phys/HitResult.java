package net.mcreator.boh.compat.mc.world.phys;

/** 1.20 HitResult. */
public abstract class HitResult {

    protected final Vec3 location;

    protected HitResult(Vec3 location) {
        this.location = location;
    }

    public Vec3 getLocation() {
        return location;
    }

    public abstract HitResultType getType();

    public double distanceTo(net.minecraft.entity.Entity e) {
        double dx = location.x - e.posX, dy = location.y - e.posY, dz = location.z - e.posZ;
        return dx * dx + dy * dy + dz * dz;
    }
}
