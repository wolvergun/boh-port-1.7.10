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
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GeoEntityRenderer<T extends Entity & GeoAnimatable> extends Render implements GeoRenderer<T> {
    protected final List<GeoRenderLayer<T>> renderLayers = new ArrayList<>();
    protected final GeoModel<T> model;
    protected T animatable;
    protected float scaleWidth = 1.0F;
    protected float scaleHeight = 1.0F;
    public float shadowRadius = 0.5F;
    public float shadowStrength = 1.0F;

    public GeoEntityRenderer(Object context, GeoModel<T> model) {
        this.model = model;
    }

    public GeoEntityRenderer(GeoModel<T> model) {
        this.model = model;
    }

    @Override
    public GeoModel<T> getGeoModel() {
        return this.model;
    }

    public T getAnimatable() {
        return this.animatable;
    }

    @Override
    public List<GeoRenderLayer<T>> getRenderLayers() {
        return this.renderLayers;
    }

    public GeoEntityRenderer<T> addRenderLayer(GeoRenderLayer<T> layer) {
        this.renderLayers.add(layer);
        return this;
    }

    public GeoEntityRenderer<T> withScale(float scale) {
        return this.withScale(scale, scale);
    }

    public GeoEntityRenderer<T> withScale(float w, float h) {
        this.scaleWidth = w;
        this.scaleHeight = h;
        return this;
    }

    public long getInstanceId(T animatable) {
        return animatable.getEntityId();
    }

    public ResourceLocation getTextureLocation(T animatable) {
        return this.model.getTextureResource(animatable);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getTextureLocation((T)entity);
    }

    protected float getMotionAnimThreshold(T animatable) {
        return 0.015F;
    }

    protected float getDeathMaxRotation(T animatable) {
        return 90.0F;
    }

    public RenderManager getRenderManager() {
        return this.renderManager;
    }

    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTick) {
        T e = (T)entity;
        this.animatable = (T)entity;
        this.shadowSize = this.shadowRadius;
        RenderUtils.partialTick = partialTick;
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glEnable(32826);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        BufferSource buffers = new BufferSource();
        PoseStack poseStack = new PoseStack();
        int light = entity.getBrightnessForRender(partialTick);
        int overlay = OverlayTexture.NO_OVERLAY;
        if (entity instanceof EntityLivingBase l) {
            overlay = OverlayTexture.pack(0.0F, l.hurtTime > 0 || l.deathTime > 0);
        }

        float alpha = entity.isInvisible() ? (entity.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer) ? 0.0F : 0.15F) : 1.0F;

        try {
            if (alpha > 0.0F) {
                this.defaultRender(poseStack, e, buffers, null, null, entityYaw, partialTick, light, overlay, 1.0F, 1.0F, 1.0F, alpha);
            }
        } finally {
            buffers.endBatch();
            GL11.glDisable(32826);
            GL11.glEnable(2884);
            GL11.glDisable(3042);
            GL11.glDepthMask(true);
            GL11.glPopMatrix();
        }

        this.renderName((T)entity, x, y, z);
        this.animatable = null;
    }

    protected void renderName(T entity, double x, double y, double z) {
        if (entity instanceof EntityLivingBase l) {
            if (Minecraft.isGuiEnabled() && l != this.renderManager.livingPlayer && l.riddenByEntity == null) {
                boolean show = l instanceof EntityLiving && ((EntityLiving)l).getAlwaysRenderNameTagForRender();
                if (show || l instanceof EntityLiving && ((EntityLiving)l).hasCustomNameTag() && l == this.renderManager.field_147941_i) {
                    String name = l.func_145748_c_().getFormattedText();
                    if (l.getDistanceSqToEntity(this.renderManager.livingPlayer) < 4096.0) {
                        this.func_147906_a(l, name, x, y, z, 64);
                    }
                }
            }
        }
    }

    public void preRender(
        PoseStack poseStack,
        T animatable,
        BakedGeoModel model,
        MultiBufferSource bufferSource,
        VertexConsumer buffer,
        boolean isReRender,
        float partialTick,
        int packedLight,
        int packedOverlay,
        float red,
        float green,
        float blue,
        float alpha
    ) {
        if (!isReRender && (this.scaleWidth != 1.0F || this.scaleHeight != 1.0F)) {
            poseStack.scale(this.scaleWidth, this.scaleHeight, this.scaleWidth);
        }
    }

    public RenderType getRenderType(T animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(texture);
    }

    public void actuallyRender(
        PoseStack poseStack,
        T animatable,
        BakedGeoModel model,
        RenderType renderType,
        MultiBufferSource bufferSource,
        VertexConsumer buffer,
        boolean isReRender,
        float partialTick,
        int packedLight,
        int packedOverlay,
        float red,
        float green,
        float blue,
        float alpha
    ) {
        poseStack.pushPose();
        EntityLivingBase living = animatable instanceof EntityLivingBase ? (EntityLivingBase)animatable : null;
        boolean shouldSit = animatable.ridingEntity != null && animatable.ridingEntity.shouldRiderSit();
        float lerpBodyRot = living == null
            ? animatable.prevRotationYaw + wrap(animatable.rotationYaw - animatable.prevRotationYaw) * partialTick
            : living.prevRenderYawOffset + wrap(living.renderYawOffset - living.prevRenderYawOffset) * partialTick;
        float lerpHeadRot = living == null ? lerpBodyRot : living.prevRotationYawHead + wrap(living.rotationYawHead - living.prevRotationYawHead) * partialTick;
        float netHeadYaw = lerpHeadRot - lerpBodyRot;
        if (shouldSit && animatable.ridingEntity instanceof EntityLivingBase v) {
            lerpBodyRot = v.prevRenderYawOffset + wrap(v.renderYawOffset - v.prevRenderYawOffset) * partialTick;
            netHeadYaw = lerpHeadRot - lerpBodyRot;
            float clamped = Math.max(-85.0F, Math.min(85.0F, wrap(netHeadYaw)));
            lerpBodyRot = lerpHeadRot - clamped;
            if (clamped * clamped > 2500.0F) {
                lerpBodyRot += clamped * 0.2F;
            }

            netHeadYaw = lerpHeadRot - lerpBodyRot;
        }

        float ageInTicks = animatable.ticksExisted + partialTick;
        float limbSwingAmount = 0.0F;
        float limbSwing = 0.0F;
        this.applyRotations(animatable, poseStack, ageInTicks, lerpBodyRot, partialTick);
        if (!shouldSit && animatable.isEntityAlive() && living != null) {
            limbSwingAmount = living.prevLimbSwingAmount + (living.limbSwingAmount - living.prevLimbSwingAmount) * partialTick;
            limbSwing = living.limbSwing - living.limbSwingAmount * (1.0F - partialTick);
            if (living.isChild()) {
                limbSwing *= 3.0F;
            }

            if (limbSwingAmount > 1.0F) {
                limbSwingAmount = 1.0F;
            }
        }

        if (!isReRender) {
            float headPitch = animatable.prevRotationPitch + (animatable.rotationPitch - animatable.prevRotationPitch) * partialTick;
            double vx = animatable.posX - animatable.prevPosX;
            double vz = animatable.posZ - animatable.prevPosZ;
            float avgVelocity = (float)((Math.abs(vx) + Math.abs(vz)) / 2.0);
            AnimationState<T> state = new AnimationState<>(
                animatable, limbSwing, limbSwingAmount, partialTick, avgVelocity >= this.getMotionAnimThreshold(animatable) && limbSwingAmount != 0.0F
            );
            long instanceId = this.getInstanceId(animatable);
            state.setData(DataTickets.TICK, animatable.getTick(animatable));
            state.setData(DataTickets.ENTITY, animatable);
            state.setData(DataTickets.ENTITY_MODEL_DATA, new EntityModelData(shouldSit, living != null && living.isChild(), -netHeadYaw, -headPitch));
            this.model.addAdditionalStateData(animatable, instanceId, state);
            this.model.handleAnimations(animatable, instanceId, state);
        }

        poseStack.translate(0.0F, 0.01F, 0.0F);
        GeoRenderer.super.actuallyRender(
            poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha
        );
        poseStack.popPose();
    }

    protected void applyRotations(T animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        EntityLivingBase living = animatable instanceof EntityLivingBase ? (EntityLivingBase)animatable : null;
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - rotationYaw));
        if (living != null && living.deathTime > 0) {
            float deathRotation = (living.deathTime + partialTick - 1.0F) / 20.0F * 1.6F;
            poseStack.mulPose(Axis.ZP.rotationDegrees(Math.min((float)Math.sqrt(deathRotation), 1.0F) * this.getDeathMaxRotation(animatable)));
        } else if (living != null && living instanceof EntityLiving && ((EntityLiving)living).hasCustomNameTag()) {
            String name = EnumChatFormatting.getTextWithoutFormattingCodes(((EntityLiving)living).getCustomNameTag());
            if ("Dinnerbone".equals(name) || "Grumm".equalsIgnoreCase(name)) {
                poseStack.translate(0.0F, animatable.height + 0.1F, 0.0F);
                poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
            }
        }
    }

    private static float wrap(float deg) {
        deg %= 360.0F;
        if (deg >= 180.0F) {
            deg -= 360.0F;
        }

        if (deg < -180.0F) {
            deg += 360.0F;
        }

        return deg;
    }
}
