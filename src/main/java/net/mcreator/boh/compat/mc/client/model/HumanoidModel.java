package net.mcreator.boh.compat.mc.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

public class HumanoidModel<T> extends ModelBiped {
    public final HumanoidModel.Part head = new HumanoidModel.Part();
    public final HumanoidModel.Part hat = new HumanoidModel.Part();
    public final HumanoidModel.Part body = new HumanoidModel.Part();
    public final HumanoidModel.Part rightArm = new HumanoidModel.Part();
    public final HumanoidModel.Part leftArm = new HumanoidModel.Part();
    public final HumanoidModel.Part rightLeg = new HumanoidModel.Part();
    public final HumanoidModel.Part leftLeg = new HumanoidModel.Part();

    public HumanoidModel() {
    }

    public HumanoidModel(Object root) {
    }

    public HumanoidModel(float inflate) {
        super(inflate);
    }

    public void syncFromBiped() {
        copy(this.bipedHead, this.head);
        copy(this.bipedHeadwear, this.hat);
        copy(this.bipedBody, this.body);
        copy(this.bipedRightArm, this.rightArm);
        copy(this.bipedLeftArm, this.leftArm);
        copy(this.bipedRightLeg, this.rightLeg);
        copy(this.bipedLeftLeg, this.leftLeg);
    }

    private static void copy(ModelRenderer r, HumanoidModel.Part p) {
        p.xRot = r.rotateAngleX;
        p.yRot = r.rotateAngleY;
        p.zRot = r.rotateAngleZ;
        p.x = r.rotationPointX;
        p.y = r.rotationPointY;
        p.z = r.rotationPointZ;
        p.visible = r.showModel;
    }

    public static final class Part {
        public float xRot;
        public float yRot;
        public float zRot;
        public float x;
        public float y;
        public float z;
        public boolean visible = true;
    }
}
