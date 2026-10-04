package net.mcreator.boh.geo;

import java.util.HashMap;
import java.util.Map;

public class AnimatableInstanceCache {
    protected final GeoAnimatable animatable;
    private final Map<Long, AnimatableManager<?>> managers = new HashMap<>();

    public AnimatableInstanceCache(GeoAnimatable animatable) {
        this.animatable = animatable;
    }

    public <T extends GeoAnimatable> AnimatableManager<T> getManagerForId(long uniqueId) {
        AnimatableManager<?> m = this.managers.get(uniqueId);
        if (m == null) {
            m = new AnimatableManager(this.animatable);
            this.managers.put(uniqueId, m);
        }

        return (AnimatableManager<T>)m;
    }
}
