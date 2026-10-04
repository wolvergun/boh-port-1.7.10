package net.mcreator.boh.client.renderer;

import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.mcreator.boh.compat.mc.client.renderer.entity.Context;
import net.mcreator.boh.entity.PhantomPuppetEntity;
import net.mcreator.boh.entity.layer.PhantomPuppetLayer;
import net.mcreator.boh.entity.model.PhantomPuppetModel;
import net.mcreator.boh.geo.BakedGeoModel;
import net.mcreator.boh.geo.GeoEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class PhantomPuppetRenderer extends GeoEntityRenderer<PhantomPuppetEntity> {
    public PhantomPuppetRenderer(Context renderManager) {
        super(renderManager, new PhantomPuppetModel());
        this.shadowRadius = 0.5F;
        this.addRenderLayer(new PhantomPuppetLayer(this));
    }

    public RenderType getRenderType(PhantomPuppetEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }

    public void preRender(
        PoseStack poseStack,
        PhantomPuppetEntity entity,
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
