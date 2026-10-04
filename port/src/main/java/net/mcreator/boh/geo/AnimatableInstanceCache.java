package net.mcreator.boh.geo;

import java.util.HashMap;
import java.util.Map;

/** Holds the {@link AnimatableManager}s for an animatable, one per render instance id. */
public class AnimatableInstanceCache {

    protected final GeoAnimatable animatable;
    private final Map<Long, AnimatableManager<?>> managers = new HashMap<>();

    public AnimatableInstanceCache(GeoAnimatable animatable) {
        this.animatable = animatable;
    }

    @SuppressWarnings("unchecked")
    public <T extends GeoAnimatable> AnimatableManager<T> getManagerForId(long uniqueId) {
        AnimatableManager<?> m = managers.get(uniqueId);
        if (m == null) {
            m = new AnimatableManager<>(animatable);
            managers.put(uniqueId, m);
        }
        return (AnimatableManager<T>) m;
    }
}
