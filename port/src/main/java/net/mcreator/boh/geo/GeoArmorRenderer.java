package net.mcreator.boh.geo;

import java.util.ArrayList;
import java.util.List;

import net.mcreator.boh.compat.client.BufferSource;
import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.OverlayTexture;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.mcreator.boh.compat.mc.client.model.HumanoidModel;
import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * GeckoLib GeoArmorRenderer port: a 1.7.10 ModelBiped (returned from Item.getArmorModel) that renders the geo
 * armor bones attached to the biped's parts.
 */
public class GeoArmorRenderer<T extends Item & GeoAnimatable> extends HumanoidModel<Object> implements GeoRenderer<T> {

    protected final List<GeoRenderLayer<T>> renderLayers = new ArrayList<>();
    protected final GeoModel<T> model;
    protected T animatable;
    protected Entity currentEntity;
    protected ItemStack currentStack;
    protected EquipmentSlot currentSlot;
    protected HumanoidModel<?> baseModel;
    protected float scaleWidth = 1, scaleHeight = 1;
    protected BakedGeoModel lastModel;

    protected GeoBone head, body, rightArm, leftArm, rightLeg, leftLeg, rightBoot, leftBoot;

    public GeoArmorRenderer(GeoModel<T> model) {
        super(1.0F);
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
    public long getInstanceId(T animatable) {
        return currentEntity == null ? System.identityHashCode(animatable) : currentEntity.getEntityId();
    }

    @Override
    public ResourceLocation getTextureLocation(T animatable) {
        return model.getTextureResource(animatable);
    }

    @Override
    public List<GeoRenderLayer<T>> getRenderLayers() {
        return renderLayers;
    }

    public GeoArmorRenderer<T> addRenderLayer(GeoRenderLayer<T> layer) {
        renderLayers.add(layer);
        return this;
    }

    protected GeoBone bone(String name) {
        BakedGeoModel m = lastModel;
        return m == null ? null : m.getBone(name);
    }

    public GeoBone getHeadBone() {
        return bone("armorHead");
    }

    public GeoBone getBodyBone() {
        return bone("armorBody");
    }

    public GeoBone getRightArmBone() {
        return bone("armorRightArm");
    }

    public GeoBone getLeftArmBone() {
        return bone("armorLeftArm");
    }

    public GeoBone getRightLegBone() {
        return bone("armorRightLeg");
    }

    public GeoBone getLeftLegBone() {
        return bone("armorLeftLeg");
    }

    public GeoBone getRightBootBone() {
        return bone("armorRightBoot");
    }

    public GeoBone getLeftBootBone() {
        return bone("armorLeftBoot");
    }

    @SuppressWarnings("unchecked")
    public void prepForRender(Entity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> base) {
        if (entity == null || slot == null) return;
        this.baseModel = base;
        this.currentEntity = entity;
        this.currentStack = stack;
        this.animatable = (T) stack.getItem();
        this.currentSlot = slot;
    }

    /** 1.7.10 armor pass: compute the biped pose, then draw the geo armor. */
    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float age, float headYaw, float headPitch, float scale) {
        setRotationAngles(limbSwing, limbSwingAmount, age, headYaw, headPitch, scale, entity);
        if (animatable == null) return;
        PoseStack pose = new PoseStack();
        BufferSource buffers = new BufferSource();
        float pt = RenderUtils.partialTick;
        int light = entity.getBrightnessForRender(pt);
        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        try {
            RenderType rt = getRenderType(animatable, getTextureLocation(animatable), buffers, pt);
            defaultRender(pose, animatable, buffers, rt, buffers.getBuffer(rt), 0, pt, light,
                entity instanceof EntityLivingBase && ((EntityLivingBase) entity).hurtTime > 0 ? OverlayTexture.pack(0, true) : OverlayTexture.NO_OVERLAY,
                1, 1, 1, 1);
        } finally {
            buffers.endBatch();
            GL11.glPopMatrix();
        }
    }

    @Override
    public void preRender(PoseStack poseStack, T animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer,
        boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (lastModel != model) {
            lastModel = model;
            head = getHeadBone();
            body = getBodyBone();
            rightArm = getRightArmBone();
            leftArm = getLeftArmBone();
            rightLeg = getRightLegBone();
            leftLeg = getLeftLegBone();
            rightBoot = getRightBootBone();
            leftBoot = getLeftBootBone();
        }
        applyBaseTransformations();
        if (isChild && !isReRender) {
            poseStack.scale(0.7F, 0.7F, 0.7F);
            poseStack.translate(0, 0.65F, 0);
        }
        if (!isReRender && (scaleWidth != 1 || scaleHeight != 1)) poseStack.scale(scaleWidth, scaleHeight, scaleWidth);
        applyBoneVisibilityBySlot();
    }

    protected void applyBaseTransformations() {
        match(bipedHead, head, 0, 0);
        match(bipedBody, body, 0, 0);
        match(bipedRightArm, rightArm, 5, 2);
        match(bipedLeftArm, leftArm, -5, 2);
        match(bipedRightLeg, rightLeg, 2, 12);
        match(bipedLeftLeg, leftLeg, -2, 12);
        match(bipedRightLeg, rightBoot, 2, 12);
        match(bipedLeftLeg, leftBoot, -2, 12);
    }

    private static void match(ModelRenderer part, GeoBone bone, float dx, float dy) {
        if (bone == null) return;
        bone.setRotX(-part.rotateAngleX);
        bone.setRotY(-part.rotateAngleY);
        bone.setRotZ(part.rotateAngleZ);
        bone.setPosX(part.rotationPointX + dx);
        bone.setPosY(dy - part.rotationPointY);
        bone.setPosZ(part.rotationPointZ);
    }

    protected void applyBoneVisibilityBySlot() {
        for (GeoBone b : new GeoBone[] { head, body, rightArm, leftArm, rightLeg, leftLeg, rightBoot, leftBoot }) if (b != null) b.setHidden(true);
        if (currentSlot == null) return;
        switch (currentSlot) {
            case HEAD:
                show(head);
                break;
            case CHEST:
                show(body);
                show(rightArm);
                show(leftArm);
                break;
            case LEGS:
                show(rightLeg);
                show(leftLeg);
                break;
            case FEET:
                show(rightBoot);
                show(leftBoot);
                break;
            default:
        }
    }

    private static void show(GeoBone b) {
        if (b != null) b.setHidden(false);
    }

    @Override
    public void actuallyRender(PoseStack poseStack, T animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource,
        VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
        float alpha) {
        poseStack.pushPose();
        poseStack.translate(0, 24 / 16f, 0);
        poseStack.scale(-1, -1, 1);
        if (!isReRender) {
            AnimationState<T> state = new AnimationState<>(animatable, 0, 0, partialTick, false);
            long id = getInstanceId(animatable);
            state.setData(DataTickets.TICK, animatable.getTick(currentEntity));
            state.setData(DataTickets.ENTITY, currentEntity);
            this.model.addAdditionalStateData(animatable, id, state);
            this.model.handleAnimations(animatable, id, state);
        }
        GeoRenderer.super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight,
            packedOverlay, red, green, blue, alpha);
        poseStack.popPose();
    }
}
