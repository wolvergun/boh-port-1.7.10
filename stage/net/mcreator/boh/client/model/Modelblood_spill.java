package net.mcreator.boh.client.model;

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
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.M;

public class Modelblood_spill<T extends Entity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("boh", "modelblood_spill"), "main");

    public final ModelPart bone;

    public Modelblood_spill(ModelPart root) {
        this.bone = M.getChild(root, "bone");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = M.getRoot(meshdefinition);
        PartDefinition bone = M.addOrReplaceChild(partdefinition, "bone", M.addBox(M.texOffs(CubeListBuilder.create(), 0, 0), -3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        M.render(this.bone, poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }
}
