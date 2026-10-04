package net.mcreator.boh.entity.layer;

import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.OverlayTexture;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.mcreator.boh.entity.WillowispEntity;
import net.mcreator.boh.geo.BakedGeoModel;
import net.mcreator.boh.geo.GeoRenderLayer;
import net.mcreator.boh.geo.GeoRenderer;
import net.minecraft.util.ResourceLocation;

public class WillowispLayer extends GeoRenderLayer<WillowispEntity> {
    private static final ResourceLocation LAYER = new ResourceLocation("boh", "textures/entities/will_o_wisp.png");

    public WillowispLayer(GeoRenderer<WillowispEntity> entityRenderer) {
        super(entityRenderer);
    }

    public void render(
        PoseStack poseStack,
        WillowispEntity animatable,
        BakedGeoModel bakedModel,
        RenderType renderType,
        MultiBufferSource bufferSource,
        VertexConsumer buffer,
        float partialTick,
        int packedLight,
        int packedOverlay
    ) {
        RenderType glowRenderType = RenderType.eyes(LAYER);
        this.getRenderer()
            .reRender(
                this.getDefaultBakedModel(animatable),
                poseStack,
                bufferSource,
                animatable,
                glowRenderType,
                bufferSource.getBuffer(glowRenderType),
                partialTick,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                1.0F,
                1.0F,
                1.0F,
                1.0F
            );
    }
}
