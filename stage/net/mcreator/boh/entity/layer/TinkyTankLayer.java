package net.mcreator.boh.entity.layer;

import net.mcreator.boh.entity.TinkyTankEntity;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.compat.client.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.BakedGeoModel;
import net.mcreator.boh.geo.GeoRenderer;
import net.mcreator.boh.geo.GeoRenderLayer;

public class TinkyTankLayer extends GeoRenderLayer<TinkyTankEntity> {

    private static final ResourceLocation LAYER = new ResourceLocation("boh", "textures/entities/tinkytankglow.png");

    public TinkyTankLayer(GeoRenderer<TinkyTankEntity> entityRenderer) {
        super(entityRenderer);
    }

    public void render(PoseStack poseStack, TinkyTankEntity animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        RenderType glowRenderType = RenderType.eyes(LAYER);
        this.getRenderer().reRender(this.getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, glowRenderType, bufferSource.getBuffer(glowRenderType), partialTick, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
