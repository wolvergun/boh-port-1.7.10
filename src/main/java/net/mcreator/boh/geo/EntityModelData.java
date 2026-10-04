package net.mcreator.boh.geo;

public final class EntityModelData {
    private final boolean isSitting;
    private final boolean isChild;
    private final float netHeadYaw;
    private final float headPitch;

    public EntityModelData(boolean isSitting, boolean isChild, float netHeadYaw, float headPitch) {
        this.isSitting = isSitting;
        this.isChild = isChild;
        this.netHeadYaw = netHeadYaw;
        this.headPitch = headPitch;
    }

    public boolean isSitting() {
        return this.isSitting;
    }

    public boolean isChild() {
        return this.isChild;
    }

    public float netHeadYaw() {
        return this.netHeadYaw;
    }

    public float headPitch() {
        return this.headPitch;
    }
}
