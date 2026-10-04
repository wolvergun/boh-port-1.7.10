package net.mcreator.boh.client.renderer;

import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.mcreator.boh.compat.mc.client.renderer.entity.Context;
import net.mcreator.boh.entity.GoldHostileEntity;
import net.mcreator.boh.entity.model.GoldHostileModel;
import net.mcreator.boh.geo.BakedGeoModel;
import net.mcreator.boh.geo.GeoEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GoldHostileRenderer extends GeoEntityRenderer<GoldHostileEntity> {
    public GoldHostileRenderer(Context renderManager) {
        super(renderManager, new GoldHostileModel());
        this.shadowRadius = 0.5F;
    }

    public RenderType getRenderType(GoldHostileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }

    public void preRender(
        PoseStack poseStack,
        GoldHostileEntity entity,
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
