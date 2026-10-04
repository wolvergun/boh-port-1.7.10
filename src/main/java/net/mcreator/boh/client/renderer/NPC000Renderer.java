package net.mcreator.boh.client.renderer;

import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.mcreator.boh.compat.mc.client.renderer.entity.Context;
import net.mcreator.boh.entity.NPC000Entity;
import net.mcreator.boh.entity.model.NPC000Model;
import net.mcreator.boh.geo.BakedGeoModel;
import net.mcreator.boh.geo.GeoEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class NPC000Renderer extends GeoEntityRenderer<NPC000Entity> {
    public NPC000Renderer(Context renderManager) {
        super(renderManager, new NPC000Model());
        this.shadowRadius = 0.5F;
    }

    public RenderType getRenderType(NPC000Entity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }

    public void preRender(
        PoseStack poseStack,
        NPC000Entity entity,
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

    protected float getDeathMaxRotation(NPC000Entity entityLivingBaseIn) {
        return 0.0F;
    }
}
