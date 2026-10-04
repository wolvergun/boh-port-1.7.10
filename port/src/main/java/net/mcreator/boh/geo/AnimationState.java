package net.mcreator.boh.geo;

import java.util.HashMap;
import java.util.Map;

/** Snapshot of the render-time state handed to controller predicates. */
public class AnimationState<T extends GeoAnimatable> {

    private final T animatable;
    private final float limbSwing;
    private final float limbSwingAmount;
    private final float partialTick;
    private final boolean isMoving;
    private final Map<DataTicket<?>, Object> extraData = new HashMap<>();

    protected AnimationController<T> controller;
    public double animationTick;

    public AnimationState(T animatable, float limbSwing, float limbSwingAmount, float partialTick, boolean isMoving) {
        this.animatable = animatable;
        this.limbSwing = limbSwing;
        this.limbSwingAmount = limbSwingAmount;
        this.partialTick = partialTick;
        this.isMoving = isMoving;
    }

    public double getAnimationTick() {
        return animationTick;
    }

    public T getAnimatable() {
        return animatable;
    }

    public float getLimbSwing() {
        return limbSwing;
    }

    public float getLimbSwingAmount() {
        return limbSwingAmount;
    }

    public float getPartialTick() {
        return partialTick;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public AnimationController<T> getController() {
        return controller;
    }

    public AnimationState<T> withController(AnimationController<T> controller) {
        this.controller = controller;
        return this;
    }

    @SuppressWarnings("unchecked")
    public <D> D getData(DataTicket<D> ticket) {
        return (D) extraData.get(ticket);
    }

    public <D> void setData(DataTicket<D> ticket, D data) {
        extraData.put(ticket, data);
    }

    public void setAnimation(RawAnimation animation) {
        controller.setAnimation(animation);
    }

    public PlayState setAndContinue(RawAnimation animation) {
        controller.setAnimation(animation);
        return PlayState.CONTINUE;
    }

    public boolean isCurrentAnimation(RawAnimation animation) {
        return animation != null && animation.equals(controller.getCurrentRawAnimation());
    }

    public boolean isCurrentAnimationStage(String name) {
        return controller.getCurrentAnimation() != null && controller.getCurrentAnimation().animation.name().equals(name);
    }

    public void resetCurrentAnimation() {
        controller.forceAnimationReset();
    }

    public void setControllerSpeed(float speed) {
        controller.setAnimationSpeed(speed);
    }
}
