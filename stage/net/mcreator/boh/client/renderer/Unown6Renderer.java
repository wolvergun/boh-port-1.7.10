package net.mcreator.boh.client.renderer;

import net.mcreator.boh.entity.Unown6Entity;
import net.mcreator.boh.entity.layer.Unown6Layer;
import net.mcreator.boh.entity.model.Unown6Model;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.compat.mc.client.renderer.entity.Context;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.BakedGeoModel;
import net.mcreator.boh.geo.GeoEntityRenderer;

public class Unown6Renderer extends GeoEntityRenderer<Unown6Entity> {

    public Unown6Renderer(Context renderManager) {
        super(renderManager, new Unown6Model());
        this.shadowRadius = 0.3F;
        this.addRenderLayer(new Unown6Layer(this));
    }

    public RenderType getRenderType(Unown6Entity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }

    public void preRender(PoseStack poseStack, Unown6Entity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        float scale = 1.0F;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
