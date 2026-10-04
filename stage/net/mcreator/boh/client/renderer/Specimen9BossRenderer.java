package net.mcreator.boh.client.renderer;

import net.mcreator.boh.entity.Specimen9BossEntity;
import net.mcreator.boh.entity.layer.Specimen9BossLayer;
import net.mcreator.boh.entity.model.Specimen9BossModel;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.compat.mc.client.renderer.entity.Context;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.BakedGeoModel;
import net.mcreator.boh.geo.GeoEntityRenderer;

public class Specimen9BossRenderer extends GeoEntityRenderer<Specimen9BossEntity> {

    public Specimen9BossRenderer(Context renderManager) {
        super(renderManager, new Specimen9BossModel());
        this.shadowRadius = 0.5F;
        this.addRenderLayer(new Specimen9BossLayer(this));
    }

    public RenderType getRenderType(Specimen9BossEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }

    public void preRender(PoseStack poseStack, Specimen9BossEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        float scale = 1.0F;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
