package net.mcreator.boh.client.renderer;

import net.mcreator.boh.entity.LightHeadEntity;
import net.mcreator.boh.entity.layer.LightHeadLayer;
import net.mcreator.boh.entity.model.LightHeadModel;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.compat.mc.client.renderer.entity.Context;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.BakedGeoModel;
import net.mcreator.boh.geo.GeoEntityRenderer;

public class LightHeadRenderer extends GeoEntityRenderer<LightHeadEntity> {

    public LightHeadRenderer(Context renderManager) {
        super(renderManager, new LightHeadModel());
        this.shadowRadius = 0.8F;
        this.addRenderLayer(new LightHeadLayer(this));
    }

    public RenderType getRenderType(LightHeadEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }

    public void preRender(PoseStack poseStack, LightHeadEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        float scale = 1.0F;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
