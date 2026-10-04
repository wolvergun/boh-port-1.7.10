package net.mcreator.boh.compat.mc.world.phys;

import net.minecraft.entity.Entity;

public class EntityHitResult extends HitResult {

    private final Entity entity;

    public EntityHitResult(Entity entity) {
        this(entity, new Vec3(entity.posX, entity.posY, entity.posZ));
    }

    public EntityHitResult(Entity entity, Vec3 location) {
        super(location);
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public HitResultType getType() {
        return HitResultType.ENTITY;
    }
}
