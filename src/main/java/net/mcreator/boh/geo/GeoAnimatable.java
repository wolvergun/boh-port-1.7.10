package net.mcreator.boh.geo;

public interface GeoAnimatable {
    void registerControllers(AnimatableManager.ControllerRegistrar var1);

    AnimatableInstanceCache getAnimatableInstanceCache();

    default double getBoneResetTime() {
        return 1.0;
    }

    default boolean shouldPlayAnimsWhileGamePaused() {
        return false;
    }

    double getTick(Object var1);
}
