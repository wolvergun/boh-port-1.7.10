package net.mcreator.boh.client.renderer;

import net.mcreator.boh.entity.JeffTheKillerEntity;
import net.mcreator.boh.entity.layer.JeffTheKillerLayer;
import net.mcreator.boh.entity.model.JeffTheKillerModel;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.compat.mc.client.renderer.entity.Context;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.BakedGeoModel;
import net.mcreator.boh.geo.GeoEntityRenderer;

public class JeffTheKillerRenderer extends GeoEntityRenderer<JeffTheKillerEntity> {

    public JeffTheKillerRenderer(Context renderManager) {
        super(renderManager, new JeffTheKillerModel());
        this.shadowRadius = 0.5F;
        this.addRenderLayer(new JeffTheKillerLayer(this));
    }

    public RenderType getRenderType(JeffTheKillerEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }

    public void preRender(PoseStack poseStack, JeffTheKillerEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        float scale = 1.0F;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
