package net.mcreator.boh.compat.mc.world.phys.shapes;

import net.minecraft.entity.Entity;

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
        return this.entity;
    }

    public boolean isDescending() {
        return this.entity != null && this.entity.isSneaking();
    }
}
