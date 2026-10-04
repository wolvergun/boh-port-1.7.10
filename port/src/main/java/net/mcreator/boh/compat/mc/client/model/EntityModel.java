package net.mcreator.boh.compat.mc.client.model;

import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.minecraft.entity.Entity;

/** 1.20 EntityModel. */
public abstract class EntityModel<T extends Entity> {

    public float attackTime;
    public boolean riding;
    public boolean young;

    public abstract void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch);

    public abstract void renderToBuffer(PoseStack poseStack, VertexConsumer vc, int light, int overlay, float r, float g, float b, float a);

    public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTick) {}
}
