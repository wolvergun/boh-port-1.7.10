package net.mcreator.boh.compat.mc.client.renderer.entity;

import net.mcreator.boh.compat.client.BufferSource;
import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.PoseStack;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/** 1.20 EntityRenderer on top of 1.7.10 Render: doRender sets up a PoseStack at the entity and calls render(). */
public abstract class EntityRenderer<T extends Entity> extends Render {

    public float shadowRadius;
    public float shadowStrength = 1;

    protected EntityRenderer(Context context) {}

    public abstract ResourceLocation getTextureLocation(T entity);

    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffers, int light) {}

    @Override
    @SuppressWarnings("unchecked")
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTick) {
        shadowSize = shadowRadius;
        shadowOpaque = shadowStrength;
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        BufferSource buffers = new BufferSource();
        try {
            render((T) entity, yaw, partialTick, new PoseStack(), buffers, entity.getBrightnessForRender(partialTick));
        } finally {
            buffers.endBatch();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected ResourceLocation getEntityTexture(Entity entity) {
        return getTextureLocation((T) entity);
    }
}
