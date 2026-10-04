package net.mcreator.boh.client.renderer;

import net.mcreator.boh.entity.MothmanEntity;
import net.mcreator.boh.entity.layer.MothmanLayer;
import net.mcreator.boh.entity.model.MothmanModel;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.compat.mc.client.renderer.entity.Context;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.BakedGeoModel;
import net.mcreator.boh.geo.GeoEntityRenderer;

public class MothmanRenderer extends GeoEntityRenderer<MothmanEntity> {

    public MothmanRenderer(Context renderManager) {
        super(renderManager, new MothmanModel());
        this.shadowRadius = 0.5F;
        this.addRenderLayer(new MothmanLayer(this));
    }

    public RenderType getRenderType(MothmanEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }

    public void preRender(PoseStack poseStack, MothmanEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        float scale = 1.3F;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
