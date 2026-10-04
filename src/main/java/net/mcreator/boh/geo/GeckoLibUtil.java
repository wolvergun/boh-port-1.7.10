package net.mcreator.boh.geo;

public final class GeckoLibUtil {
    private GeckoLibUtil() {
    }

    public static AnimatableInstanceCache createInstanceCache(GeoAnimatable animatable) {
        return new AnimatableInstanceCache(animatable);
    }
}
