package net.mcreator.boh.compat.mc.world.entity;

import java.util.function.Function;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class Builder<T extends Entity> {
    private final EntityType.EntityFactory<T> factory;
    private final Class<T> entityClass;
    private final MobCategory category;
    private float width = 0.6F;
    private float height = 1.8F;
    private boolean fireImmune;
    private int trackingRange = 80;
    private int updateInterval = 3;
    private boolean velocityUpdates = true;

    private Builder(EntityType.EntityFactory<T> factory, Class<T> entityClass, MobCategory category) {
        this.factory = factory;
        this.entityClass = entityClass;
        this.category = category;
    }

    public static <T extends Entity> Builder<T> of(EntityType.EntityFactory<T> factory, Class<T> entityClass, MobCategory category) {
        return new Builder<>(factory, entityClass, category);
    }

    public Builder<T> sized(float w, float h) {
        this.width = w;
        this.height = h;
        return this;
    }

    public Builder<T> fireImmune() {
        this.fireImmune = true;
        return this;
    }

    public Builder<T> setTrackingRange(int range) {
        this.trackingRange = range;
        return this;
    }

    public Builder<T> clientTrackingRange(int range) {
        this.trackingRange = range * 16;
        return this;
    }

    public Builder<T> setUpdateInterval(int interval) {
        this.updateInterval = interval;
        return this;
    }

    public Builder<T> updateInterval(int interval) {
        this.updateInterval = interval;
        return this;
    }

    public Builder<T> setShouldReceiveVelocityUpdates(boolean v) {
        this.velocityUpdates = v;
        return this;
    }

    public Builder<T> setCustomClientFactory(Function<World, T> clientFactory) {
        return this;
    }

    public Builder<T> noSummon() {
        return this;
    }

    public Builder<T> noSave() {
        return this;
    }

    public Builder<T> canSpawnFarFromPlayer() {
        return this;
    }

    public EntityType<T> build(String name) {
        return new EntityType<>(
            this.factory,
            this.entityClass,
            this.category,
            this.width,
            this.height,
            this.fireImmune,
            this.trackingRange,
            this.updateInterval,
            this.velocityUpdates
        );
    }
}
