package net.mcreator.boh.client.renderer;

import net.mcreator.boh.entity.SpringtrapEntity;
import net.mcreator.boh.entity.layer.SpringtrapLayer;
import net.mcreator.boh.entity.model.SpringtrapModel;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.compat.mc.client.renderer.entity.Context;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.BakedGeoModel;
import net.mcreator.boh.geo.GeoEntityRenderer;

public class SpringtrapRenderer extends GeoEntityRenderer<SpringtrapEntity> {

    public SpringtrapRenderer(Context renderManager) {
        super(renderManager, new SpringtrapModel());
        this.shadowRadius = 0.5F;
        this.addRenderLayer(new SpringtrapLayer(this));
    }

    public RenderType getRenderType(SpringtrapEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }

    public void preRender(PoseStack poseStack, SpringtrapEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        float scale = 0.8F;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    protected float getDeathMaxRotation(SpringtrapEntity entityLivingBaseIn) {
        return 0.0F;
    }
}
