package net.mcreator.boh.client.renderer;

import net.mcreator.boh.client.model.Modelblood_spill;
import net.mcreator.boh.entity.ShotgunProjectileEntity;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.mcreator.boh.compat.client.Axis;
import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.compat.mc.client.renderer.entity.EntityRenderer;
import net.mcreator.boh.compat.mc.client.renderer.entity.Context;
import net.mcreator.boh.compat.client.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.M;

public class ShotgunProjectileRenderer extends EntityRenderer<ShotgunProjectileEntity> {

    private static final ResourceLocation texture = new ResourceLocation("boh:textures/entities/jeff_glow.png");

    private final Modelblood_spill model;

    public ShotgunProjectileRenderer(Context context) {
        super(context);
        this.model = new Modelblood_spill(M.bakeLayer(context, Modelblood_spill.LAYER_LOCATION));
    }

    public void render(ShotgunProjectileEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        VertexConsumer vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, M.yRotO(entityIn), M.getYRot(entityIn)) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F + Mth.lerp(partialTicks, M.xRotO(entityIn), M.getXRot(entityIn))));
        this.model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
        super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    public ResourceLocation getTextureLocation(ShotgunProjectileEntity entity) {
        return texture;
    }
}
