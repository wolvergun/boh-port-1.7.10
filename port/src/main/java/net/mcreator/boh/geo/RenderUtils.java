package net.mcreator.boh.geo;

import net.mcreator.boh.compat.client.Axis;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.Quaternionf;
import net.minecraft.client.Minecraft;

public final class RenderUtils {

    /** Partial tick of the frame currently being rendered (set by the renderers). */
    public static float partialTick;

    private RenderUtils() {}

    public static double getCurrentTick() {
        Minecraft mc = Minecraft.getMinecraft();
        long time = mc.theWorld != null ? mc.theWorld.getTotalWorldTime() : System.currentTimeMillis() / 50;
        return time + partialTick;
    }

    public static void translateMatrixToBone(PoseStack poseStack, CoreGeoBone bone) {
        poseStack.translate(-bone.getPosX() / 16f, bone.getPosY() / 16f, bone.getPosZ() / 16f);
    }

    public static void rotateMatrixAroundBone(PoseStack poseStack, CoreGeoBone bone) {
        if (bone.getRotZ() != 0) poseStack.mulPose(Axis.ZP.rotation(bone.getRotZ()));
        if (bone.getRotY() != 0) poseStack.mulPose(Axis.YP.rotation(bone.getRotY()));
        if (bone.getRotX() != 0) poseStack.mulPose(Axis.XP.rotation(bone.getRotX()));
    }

    public static void rotateMatrixAroundCube(PoseStack poseStack, GeoCube cube) {
        if (cube.rotZ != 0) poseStack.mulPose(new Quaternionf().rotationZ(cube.rotZ));
        if (cube.rotY != 0) poseStack.mulPose(new Quaternionf().rotationY(cube.rotY));
        if (cube.rotX != 0) poseStack.mulPose(new Quaternionf().rotationX(cube.rotX));
    }

    public static void scaleMatrixForBone(PoseStack poseStack, CoreGeoBone bone) {
        poseStack.scale(bone.getScaleX(), bone.getScaleY(), bone.getScaleZ());
    }

    public static void translateToPivotPoint(PoseStack poseStack, GeoCube cube) {
        poseStack.translate(cube.pivotX / 16f, cube.pivotY / 16f, cube.pivotZ / 16f);
    }

    public static void translateToPivotPoint(PoseStack poseStack, CoreGeoBone bone) {
        poseStack.translate(bone.getPivotX() / 16f, bone.getPivotY() / 16f, bone.getPivotZ() / 16f);
    }

    public static void translateAwayFromPivotPoint(PoseStack poseStack, GeoCube cube) {
        poseStack.translate(-cube.pivotX / 16f, -cube.pivotY / 16f, -cube.pivotZ / 16f);
    }

    public static void translateAwayFromPivotPoint(PoseStack poseStack, CoreGeoBone bone) {
        poseStack.translate(-bone.getPivotX() / 16f, -bone.getPivotY() / 16f, -bone.getPivotZ() / 16f);
    }

    public static void translateAndRotateMatrixForBone(PoseStack poseStack, CoreGeoBone bone) {
        translateToPivotPoint(poseStack, bone);
        rotateMatrixAroundBone(poseStack, bone);
    }

    public static void prepMatrixForBone(PoseStack poseStack, CoreGeoBone bone) {
        translateMatrixToBone(poseStack, bone);
        translateToPivotPoint(poseStack, bone);
        rotateMatrixAroundBone(poseStack, bone);
        scaleMatrixForBone(poseStack, bone);
        translateAwayFromPivotPoint(poseStack, bone);
    }
}
