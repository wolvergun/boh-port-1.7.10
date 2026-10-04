package net.mcreator.boh.geo;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public abstract class GeoModel<T extends GeoAnimatable> {
    private final AnimationProcessor<T> processor = new AnimationProcessor<>(this);
    private BakedGeoModel currentModel;
    private double animTime;
    private double lastGameTickTime;
    private long lastRenderedInstance = -1L;

    public abstract ResourceLocation getModelResource(T var1);

    public abstract ResourceLocation getTextureResource(T var1);

    public abstract ResourceLocation getAnimationResource(T var1);

    public boolean crashIfBoneMissing() {
        return false;
    }

    public final BakedGeoModel getBakedModel(ResourceLocation location) {
        BakedGeoModel model = GeoLoader.getModel(location);
        if (model != null && model != this.currentModel) {
            this.processor.setActiveModel(model);
            this.currentModel = model;
        }

        return this.currentModel;
    }

    public CoreGeoBone getBone(String name) {
        return this.processor.getBone(name);
    }

    public Animation getAnimation(T animatable, String name) {
        ResourceLocation loc = this.getAnimationResource(animatable);
        return loc == null ? null : GeoLoader.getAnimations(loc).get(name);
    }

    public AnimationProcessor<T> getAnimationProcessor() {
        return this.processor;
    }

    public void addAdditionalStateData(T animatable, long instanceId, AnimationState<T> state) {
    }

    public void handleAnimations(T animatable, long instanceId, AnimationState<T> animationState) {
        Minecraft mc = Minecraft.getMinecraft();
        AnimatableManager<T> manager = animatable.getAnimatableInstanceCache().getManagerForId(instanceId);
        Double currentTick = animationState.getData(DataTickets.TICK);
        if (currentTick == null) {
            currentTick = animatable instanceof Entity ? ((Entity)animatable).ticksExisted : RenderUtils.getCurrentTick();
        }

        if (manager.getFirstTickTime() == -1.0) {
            manager.startedAt(currentTick + RenderUtils.partialTick);
        }

        double currentFrameTime = animatable instanceof Entity ? currentTick + RenderUtils.partialTick : currentTick - manager.getFirstTickTime();
        boolean isReRender = !manager.isFirstTick() && currentFrameTime == manager.getLastUpdateTime();
        if (!isReRender || instanceId != this.lastRenderedInstance) {
            if (!mc.isGamePaused() || animatable.shouldPlayAnimsWhileGamePaused()) {
                manager.updatedAt(currentFrameTime);
                double lastUpdateTime = manager.getLastUpdateTime();
                this.animTime = this.animTime + (lastUpdateTime - this.lastGameTickTime);
                this.lastGameTickTime = lastUpdateTime;
            }

            animationState.animationTick = this.animTime;
            this.lastRenderedInstance = instanceId;
            if (!this.processor.getRegisteredBones().isEmpty()) {
                this.processor.tickAnimation(animatable, this, manager, this.animTime, animationState, this.crashIfBoneMissing());
            }

            this.setCustomAnimations(animatable, instanceId, animationState);
        }
    }

    public void setCustomAnimations(T animatable, long instanceId, AnimationState<T> animationState) {
    }
}
