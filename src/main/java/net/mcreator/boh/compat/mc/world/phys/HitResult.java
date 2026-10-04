package net.mcreator.boh.compat.mc.world.phys;

import net.minecraft.entity.Entity;

public abstract class HitResult {
    protected final Vec3 location;

    protected HitResult(Vec3 location) {
        this.location = location;
    }

    public Vec3 getLocation() {
        return this.location;
    }

    public abstract HitResultType getType();

    public double distanceTo(Entity e) {
        double dx = this.location.x - e.posX;
        double dy = this.location.y - e.posY;
        double dz = this.location.z - e.posZ;
        return dx * dx + dy * dy + dz * dz;
    }
}
