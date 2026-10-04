package net.mcreator.boh.compat.mc.client.model;

import net.mcreator.boh.compat.mc.world.entity.HumanoidArm;
import net.minecraft.entity.EntityLivingBase;

public final class ArmPose {
    public static final ArmPose EMPTY = new ArmPose("EMPTY", false, null);
    public static final ArmPose ITEM = new ArmPose("ITEM", false, null);
    public final String name;
    public final boolean twoHanded;
    public final ArmPose.Transform transform;

    private ArmPose(String name, boolean twoHanded, ArmPose.Transform transform) {
        this.name = name;
        this.twoHanded = twoHanded;
        this.transform = transform;
    }

    public static ArmPose create(String name, boolean twoHanded, ArmPose.Transform transform) {
        return new ArmPose(name, twoHanded, transform);
    }

    public boolean isTwoHanded() {
        return this.twoHanded;
    }

    @FunctionalInterface
    public interface Transform {
        void apply(HumanoidModel<?> var1, EntityLivingBase var2, HumanoidArm var3);
    }
}
