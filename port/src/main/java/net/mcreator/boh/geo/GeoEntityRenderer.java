package net.mcreator.boh.geo;

import java.util.ArrayList;
import java.util.List;

import net.mcreator.boh.compat.client.Axis;
import net.mcreator.boh.compat.client.BufferSource;
import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.OverlayTexture;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/** Renders a geo-model entity through the 1.7.10 {@link Render} pipeline. */
public class GeoEntityRenderer<T extends Entity & GeoAnimatable> extends Render implements GeoRenderer<T> {

    protected final List<GeoRenderLayer<T>> renderLayers = new ArrayList<>();
    protected final GeoModel<T> model;
    protected T animatable;
    protected float scaleWidth = 1;
    protected float scaleHeight = 1;
    public float shadowRadius = 0.5f;
    public float shadowStrength = 1f;

    public GeoEntityRenderer(Object context, GeoModel<T> model) {
        this.model = model;
    }

    public GeoEntityRenderer(GeoModel<T> model) {
        this.model = model;
    }

    @Override
    public GeoModel<T> getGeoModel() {
        return model;
    }

    @Override
    public T getAnimatable() {
        return animatable;
    }

    @Override
    public List<GeoRenderLayer<T>> getRenderLayers() {
        return renderLayers;
    }

    public GeoEntityRenderer<T> addRenderLayer(GeoRenderLayer<T> layer) {
        renderLayers.add(layer);
        return this;
    }

    public GeoEntityRenderer<T> withScale(float scale) {
        return withScale(scale, scale);
    }

    public GeoEntityRenderer<T> withScale(float w, float h) {
        scaleWidth = w;
        scaleHeight = h;
        return this;
    }

    @Override
    public long getInstanceId(T animatable) {
        return animatable.getEntityId();
    }

    @Override
    public ResourceLocation getTextureLocation(T animatable) {
        return model.getTextureResource(animatable);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected ResourceLocation getEntityTexture(Entity entity) {
        return getTextureLocation((T) entity);
    }

    protected float getMotionAnimThreshold(T animatable) {
        return 0.015f;
    }

    protected float getDeathMaxRotation(T animatable) {
        return 90f;
    }

    public RenderManager getRenderManager() {
        return renderManager;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTick) {
        T e = (T) entity;
        animatable = e;
        shadowSize = shadowRadius;
        RenderUtils.partialTick = partialTick;

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1, 1, 1, 1);
        BufferSource buffers = new BufferSource();
        PoseStack poseStack = new PoseStack();
        int light = entity.getBrightnessForRender(partialTick);
        int overlay = OverlayTexture.NO_OVERLAY;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase l = (EntityLivingBase) entity;
            overlay = OverlayTexture.pack(0, l.hurtTime > 0 || l.deathTime > 0);
        }
        float alpha = entity.isInvisible() ? (entity.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer) ? 0f : 0.15f) : 1f;
        try {
            if (alpha > 0) defaultRender(poseStack, e, buffers, null, null, entityYaw, partialTick, light, overlay, 1, 1, 1, alpha);
        } finally {
            buffers.endBatch();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDepthMask(true);
            GL11.glPopMatrix();
        }
        renderName(e, x, y, z);
        animatable = null;
    }

    protected void renderName(T entity, double x, double y, double z) {
        if (!(entity instanceof EntityLivingBase)) return;
        EntityLivingBase l = (EntityLivingBase) entity;
        if (!Minecraft.isGuiEnabled() || l == renderManager.livingPlayer || l.riddenByEntity != null) return;
        boolean show = l instanceof net.minecraft.entity.EntityLiving
            && ((net.minecraft.entity.EntityLiving) l).getAlwaysRenderNameTagForRender();
        if (!show && !(l instanceof net.minecraft.entity.EntityLiving
            && ((net.minecraft.entity.EntityLiving) l).hasCustomNameTag()
            && l == renderManager.field_147941_i)) return;
        String name = l.func_145748_c_()
            .getFormattedText();
        if (l.getDistanceSqToEntity(renderManager.livingPlayer) < 64 * 64) {
            func_147906_a(l, name, x, y, z, 64);
        }
    }

    @Override
    public void preRender(PoseStack poseStack, T animatable, BakedGeoModel model, MultiBufferSource bufferSource,
        VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red,
        float green, float blue, float alpha) {
        if (!isReRender && (scaleWidth != 1 || scaleHeight != 1)) poseStack.scale(scaleWidth, scaleHeight, scaleWidth);
    }

    @Override
    public RenderType getRenderType(T animatable, ResourceLocation texture, MultiBufferSource bufferSource,
        float partialTick) {
        return RenderType.entityCutoutNoCull(texture);
    }

    @Override
    public void actuallyRender(PoseStack poseStack, T animatable, BakedGeoModel model, RenderType renderType,
        MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight,
        int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        EntityLivingBase living = animatable instanceof EntityLivingBase ? (EntityLivingBase) animatable : null;
        boolean shouldSit = animatable.ridingEntity != null && animatable.ridingEntity.shouldRiderSit();
        float lerpBodyRot = living == null ? animatable.prevRotationYaw + wrap(animatable.rotationYaw - animatable.prevRotationYaw) * partialTick
            : living.prevRenderYawOffset + wrap(living.renderYawOffset - living.prevRenderYawOffset) * partialTick;
        float lerpHeadRot = living == null ? lerpBodyRot
            : living.prevRotationYawHead + wrap(living.rotationYawHead - living.prevRotationYawHead) * partialTick;
        float netHeadYaw = lerpHeadRot - lerpBodyRot;

        if (shouldSit && animatable.ridingEntity instanceof EntityLivingBase) {
            EntityLivingBase v = (EntityLivingBase) animatable.ridingEntity;
            lerpBodyRot = v.prevRenderYawOffset + wrap(v.renderYawOffset - v.prevRenderYawOffset) * partialTick;
            netHeadYaw = lerpHeadRot - lerpBodyRot;
            float clamped = Math.max(-85, Math.min(85, wrap(netHeadYaw)));
            lerpBodyRot = lerpHeadRot - clamped;
            if (clamped * clamped > 2500f) lerpBodyRot += clamped * 0.2f;
            netHeadYaw = lerpHeadRot - lerpBodyRot;
        }

        float ageInTicks = animatable.ticksExisted + partialTick;
        float limbSwingAmount = 0;
        float limbSwing = 0;
        applyRotations(animatable, poseStack, ageInTicks, lerpBodyRot, partialTick);

        if (!shouldSit && animatable.isEntityAlive() && living != null) {
            limbSwingAmount = living.prevLimbSwingAmount + (living.limbSwingAmount - living.prevLimbSwingAmount) * partialTick;
            limbSwing = living.limbSwing - living.limbSwingAmount * (1 - partialTick);
            if (living.isChild()) limbSwing *= 3f;
            if (limbSwingAmount > 1f) limbSwingAmount = 1f;
        }

        if (!isReRender) {
            float headPitch = animatable.prevRotationPitch + (animatable.rotationPitch - animatable.prevRotationPitch) * partialTick;
            double vx = animatable.posX - animatable.prevPosX, vz = animatable.posZ - animatable.prevPosZ;
            float avgVelocity = (float) ((Math.abs(vx) + Math.abs(vz)) / 2f);
            AnimationState<T> state = new AnimationState<>(animatable, limbSwing, limbSwingAmount, partialTick,
                avgVelocity >= getMotionAnimThreshold(animatable) && limbSwingAmount != 0);
            long instanceId = getInstanceId(animatable);
            state.setData(DataTickets.TICK, animatable.getTick(animatable));
            state.setData(DataTickets.ENTITY, animatable);
            state.setData(DataTickets.ENTITY_MODEL_DATA,
                new EntityModelData(shouldSit, living != null && living.isChild(), -netHeadYaw, -headPitch));
            this.model.addAdditionalStateData(animatable, instanceId, state);
            this.model.handleAnimations(animatable, instanceId, state);
        }

        poseStack.translate(0, 0.01f, 0);
        GeoRenderer.super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender,
            partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.popPose();
    }

    protected void applyRotations(T animatable, PoseStack poseStack, float ageInTicks, float rotationYaw,
        float partialTick) {
        EntityLivingBase living = animatable instanceof EntityLivingBase ? (EntityLivingBase) animatable : null;
        poseStack.mulPose(Axis.YP.rotationDegrees(180f - rotationYaw));
        if (living != null && living.deathTime > 0) {
            float deathRotation = (living.deathTime + partialTick - 1f) / 20f * 1.6f;
            poseStack.mulPose(Axis.ZP.rotationDegrees(Math.min((float) Math.sqrt(deathRotation), 1) * getDeathMaxRotation(animatable)));
        } else if (living != null && living instanceof net.minecraft.entity.EntityLiving
            && ((net.minecraft.entity.EntityLiving) living).hasCustomNameTag()) {
            String name = EnumChatFormatting.getTextWithoutFormattingCodes(((net.minecraft.entity.EntityLiving) living).getCustomNameTag());
            if ("Dinnerbone".equals(name) || "Grumm".equalsIgnoreCase(name)) {
                poseStack.translate(0, animatable.height + 0.1f, 0);
                poseStack.mulPose(Axis.ZP.rotationDegrees(180f));
            }
        }
    }

    private static float wrap(float deg) {
        deg %= 360f;
        if (deg >= 180f) deg -= 360f;
        if (deg < -180f) deg += 360f;
        return deg;
    }
}
