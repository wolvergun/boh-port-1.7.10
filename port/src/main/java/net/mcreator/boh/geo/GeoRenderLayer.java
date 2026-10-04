package net.mcreator.boh.geo;

import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.minecraft.util.ResourceLocation;

public abstract class GeoRenderLayer<T extends GeoAnimatable> {

    protected final GeoRenderer<T> renderer;

    public GeoRenderLayer(GeoRenderer<T> renderer) {
        this.renderer = renderer;
    }

    public GeoModel<T> getGeoModel() {
        return renderer.getGeoModel();
    }

    public GeoRenderer<T> getRenderer() {
        return renderer;
    }

    protected BakedGeoModel getDefaultBakedModel(T animatable) {
        return getGeoModel().getBakedModel(getGeoModel().getModelResource(animatable));
    }

    protected ResourceLocation getTextureResource(T animatable) {
        return renderer.getTextureLocation(animatable);
    }

    public void preRender(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType renderType,
        MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {}

    public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType renderType,
        MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {}

    public void renderForBone(PoseStack poseStack, T animatable, GeoBone bone, RenderType renderType,
        MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {}
}
