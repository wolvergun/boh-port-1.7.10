package net.mcreator.boh.compat.mc.client;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.Entity;

public final class Camera {
    private final Entity entity;
    private final Vec3 position;

    public Camera(Entity entity, Vec3 position) {
        this.entity = entity;
        this.position = position;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public Vec3 getPosition() {
        return this.position;
    }

    public BlockPos getBlockPosition() {
        return BlockPos.containing(this.position.x, this.position.y, this.position.z);
    }

    public boolean isInitialized() {
        return this.entity != null;
    }

    public float getYRot() {
        return this.entity == null ? 0.0F : this.entity.rotationYaw;
    }

    public float getXRot() {
        return this.entity == null ? 0.0F : this.entity.rotationPitch;
    }
}
