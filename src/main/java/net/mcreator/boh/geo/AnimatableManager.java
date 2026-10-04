package net.mcreator.boh.geo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AnimatableManager<T extends GeoAnimatable> {
    private final Map<String, BoneSnapshot> boneSnapshots = new HashMap<>();
    private final Map<String, AnimationController<T>> animationControllers;
    private final Map<DataTicket<?>, Object> extraData = new HashMap<>();
    private double lastUpdateTime;
    private boolean isFirstTick = true;
    private double firstTickTime = -1.0;

    public AnimatableManager(GeoAnimatable animatable) {
        AnimatableManager.ControllerRegistrar registrar = new AnimatableManager.ControllerRegistrar();
        animatable.registerControllers(registrar);
        this.animationControllers = new LinkedHashMap<>();

        for (AnimationController<?> c : registrar.controllers) {
            this.animationControllers.put(c.getName(), (AnimationController<T>)c);
        }
    }

    public void addController(AnimationController<T> controller) {
        this.animationControllers.put(controller.getName(), controller);
    }

    public void removeController(String name) {
        this.animationControllers.remove(name);
    }

    public Map<String, AnimationController<T>> getAnimationControllers() {
        return this.animationControllers;
    }

    public Map<String, BoneSnapshot> getBoneSnapshotCollection() {
        return this.boneSnapshots;
    }

    public void clearSnapshotCache() {
        this.boneSnapshots.clear();
    }

    public double getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void updatedAt(double time) {
        this.lastUpdateTime = time;
    }

    public double getFirstTickTime() {
        return this.firstTickTime;
    }

    protected void startedAt(double time) {
        this.firstTickTime = time;
    }

    protected boolean isFirstTick() {
        return this.isFirstTick;
    }

    protected void finishFirstTick() {
        this.isFirstTick = false;
    }

    public <D> void setData(DataTicket<D> ticket, D data) {
        this.extraData.put(ticket, data);
    }

    public <D> D getData(DataTicket<D> ticket) {
        return (D)this.extraData.get(ticket);
    }

    public void tryTriggerAnimation(String animName) {
        for (AnimationController<?> c : this.animationControllers.values()) {
            if (c.tryTriggerAnimation(animName)) {
                return;
            }
        }
    }

    public void tryTriggerAnimation(String controllerName, String animName) {
        AnimationController<?> c = this.animationControllers.get(controllerName);
        if (c != null) {
            c.tryTriggerAnimation(animName);
        }
    }

    public static final class ControllerRegistrar {
        final List<AnimationController<?>> controllers = new ArrayList<>();

        public AnimatableManager.ControllerRegistrar add(AnimationController<?>... controllers) {
            for (AnimationController<?> c : controllers) {
                this.controllers.add(c);
            }

            return this;
        }

        public AnimatableManager.ControllerRegistrar add(AnimationController<?> controller) {
            this.controllers.add(controller);
            return this;
        }

        public AnimatableManager.ControllerRegistrar remove(String name) {
            this.controllers.removeIf(c -> c.getName().equals(name));
            return this;
        }
    }
}
