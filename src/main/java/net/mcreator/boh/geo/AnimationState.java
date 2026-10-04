package net.mcreator.boh.geo;

import java.util.HashMap;
import java.util.Map;

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
        return this.animationTick;
    }

    public T getAnimatable() {
        return this.animatable;
    }

    public float getLimbSwing() {
        return this.limbSwing;
    }

    public float getLimbSwingAmount() {
        return this.limbSwingAmount;
    }

    public float getPartialTick() {
        return this.partialTick;
    }

    public boolean isMoving() {
        return this.isMoving;
    }

    public AnimationController<T> getController() {
        return this.controller;
    }

    public AnimationState<T> withController(AnimationController<T> controller) {
        this.controller = controller;
        return this;
    }

    public <D> D getData(DataTicket<D> ticket) {
        return (D)this.extraData.get(ticket);
    }

    public <D> void setData(DataTicket<D> ticket, D data) {
        this.extraData.put(ticket, data);
    }

    public void setAnimation(RawAnimation animation) {
        this.controller.setAnimation(animation);
    }

    public PlayState setAndContinue(RawAnimation animation) {
        this.controller.setAnimation(animation);
        return PlayState.CONTINUE;
    }

    public boolean isCurrentAnimation(RawAnimation animation) {
        return animation != null && animation.equals(this.controller.getCurrentRawAnimation());
    }

    public boolean isCurrentAnimationStage(String name) {
        return this.controller.getCurrentAnimation() != null && this.controller.getCurrentAnimation().animation.name().equals(name);
    }

    public void resetCurrentAnimation() {
        this.controller.forceAnimationReset();
    }

    public void setControllerSpeed(float speed) {
        this.controller.setAnimationSpeed(speed);
    }
}
