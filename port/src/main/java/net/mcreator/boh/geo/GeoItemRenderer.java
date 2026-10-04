package net.mcreator.boh.geo;

import java.util.ArrayList;
import java.util.List;

import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.mcreator.boh.compat.mc.client.renderer.BlockEntityWithoutLevelRenderer;
import net.mcreator.boh.compat.mc.world.item.ItemDisplayContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/** GeckoLib GeoItemRenderer port. */
public class GeoItemRenderer<T extends Item & GeoAnimatable> extends BlockEntityWithoutLevelRenderer implements GeoRenderer<T> {

    protected final List<GeoRenderLayer<T>> renderLayers = new ArrayList<>();
    protected final GeoModel<T> model;
    protected ItemStack currentItemStack;
    protected ItemDisplayContext renderPerspective;
    protected T animatable;
    protected float scaleWidth = 1;
    protected float scaleHeight = 1;

    public GeoItemRenderer(GeoModel<T> model) {
        this.model = model;
    }

    public GeoItemRenderer(Object dispatcher, Object modelSet, GeoModel<T> model) {
        this(model);
    }

    @Override
    public GeoModel<T> getGeoModel() {
        return model;
    }

    @Override
    public T getAnimatable() {
        return animatable;
    }

    public ItemStack getCurrentItemStack() {
        return currentItemStack;
    }

    @Override
    public long getInstanceId(T animatable) {
        return System.identityHashCode(animatable);
    }

    @Override
    public ResourceLocation getTextureLocation(T animatable) {
        return model.getTextureResource(animatable);
    }

    @Override
    public List<GeoRenderLayer<T>> getRenderLayers() {
        return renderLayers;
    }

    public GeoItemRenderer<T> addRenderLayer(GeoRenderLayer<T> layer) {
        renderLayers.add(layer);
        return this;
    }

    public GeoItemRenderer<T> withScale(float scale) {
        return withScale(scale, scale);
    }

    public GeoItemRenderer<T> withScale(float w, float h) {
        scaleWidth = w;
        scaleHeight = h;
        return this;
    }

    @Override
    public void preRender(PoseStack poseStack, T animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer,
        boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (!isReRender && (scaleWidth != 1 || scaleHeight != 1)) poseStack.scale(scaleWidth, scaleHeight, scaleWidth);
        if (!isReRender) poseStack.translate(0.5f, 0.51f, 0.5f);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void renderByItem(ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource bufferSource,
        int packedLight, int packedOverlay) {
        animatable = (T) stack.getItem();
        currentItemStack = stack;
        renderPerspective = transformType;
        float pt = RenderUtils.partialTick;
        RenderType renderType = getRenderType(animatable, getTextureLocation(animatable), bufferSource, pt);
        VertexConsumer buffer = bufferSource.getBuffer(renderType);
        defaultRender(poseStack, animatable, bufferSource, renderType, buffer, 0, pt, packedLight, packedOverlay, 1, 1, 1, 1);
        animatable = null;
        currentItemStack = null;
        renderPerspective = null;
    }

    @Override
    public void actuallyRender(PoseStack poseStack, T animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource,
        VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
        float alpha) {
        if (!isReRender) {
            AnimationState<T> state = new AnimationState<>(animatable, 0, 0, partialTick, false);
            long id = getInstanceId(animatable);
            state.setData(DataTickets.TICK, animatable.getTick(currentItemStack));
            state.setData(DataTickets.ITEM_RENDER_PERSPECTIVE, renderPerspective);
            this.model.addAdditionalStateData(animatable, id, state);
            this.model.handleAnimations(animatable, id, state);
        }
        GeoRenderer.super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight,
            packedOverlay, red, green, blue, alpha);
    }
}
