package net.mcreator.boh.client.renderer;

import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.mcreator.boh.compat.mc.client.renderer.entity.Context;
import net.mcreator.boh.entity.Unown3Entity;
import net.mcreator.boh.entity.layer.Unown3Layer;
import net.mcreator.boh.entity.model.Unown3Model;
import net.mcreator.boh.geo.BakedGeoModel;
import net.mcreator.boh.geo.GeoEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class Unown3Renderer extends GeoEntityRenderer<Unown3Entity> {
    public Unown3Renderer(Context renderManager) {
        super(renderManager, new Unown3Model());
        this.shadowRadius = 0.3F;
        this.addRenderLayer(new Unown3Layer(this));
    }

    public RenderType getRenderType(Unown3Entity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }

    public void preRender(
        PoseStack poseStack,
        Unown3Entity entity,
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
