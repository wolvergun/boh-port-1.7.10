package net.mcreator.boh.geo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/** Per-instance animation state: controllers and the bone snapshots they animate from. */
public class AnimatableManager<T extends GeoAnimatable> {

    private final Map<String, BoneSnapshot> boneSnapshots = new HashMap<>();
    private final Map<String, AnimationController<T>> animationControllers;
    private final Map<DataTicket<?>, Object> extraData = new HashMap<>();

    private double lastUpdateTime;
    private boolean isFirstTick = true;
    private double firstTickTime = -1;

    @SuppressWarnings("unchecked")
    public AnimatableManager(GeoAnimatable animatable) {
        ControllerRegistrar registrar = new ControllerRegistrar();
        animatable.registerControllers(registrar);
        animationControllers = new LinkedHashMap<>();
        for (AnimationController<?> c : registrar.controllers) animationControllers.put(c.getName(), (AnimationController<T>) c);
    }

    public void addController(AnimationController<T> controller) {
        animationControllers.put(controller.getName(), controller);
    }

    public void removeController(String name) {
        animationControllers.remove(name);
    }

    public Map<String, AnimationController<T>> getAnimationControllers() {
        return animationControllers;
    }

    public Map<String, BoneSnapshot> getBoneSnapshotCollection() {
        return boneSnapshots;
    }

    public void clearSnapshotCache() {
        boneSnapshots.clear();
    }

    public double getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void updatedAt(double time) {
        lastUpdateTime = time;
    }

    public double getFirstTickTime() {
        return firstTickTime;
    }

    protected void startedAt(double time) {
        firstTickTime = time;
    }

    protected boolean isFirstTick() {
        return isFirstTick;
    }

    protected void finishFirstTick() {
        isFirstTick = false;
    }

    public <D> void setData(DataTicket<D> ticket, D data) {
        extraData.put(ticket, data);
    }

    @SuppressWarnings("unchecked")
    public <D> D getData(DataTicket<D> ticket) {
        return (D) extraData.get(ticket);
    }

    public void tryTriggerAnimation(String animName) {
        for (AnimationController<?> c : animationControllers.values()) if (c.tryTriggerAnimation(animName)) return;
    }

    public void tryTriggerAnimation(String controllerName, String animName) {
        AnimationController<?> c = animationControllers.get(controllerName);
        if (c != null) c.tryTriggerAnimation(animName);
    }

    public static final class ControllerRegistrar {

        final java.util.List<AnimationController<?>> controllers = new java.util.ArrayList<>();

        public ControllerRegistrar add(AnimationController<?>... controllers) {
            for (AnimationController<?> c : controllers) this.controllers.add(c);
            return this;
        }

        public ControllerRegistrar add(AnimationController<?> controller) {
            controllers.add(controller);
            return this;
        }

        public ControllerRegistrar remove(String name) {
            controllers.removeIf(c -> c.getName().equals(name));
            return this;
        }
    }
}
