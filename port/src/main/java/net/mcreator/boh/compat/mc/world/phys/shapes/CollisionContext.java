package net.mcreator.boh.compat.mc.world.phys.shapes;

import net.minecraft.entity.Entity;

/** 1.20 CollisionContext. */
public class CollisionContext {

    private static final CollisionContext EMPTY = new CollisionContext(null);
    private final Entity entity;

    CollisionContext(Entity entity) {
        this.entity = entity;
    }

    public static CollisionContext empty() {
        return EMPTY;
    }

    public static CollisionContext of(Entity e) {
        return e == null ? EMPTY : new CollisionContext(e);
    }

    public Entity getEntity() {
        return entity;
    }

    public boolean isDescending() {
        return entity != null && entity.isSneaking();
    }
}
