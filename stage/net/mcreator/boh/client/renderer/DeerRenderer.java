package net.mcreator.boh.client.renderer;

import net.mcreator.boh.entity.DeerEntity;
import net.mcreator.boh.entity.model.DeerModel;
import net.mcreator.boh.procedures.DeerBoundingBoxScaleProcedure;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.compat.mc.client.renderer.entity.Context;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.mcreator.boh.geo.BakedGeoModel;
import net.mcreator.boh.geo.GeoEntityRenderer;
import net.mcreator.boh.compat.M;

public class DeerRenderer extends GeoEntityRenderer<DeerEntity> {

    public DeerRenderer(Context renderManager) {
        super(renderManager, new DeerModel());
        this.shadowRadius = 0.5F;
    }

    public RenderType getRenderType(DeerEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }

    public void preRender(PoseStack poseStack, DeerEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        World world = M.level(entity);
        double x = M.getX(entity);
        double y = M.getY(entity);
        double z = M.getZ(entity);
        float scale = (float) DeerBoundingBoxScaleProcedure.execute(entity);
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
