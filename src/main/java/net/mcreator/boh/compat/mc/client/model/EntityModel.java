package net.mcreator.boh.compat.mc.client.model;

import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.minecraft.entity.Entity;

public abstract class EntityModel<T extends Entity> {
    public float attackTime;
    public boolean riding;
    public boolean young;

    public abstract void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6);

    public abstract void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, float var5, float var6, float var7, float var8);

    public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTick) {
    }
}
