package net.mcreator.boh.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class Modelstilt_walker<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("boh", "modelstilt_walker"), "main");
   public final ModelPart body;
   public final ModelPart right_front_leg;
   public final ModelPart right_back_leg;
   public final ModelPart left_front_leg;
   public final ModelPart left_back_leg;
   public final ModelPart head;

   public Modelstilt_walker(ModelPart root) {
      this.body = root.getChild("body");
      this.right_front_leg = root.getChild("right_front_leg");
      this.right_back_leg = root.getChild("right_back_leg");
      this.left_front_leg = root.getChild("left_front_leg");
      this.left_back_leg = root.getChild("left_back_leg");
      this.head = root.getChild("head");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition body = partdefinition.addOrReplaceChild(
         "body",
         CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -5.0F, -9.0F, 10.0F, 10.0F, 18.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -32.0F, 0.0F)
      );
      PartDefinition right_front_leg = partdefinition.addOrReplaceChild(
         "right_front_leg",
         CubeListBuilder.create().texOffs(36, 28).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 51.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offset(3.5F, -27.0F, -7.5F)
      );
      PartDefinition right_back_leg = partdefinition.addOrReplaceChild(
         "right_back_leg",
         CubeListBuilder.create().texOffs(12, 28).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 51.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offset(3.5F, -27.0F, 7.5F)
      );
      PartDefinition left_front_leg = partdefinition.addOrReplaceChild(
         "left_front_leg",
         CubeListBuilder.create().texOffs(24, 28).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 51.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-3.5F, -27.0F, -7.5F)
      );
      PartDefinition left_back_leg = partdefinition.addOrReplaceChild(
         "left_back_leg",
         CubeListBuilder.create().texOffs(0, 28).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 51.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-3.5F, -27.0F, 7.5F)
      );
      PartDefinition head = partdefinition.addOrReplaceChild(
         "head",
         CubeListBuilder.create().texOffs(38, 0).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -34.0F, -9.0F)
      );
      return LayerDefinition.create(meshdefinition, 128, 128);
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.right_front_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.right_back_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.left_front_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.left_back_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.yRot = netHeadYaw / (180.0F / (float)Math.PI);
      this.head.xRot = headPitch / (180.0F / (float)Math.PI);
      this.right_front_leg.xRot = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
      this.left_back_leg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * limbSwingAmount;
      this.right_back_leg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * limbSwingAmount;
      this.left_front_leg.xRot = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
   }
}
