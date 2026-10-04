package net.mcreator.boh.compat.mc.client;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.Entity;

/** 1.20 Camera: view entity + interpolated eye position. */
public final class Camera {

    private final Entity entity;
    private final Vec3 position;

    public Camera(Entity entity, Vec3 position) {
        this.entity = entity;
        this.position = position;
    }

    public Entity getEntity() {
        return entity;
    }

    public Vec3 getPosition() {
        return position;
    }

    public BlockPos getBlockPosition() {
        return BlockPos.containing(position.x, position.y, position.z);
    }

    public boolean isInitialized() {
        return entity != null;
    }

    public float getYRot() {
        return entity == null ? 0 : entity.rotationYaw;
    }

    public float getXRot() {
        return entity == null ? 0 : entity.rotationPitch;
    }
}
