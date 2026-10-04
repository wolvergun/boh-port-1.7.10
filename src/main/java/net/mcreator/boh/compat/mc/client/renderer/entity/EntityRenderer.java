package net.mcreator.boh.compat.mc.client.renderer.entity;

import net.mcreator.boh.compat.client.BufferSource;
import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.PoseStack;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public abstract class EntityRenderer<T extends Entity> extends Render {
    public float shadowRadius;
    public float shadowStrength = 1.0F;

    protected EntityRenderer(Context context) {
    }

    public abstract ResourceLocation getTextureLocation(T var1);

    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffers, int light) {
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTick) {
        this.shadowSize = this.shadowRadius;
        this.shadowOpaque = this.shadowStrength;
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glEnable(32826);
        BufferSource buffers = new BufferSource();

        try {
            this.render((T)entity, yaw, partialTick, new PoseStack(), buffers, entity.getBrightnessForRender(partialTick));
        } finally {
            buffers.endBatch();
            GL11.glDisable(32826);
            GL11.glPopMatrix();
        }
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getTextureLocation((T)entity);
    }
}
