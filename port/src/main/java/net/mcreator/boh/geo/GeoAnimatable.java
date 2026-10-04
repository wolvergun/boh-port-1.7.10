package net.mcreator.boh.geo;

public interface GeoAnimatable {

    void registerControllers(AnimatableManager.ControllerRegistrar controllers);

    AnimatableInstanceCache getAnimatableInstanceCache();

    default double getBoneResetTime() {
        return 1;
    }

    default boolean shouldPlayAnimsWhileGamePaused() {
        return false;
    }

    double getTick(Object object);
}
