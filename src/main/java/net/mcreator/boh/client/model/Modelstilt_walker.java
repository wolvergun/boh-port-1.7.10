package net.mcreator.boh.client.model;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.mcreator.boh.compat.mc.client.model.EntityModel;
import net.mcreator.boh.compat.mc.client.model.geom.ModelLayerLocation;
import net.mcreator.boh.compat.mc.client.model.geom.ModelPart;
import net.mcreator.boh.compat.mc.client.model.geom.PartPose;
import net.mcreator.boh.compat.mc.client.model.geom.builders.CubeDeformation;
import net.mcreator.boh.compat.mc.client.model.geom.builders.CubeListBuilder;
import net.mcreator.boh.compat.mc.client.model.geom.builders.LayerDefinition;
import net.mcreator.boh.compat.mc.client.model.geom.builders.MeshDefinition;
import net.mcreator.boh.compat.mc.client.model.geom.builders.PartDefinition;
import net.mcreator.boh.compat.mc.util.Mth;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class Modelstilt_walker<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("boh", "modelstilt_walker"), "main");
    public final ModelPart body;
    public final ModelPart right_front_leg;
    public final ModelPart right_back_leg;
    public final ModelPart left_front_leg;
    public final ModelPart left_back_leg;
    public final ModelPart head;

    public Modelstilt_walker(ModelPart root) {
        this.body = M.getChild(root, "body");
        this.right_front_leg = M.getChild(root, "right_front_leg");
        this.right_back_leg = M.getChild(root, "right_back_leg");
        this.left_front_leg = M.getChild(root, "left_front_leg");
        this.left_back_leg = M.getChild(root, "left_back_leg");
        this.head = M.getChild(root, "head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = M.getRoot(meshdefinition);
        PartDefinition body = M.addOrReplaceChild(
            partdefinition,
            "body",
            M.addBox(M.texOffs(CubeListBuilder.create(), 0, 0), -5.0F, -5.0F, -9.0F, 10.0F, 10.0F, 18.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, -32.0F, 0.0F)
        );
        PartDefinition right_front_leg = M.addOrReplaceChild(
            partdefinition,
            "right_front_leg",
            M.addBox(M.texOffs(CubeListBuilder.create(), 36, 28), -1.5F, 0.0F, -1.5F, 3.0F, 51.0F, 3.0F, new CubeDeformation(0.0F)),
            PartPose.offset(3.5F, -27.0F, -7.5F)
        );
        PartDefinition right_back_leg = M.addOrReplaceChild(
            partdefinition,
            "right_back_leg",
            M.addBox(M.texOffs(CubeListBuilder.create(), 12, 28), -1.5F, 0.0F, -1.5F, 3.0F, 51.0F, 3.0F, new CubeDeformation(0.0F)),
            PartPose.offset(3.5F, -27.0F, 7.5F)
        );
        PartDefinition left_front_leg = M.addOrReplaceChild(
            partdefinition,
            "left_front_leg",
            M.addBox(M.texOffs(CubeListBuilder.create(), 24, 28), -1.5F, 0.0F, -1.5F, 3.0F, 51.0F, 3.0F, new CubeDeformation(0.0F)),
            PartPose.offset(-3.5F, -27.0F, -7.5F)
        );
        PartDefinition left_back_leg = M.addOrReplaceChild(
            partdefinition,
            "left_back_leg",
            M.addBox(M.texOffs(CubeListBuilder.create(), 0, 28), -1.5F, 0.0F, -1.5F, 3.0F, 51.0F, 3.0F, new CubeDeformation(0.0F)),
            PartPose.offset(-3.5F, -27.0F, 7.5F)
        );
        PartDefinition head = M.addOrReplaceChild(
            partdefinition,
            "head",
            M.addBox(M.texOffs(CubeListBuilder.create(), 38, 0), -4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, -34.0F, -9.0F)
        );
        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void renderToBuffer(
        PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
    ) {
        M.render(this.body, poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        M.render(this.right_front_leg, poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        M.render(this.right_back_leg, poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        M.render(this.left_front_leg, poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        M.render(this.left_back_leg, poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        M.render(this.head, poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        M.set_yRot(this.head, netHeadYaw / (180.0F / (float)Math.PI));
        M.set_xRot(this.head, headPitch / (180.0F / (float)Math.PI));
        M.set_xRot(this.right_front_leg, Mth.cos(limbSwing * 0.6662F) * limbSwingAmount);
        M.set_xRot(this.left_back_leg, Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * limbSwingAmount);
        M.set_xRot(this.right_back_leg, Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * limbSwingAmount);
        M.set_xRot(this.left_front_leg, Mth.cos(limbSwing * 0.6662F) * limbSwingAmount);
    }
}
