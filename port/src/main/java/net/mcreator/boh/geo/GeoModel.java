package net.mcreator.boh.geo;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/** Resolves model/texture/animation resources for an animatable and drives its animation processing. */
public abstract class GeoModel<T extends GeoAnimatable> {

    private final AnimationProcessor<T> processor = new AnimationProcessor<>(this);
    private BakedGeoModel currentModel;
    private double animTime;
    private double lastGameTickTime;
    private long lastRenderedInstance = -1;

    public abstract ResourceLocation getModelResource(T animatable);

    public abstract ResourceLocation getTextureResource(T animatable);

    public abstract ResourceLocation getAnimationResource(T animatable);

    public boolean crashIfBoneMissing() {
        return false;
    }

    public final BakedGeoModel getBakedModel(ResourceLocation location) {
        BakedGeoModel model = GeoLoader.getModel(location);
        if (model != null && model != currentModel) {
            processor.setActiveModel(model);
            currentModel = model;
        }
        return currentModel;
    }

    public CoreGeoBone getBone(String name) {
        return processor.getBone(name);
    }

    public Animation getAnimation(T animatable, String name) {
        ResourceLocation loc = getAnimationResource(animatable);
        if (loc == null) return null;
        return GeoLoader.getAnimations(loc)
            .get(name);
    }

    public AnimationProcessor<T> getAnimationProcessor() {
        return processor;
    }

    public void addAdditionalStateData(T animatable, long instanceId, AnimationState<T> state) {}

    public void handleAnimations(T animatable, long instanceId, AnimationState<T> animationState) {
        Minecraft mc = Minecraft.getMinecraft();
        AnimatableManager<T> manager = animatable.getAnimatableInstanceCache()
            .getManagerForId(instanceId);
        Double currentTick = animationState.getData(DataTickets.TICK);
        if (currentTick == null) currentTick = animatable instanceof Entity ? (double) ((Entity) animatable).ticksExisted
            : RenderUtils.getCurrentTick();

        if (manager.getFirstTickTime() == -1) manager.startedAt(currentTick + RenderUtils.partialTick);

        double currentFrameTime = animatable instanceof Entity ? currentTick + RenderUtils.partialTick
            : currentTick - manager.getFirstTickTime();
        boolean isReRender = !manager.isFirstTick() && currentFrameTime == manager.getLastUpdateTime();
        if (isReRender && instanceId == lastRenderedInstance) return;

        if (!mc.isGamePaused() || animatable.shouldPlayAnimsWhileGamePaused()) {
            manager.updatedAt(currentFrameTime);
            double lastUpdateTime = manager.getLastUpdateTime();
            animTime += lastUpdateTime - lastGameTickTime;
            lastGameTickTime = lastUpdateTime;
        }

        animationState.animationTick = animTime;
        lastRenderedInstance = instanceId;
        if (!processor.getRegisteredBones()
            .isEmpty()) processor.tickAnimation(animatable, this, manager, animTime, animationState, crashIfBoneMissing());
        setCustomAnimations(animatable, instanceId, animationState);
    }

    public void setCustomAnimations(T animatable, long instanceId, AnimationState<T> animationState) {}
}
