package net.mcreator.boh.compat.mc.client.model;

import net.minecraft.client.model.ModelBiped;

/**
 * 1.20 HumanoidModel mapped onto 1.7.10 ModelBiped so it can be returned as an armor model. Part fields mirror the
 * biped renderers' angles (radians) for code that reads/writes them.
 */
public class HumanoidModel<T> extends ModelBiped {

    public static final class Part {

        public float xRot, yRot, zRot, x, y, z;
        public boolean visible = true;
    }

    public final Part head = new Part(), hat = new Part(), body = new Part(), rightArm = new Part(), leftArm = new Part(),
        rightLeg = new Part(), leftLeg = new Part();

    public HumanoidModel() {
        super();
    }

    public HumanoidModel(Object root) {
        super();
    }

    public HumanoidModel(float inflate) {
        super(inflate);
    }

    /** Copies the 1.7.10 biped angles into the 1.20-style part fields. */
    public void syncFromBiped() {
        copy(bipedHead, head);
        copy(bipedHeadwear, hat);
        copy(bipedBody, body);
        copy(bipedRightArm, rightArm);
        copy(bipedLeftArm, leftArm);
        copy(bipedRightLeg, rightLeg);
        copy(bipedLeftLeg, leftLeg);
    }

    private static void copy(net.minecraft.client.model.ModelRenderer r, Part p) {
        p.xRot = r.rotateAngleX;
        p.yRot = r.rotateAngleY;
        p.zRot = r.rotateAngleZ;
        p.x = r.rotationPointX;
        p.y = r.rotationPointY;
        p.z = r.rotationPointZ;
        p.visible = r.showModel;
    }
}
