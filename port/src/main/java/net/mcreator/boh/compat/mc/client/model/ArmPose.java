package net.mcreator.boh.compat.mc.client.model;

import net.mcreator.boh.compat.mc.world.entity.HumanoidArm;
import net.minecraft.entity.EntityLivingBase;

/** 1.20 HumanoidModel.ArmPose (custom poses from IClientItemExtensions). */
public final class ArmPose {

    @FunctionalInterface
    public interface Transform {

        void apply(HumanoidModel<?> model, EntityLivingBase entity, HumanoidArm arm);
    }

    public static final ArmPose EMPTY = new ArmPose("EMPTY", false, null);
    public static final ArmPose ITEM = new ArmPose("ITEM", false, null);

    public final String name;
    public final boolean twoHanded;
    public final Transform transform;

    private ArmPose(String name, boolean twoHanded, Transform transform) {
        this.name = name;
        this.twoHanded = twoHanded;
        this.transform = transform;
    }

    public static ArmPose create(String name, boolean twoHanded, Transform transform) {
        return new ArmPose(name, twoHanded, transform);
    }

    public boolean isTwoHanded() {
        return twoHanded;
    }
}
