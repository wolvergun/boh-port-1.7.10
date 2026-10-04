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

public class GeoItemRenderer<T extends Item & GeoAnimatable> extends BlockEntityWithoutLevelRenderer implements GeoRenderer<T> {
    protected final List<GeoRenderLayer<T>> renderLayers = new ArrayList<>();
    protected final GeoModel<T> model;
    protected ItemStack currentItemStack;
    protected ItemDisplayContext renderPerspective;
    protected T animatable;
    protected float scaleWidth = 1.0F;
    protected float scaleHeight = 1.0F;

    public GeoItemRenderer(GeoModel<T> model) {
        this.model = model;
    }

    public GeoItemRenderer(Object dispatcher, Object modelSet, GeoModel<T> model) {
        this(model);
    }

    @Override
    public GeoModel<T> getGeoModel() {
        return this.model;
    }

    public T getAnimatable() {
        return this.animatable;
    }

    public ItemStack getCurrentItemStack() {
        return this.currentItemStack;
    }

    public long getInstanceId(T animatable) {
        return System.identityHashCode(animatable);
    }

    public ResourceLocation getTextureLocation(T animatable) {
        return this.model.getTextureResource(animatable);
    }

    @Override
    public List<GeoRenderLayer<T>> getRenderLayers() {
        return this.renderLayers;
    }

    public GeoItemRenderer<T> addRenderLayer(GeoRenderLayer<T> layer) {
        this.renderLayers.add(layer);
        return this;
    }

    public GeoItemRenderer<T> withScale(float scale) {
        return this.withScale(scale, scale);
    }

    public GeoItemRenderer<T> withScale(float w, float h) {
        this.scaleWidth = w;
        this.scaleHeight = h;
        return this;
    }

    public void preRender(
        PoseStack poseStack,
        T animatable,
        BakedGeoModel model,
        MultiBufferSource bufferSource,
        VertexConsumer buffer,
        boolean isReRender,
        float partialTick,
        int packedLight,
        int packedOverlay,
        float red,
        float green,
        float blue,
        float alpha
    ) {
        if (!isReRender && (this.scaleWidth != 1.0F || this.scaleHeight != 1.0F)) {
            poseStack.scale(this.scaleWidth, this.scaleHeight, this.scaleWidth);
        }

        if (!isReRender) {
            poseStack.translate(0.5F, 0.51F, 0.5F);
        }
    }

    @Override
    public void renderByItem(
        ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay
    ) {
        this.animatable = (T)stack.getItem();
        this.currentItemStack = stack;
        this.renderPerspective = transformType;
        float pt = RenderUtils.partialTick;
        RenderType renderType = this.getRenderType(this.animatable, this.getTextureLocation(this.animatable), bufferSource, pt);
        VertexConsumer buffer = bufferSource.getBuffer(renderType);
        this.defaultRender(poseStack, this.animatable, bufferSource, renderType, buffer, 0.0F, pt, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        this.animatable = null;
        this.currentItemStack = null;
        this.renderPerspective = null;
    }

    public void actuallyRender(
        PoseStack poseStack,
        T animatable,
        BakedGeoModel model,
        RenderType renderType,
        MultiBufferSource bufferSource,
        VertexConsumer buffer,
        boolean isReRender,
        float partialTick,
        int packedLight,
        int packedOverlay,
        float red,
        float green,
        float blue,
        float alpha
    ) {
        if (!isReRender) {
            AnimationState<T> state = new AnimationState<>(animatable, 0.0F, 0.0F, partialTick, false);
            long id = this.getInstanceId(animatable);
            state.setData(DataTickets.TICK, animatable.getTick(this.currentItemStack));
            state.setData(DataTickets.ITEM_RENDER_PERSPECTIVE, this.renderPerspective);
            this.model.addAdditionalStateData(animatable, id, state);
            this.model.handleAnimations(animatable, id, state);
        }

        GeoRenderer.super.actuallyRender(
            poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha
        );
    }
}
