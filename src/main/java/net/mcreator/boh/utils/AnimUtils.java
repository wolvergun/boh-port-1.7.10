package net.mcreator.boh.utils;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.mcreator.boh.compat.mc.client.model.geom.ModelPart;
import net.mcreator.boh.geo.GeoBone;

public class AnimUtils {
    public static void renderPartOverBone(
        ModelPart model, GeoBone bone, PoseStack stack, VertexConsumer buffer, int packedLightIn, int packedOverlayIn, float alpha
    ) {
        renderPartOverBone(model, bone, stack, buffer, packedLightIn, packedOverlayIn, 1.0F, 1.0F, 1.0F, alpha);
    }

    public static void renderPartOverBone(
        ModelPart model, GeoBone bone, PoseStack stack, VertexConsumer buffer, int packedLightIn, int packedOverlayIn, float r, float g, float b, float a
    ) {
        setupModelFromBone(model, bone);
        M.render(model, stack, buffer, packedLightIn, packedOverlayIn, r, g, b, a);
    }

    public static void setupModelFromBone(ModelPart model, GeoBone bone) {
        M.setPos(model, bone.getPivotX(), bone.getPivotY(), bone.getPivotZ());
        M.set_xRot(model, 0.0F);
        M.set_yRot(model, 0.0F);
        M.set_zRot(model, 0.0F);
    }
}
