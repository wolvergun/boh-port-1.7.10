package net.mcreator.boh.geo;

import java.util.List;
import net.mcreator.boh.compat.client.Matrix3f;
import net.mcreator.boh.compat.client.Matrix4f;
import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.minecraft.util.ResourceLocation;

public interface GeoRenderer<T extends GeoAnimatable> {
    GeoModel<T> getGeoModel();

    T getAnimatable();

    default ResourceLocation getTextureLocation(T animatable) {
        return this.getGeoModel().getTextureResource(animatable);
    }

    List<GeoRenderLayer<T>> getRenderLayers();

    default RenderType getRenderType(T animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(texture);
    }

    default long getInstanceId(T animatable) {
        return animatable.hashCode();
    }

    default void defaultRender(
        PoseStack poseStack,
        T animatable,
        MultiBufferSource bufferSource,
        RenderType renderType,
        VertexConsumer buffer,
        float yaw,
        float partialTick,
        int packedLight,
        int packedOverlay,
        float red,
        float green,
        float blue,
        float alpha
    ) {
        poseStack.pushPose();
        BakedGeoModel model = this.getGeoModel().getBakedModel(this.getGeoModel().getModelResource(animatable));
        if (model == null) {
            poseStack.popPose();
        } else {
            if (renderType == null) {
                renderType = this.getRenderType(animatable, this.getTextureLocation(animatable), bufferSource, partialTick);
            }

            if (buffer == null && renderType != null) {
                buffer = bufferSource.getBuffer(renderType);
            }

            this.preRender(poseStack, animatable, model, bufferSource, buffer, false, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
            this.actuallyRender(
                poseStack, animatable, model, renderType, bufferSource, buffer, false, partialTick, packedLight, packedOverlay, red, green, blue, alpha
            );
            this.applyRenderLayers(poseStack, animatable, model, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
            this.postRender(poseStack, animatable, model, bufferSource, buffer, false, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
            poseStack.popPose();
            this.renderFinal(poseStack, animatable, model, bufferSource, buffer, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        }
    }

    default void reRender(
        BakedGeoModel model,
        PoseStack poseStack,
        MultiBufferSource bufferSource,
        T animatable,
        RenderType renderType,
        VertexConsumer buffer,
        float partialTick,
        int packedLight,
        int packedOverlay,
        float red,
        float green,
        float blue,
        float alpha
    ) {
        poseStack.pushPose();
        this.preRender(poseStack, animatable, model, bufferSource, buffer, true, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        this.actuallyRender(
            poseStack, animatable, model, renderType, bufferSource, buffer, true, partialTick, packedLight, packedOverlay, red, green, blue, alpha
        );
        this.postRender(poseStack, animatable, model, bufferSource, buffer, true, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.popPose();
    }

    default void actuallyRender(
        PoseStack poseStack,
        T animatable,
        BakedGeoModel model,
        RenderType renderType,
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
        if (buffer != null) {
            for (GeoBone bone : model.topLevelBones()) {
                this.renderRecursively(
                    poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha
                );
            }
        }
    }

    default void applyRenderLayersForBone(
        PoseStack poseStack,
        T animatable,
        GeoBone bone,
        RenderType renderType,
        MultiBufferSource bufferSource,
        VertexConsumer buffer,
        float partialTick,
        int packedLight,
        int packedOverlay
    ) {
        for (GeoRenderLayer<T> layer : this.getRenderLayers()) {
            layer.renderForBone(poseStack, animatable, bone, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
        }
    }

    default void applyRenderLayers(
        PoseStack poseStack,
        T animatable,
        BakedGeoModel model,
        RenderType renderType,
        MultiBufferSource bufferSource,
        VertexConsumer buffer,
        float partialTick,
        int packedLight,
        int packedOverlay
    ) {
        for (GeoRenderLayer<T> layer : this.getRenderLayers()) {
            layer.render(poseStack, animatable, model, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
        }
    }

    default void preRender(
        PoseStack poseStack,
        T animatable,
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
    }

    default void postRender(
        PoseStack poseStack,
        T animatable,
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
    }

    default void renderFinal(
        PoseStack poseStack,
        T animatable,
        BakedGeoModel model,
        MultiBufferSource bufferSource,
        VertexConsumer buffer,
        float partialTick,
        int packedLight,
        int packedOverlay,
        float red,
        float green,
        float blue,
        float alpha
    ) {
    }

    default void renderRecursively(
        PoseStack poseStack,
        T animatable,
        GeoBone bone,
        RenderType renderType,
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
        poseStack.pushPose();
        RenderUtils.prepMatrixForBone(poseStack, bone);
        this.renderCubesOfBone(poseStack, bone, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        if (!isReRender) {
            this.applyRenderLayersForBone(poseStack, animatable, bone, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
        }

        this.renderChildBones(
            poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha
        );
        poseStack.popPose();
    }

    default void renderCubesOfBone(
        PoseStack poseStack, GeoBone bone, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
    ) {
        if (!bone.isHidden()) {
            for (GeoCube cube : bone.getCubes()) {
                poseStack.pushPose();
                this.renderCube(poseStack, cube, buffer, packedLight, packedOverlay, red, green, blue, alpha);
                poseStack.popPose();
            }
        }
    }

    default void renderChildBones(
        PoseStack poseStack,
        T animatable,
        GeoBone bone,
        RenderType renderType,
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
        if (!bone.isHidingChildren()) {
            for (GeoBone child : bone.getChildBones()) {
                this.renderRecursively(
                    poseStack,
                    animatable,
                    child,
                    renderType,
                    bufferSource,
                    buffer,
                    isReRender,
                    partialTick,
                    packedLight,
                    packedOverlay,
                    red,
                    green,
                    blue,
                    alpha
                );
            }
        }
    }

    default void renderCube(
        PoseStack poseStack, GeoCube cube, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
    ) {
        RenderUtils.translateToPivotPoint(poseStack, cube);
        RenderUtils.rotateMatrixAroundCube(poseStack, cube);
        RenderUtils.translateAwayFromPivotPoint(poseStack, cube);
        Matrix3f n = poseStack.last().normal();
        Matrix4f m = poseStack.last().pose();

        for (GeoQuad quad : cube.quads) {
            if (quad != null) {
                float nx = n.m00 * quad.nx + n.m01 * quad.ny + n.m02 * quad.nz;
                float ny = n.m10 * quad.nx + n.m11 * quad.ny + n.m12 * quad.nz;
                float nz = n.m20 * quad.nx + n.m21 * quad.ny + n.m22 * quad.nz;
                float len = (float)Math.sqrt(nx * nx + ny * ny + nz * nz);
                if (len > 1.0E-5F) {
                    nx /= len;
                    ny /= len;
                    nz /= len;
                }

                if (nx < 0.0F && (cube.sizeY == 0.0F || cube.sizeZ == 0.0F)) {
                    nx = -nx;
                }

                if (ny < 0.0F && (cube.sizeX == 0.0F || cube.sizeZ == 0.0F)) {
                    ny = -ny;
                }

                if (nz < 0.0F && (cube.sizeX == 0.0F || cube.sizeY == 0.0F)) {
                    nz = -nz;
                }

                float[] d = quad.data;

                for (int i = 0; i < 4; i++) {
                    int o = i * 5;
                    float x = d[o];
                    float y = d[o + 1];
                    float z = d[o + 2];
                    buffer.vertex(
                        m.transformX(x, y, z),
                        m.transformY(x, y, z),
                        m.transformZ(x, y, z),
                        red,
                        green,
                        blue,
                        alpha,
                        d[o + 3],
                        d[o + 4],
                        packedOverlay,
                        packedLight,
                        nx,
                        ny,
                        nz
                    );
                }
            }
        }
    }
}
