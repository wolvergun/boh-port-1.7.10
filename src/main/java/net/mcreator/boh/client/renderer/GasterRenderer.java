package net.mcreator.boh.client.renderer;

import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.mcreator.boh.compat.mc.client.renderer.entity.Context;
import net.mcreator.boh.entity.GasterEntity;
import net.mcreator.boh.entity.layer.GasterLayer;
import net.mcreator.boh.entity.model.GasterModel;
import net.mcreator.boh.geo.BakedGeoModel;
import net.mcreator.boh.geo.GeoEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GasterRenderer extends GeoEntityRenderer<GasterEntity> {
    public GasterRenderer(Context renderManager) {
        super(renderManager, new GasterModel());
        this.shadowRadius = 0.0F;
        this.addRenderLayer(new GasterLayer(this));
    }

    public RenderType getRenderType(GasterEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }

    public void preRender(
        PoseStack poseStack,
        GasterEntity entity,
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
        float scale = 1.0F;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(poseStack, (Entity)entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
