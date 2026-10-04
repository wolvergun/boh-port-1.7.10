package net.mcreator.boh.client.renderer;

import net.mcreator.boh.entity.MichaelDaviesEntity;
import net.mcreator.boh.entity.layer.MichaelDaviesLayer;
import net.mcreator.boh.entity.model.MichaelDaviesModel;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.compat.mc.client.renderer.entity.Context;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.BakedGeoModel;
import net.mcreator.boh.geo.GeoEntityRenderer;

public class MichaelDaviesRenderer extends GeoEntityRenderer<MichaelDaviesEntity> {

    public MichaelDaviesRenderer(Context renderManager) {
        super(renderManager, new MichaelDaviesModel());
        this.shadowRadius = 0.5F;
        this.addRenderLayer(new MichaelDaviesLayer(this));
    }

    public RenderType getRenderType(MichaelDaviesEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }

    public void preRender(PoseStack poseStack, MichaelDaviesEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        float scale = 0.9F;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    protected float getDeathMaxRotation(MichaelDaviesEntity entityLivingBaseIn) {
        return 0.0F;
    }
}
