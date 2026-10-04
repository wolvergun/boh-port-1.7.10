package net.mcreator.boh.compat.mc.world.entity;

import java.util.function.Function;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/** 1.20 EntityType.Builder. */
public class Builder<T extends Entity> {

    private final EntityType.EntityFactory<T> factory;
    private final Class<T> entityClass;
    private final MobCategory category;
    private float width = 0.6f, height = 1.8f;
    private boolean fireImmune;
    private int trackingRange = 80, updateInterval = 3;
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
        width = w;
        height = h;
        return this;
    }

    public Builder<T> fireImmune() {
        fireImmune = true;
        return this;
    }

    public Builder<T> setTrackingRange(int range) {
        trackingRange = range;
        return this;
    }

    public Builder<T> clientTrackingRange(int range) {
        trackingRange = range * 16;
        return this;
    }

    public Builder<T> setUpdateInterval(int interval) {
        updateInterval = interval;
        return this;
    }

    public Builder<T> updateInterval(int interval) {
        updateInterval = interval;
        return this;
    }

    public Builder<T> setShouldReceiveVelocityUpdates(boolean v) {
        velocityUpdates = v;
        return this;
    }

    /** 1.7.10 always constructs entities through their (World) constructor; the client factory is not needed. */
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
        return new EntityType<>(factory, entityClass, category, width, height, fireImmune, trackingRange, updateInterval,
            velocityUpdates);
    }
}
