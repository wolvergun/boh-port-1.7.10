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

public class GeoArmorRenderer<T extends Item & GeoAnimatable> extends HumanoidModel<Object> implements GeoRenderer<T> {
    protected final List<GeoRenderLayer<T>> renderLayers = new ArrayList<>();
    protected final GeoModel<T> model;
    protected T animatable;
    protected Entity currentEntity;
    protected ItemStack currentStack;
    protected EquipmentSlot currentSlot;
    protected HumanoidModel<?> baseModel;
    protected float scaleWidth = 1.0F;
    protected float scaleHeight = 1.0F;
    protected BakedGeoModel lastModel;
    protected GeoBone head;
    protected GeoBone body;
    protected GeoBone rightArm;
    protected GeoBone leftArm;
    protected GeoBone rightLeg;
    protected GeoBone leftLeg;
    protected GeoBone rightBoot;
    protected GeoBone leftBoot;

    public GeoArmorRenderer(GeoModel<T> model) {
        super(1.0F);
        this.model = model;
    }

    @Override
    public GeoModel<T> getGeoModel() {
        return this.model;
    }

    public T getAnimatable() {
        return this.animatable;
    }

    public long getInstanceId(T animatable) {
        return this.currentEntity == null ? System.identityHashCode(animatable) : this.currentEntity.getEntityId();
    }

    public ResourceLocation getTextureLocation(T animatable) {
        return this.model.getTextureResource(animatable);
    }

    @Override
    public List<GeoRenderLayer<T>> getRenderLayers() {
        return this.renderLayers;
    }

    public GeoArmorRenderer<T> addRenderLayer(GeoRenderLayer<T> layer) {
        this.renderLayers.add(layer);
        return this;
    }

    protected GeoBone bone(String name) {
        BakedGeoModel m = this.lastModel;
        return m == null ? null : m.getBone(name);
    }

    public GeoBone getHeadBone() {
        return this.bone("armorHead");
    }

    public GeoBone getBodyBone() {
        return this.bone("armorBody");
    }

    public GeoBone getRightArmBone() {
        return this.bone("armorRightArm");
    }

    public GeoBone getLeftArmBone() {
        return this.bone("armorLeftArm");
    }

    public GeoBone getRightLegBone() {
        return this.bone("armorRightLeg");
    }

    public GeoBone getLeftLegBone() {
        return this.bone("armorLeftLeg");
    }

    public GeoBone getRightBootBone() {
        return this.bone("armorRightBoot");
    }

    public GeoBone getLeftBootBone() {
        return this.bone("armorLeftBoot");
    }

    public void prepForRender(Entity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> base) {
        if (entity != null && slot != null) {
            this.baseModel = base;
            this.currentEntity = entity;
            this.currentStack = stack;
            this.animatable = (T)stack.getItem();
            this.currentSlot = slot;
        }
    }

    public void render(Entity entity, float limbSwing, float limbSwingAmount, float age, float headYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, age, headYaw, headPitch, scale, entity);
        if (this.animatable != null) {
            PoseStack pose = new PoseStack();
            BufferSource buffers = new BufferSource();
            float pt = RenderUtils.partialTick;
            int light = entity.getBrightnessForRender(pt);
            GL11.glPushMatrix();
            GL11.glEnable(32826);

            try {
                RenderType rt = this.getRenderType(this.animatable, this.getTextureLocation(this.animatable), buffers, pt);
                this.defaultRender(
                    pose,
                    this.animatable,
                    buffers,
                    rt,
                    buffers.getBuffer(rt),
                    0.0F,
                    pt,
                    light,
                    entity instanceof EntityLivingBase && ((EntityLivingBase)entity).hurtTime > 0
                        ? OverlayTexture.pack(0.0F, true)
                        : OverlayTexture.NO_OVERLAY,
                    1.0F,
                    1.0F,
                    1.0F,
                    1.0F
                );
            } finally {
                buffers.endBatch();
                GL11.glPopMatrix();
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
        if (this.lastModel != model) {
            this.lastModel = model;
            this.head = this.getHeadBone();
            this.body = this.getBodyBone();
            this.rightArm = this.getRightArmBone();
            this.leftArm = this.getLeftArmBone();
            this.rightLeg = this.getRightLegBone();
            this.leftLeg = this.getLeftLegBone();
            this.rightBoot = this.getRightBootBone();
            this.leftBoot = this.getLeftBootBone();
        }

        this.applyBaseTransformations();
        if (this.isChild && !isReRender) {
            poseStack.scale(0.7F, 0.7F, 0.7F);
            poseStack.translate(0.0F, 0.65F, 0.0F);
        }

        if (!isReRender && (this.scaleWidth != 1.0F || this.scaleHeight != 1.0F)) {
            poseStack.scale(this.scaleWidth, this.scaleHeight, this.scaleWidth);
        }

        this.applyBoneVisibilityBySlot();
    }

    protected void applyBaseTransformations() {
        match(this.bipedHead, this.head, 0.0F, 0.0F);
        match(this.bipedBody, this.body, 0.0F, 0.0F);
        match(this.bipedRightArm, this.rightArm, 5.0F, 2.0F);
        match(this.bipedLeftArm, this.leftArm, -5.0F, 2.0F);
        match(this.bipedRightLeg, this.rightLeg, 2.0F, 12.0F);
        match(this.bipedLeftLeg, this.leftLeg, -2.0F, 12.0F);
        match(this.bipedRightLeg, this.rightBoot, 2.0F, 12.0F);
        match(this.bipedLeftLeg, this.leftBoot, -2.0F, 12.0F);
    }

    private static void match(ModelRenderer part, GeoBone bone, float dx, float dy) {
        if (bone != null) {
            bone.setRotX(-part.rotateAngleX);
            bone.setRotY(-part.rotateAngleY);
            bone.setRotZ(part.rotateAngleZ);
            bone.setPosX(part.rotationPointX + dx);
            bone.setPosY(dy - part.rotationPointY);
            bone.setPosZ(part.rotationPointZ);
        }
    }

    protected void applyBoneVisibilityBySlot() {
        for (GeoBone b : new GeoBone[]{this.head, this.body, this.rightArm, this.leftArm, this.rightLeg, this.leftLeg, this.rightBoot, this.leftBoot}) {
            if (b != null) {
                b.setHidden(true);
            }
        }

        if (this.currentSlot != null) {
            switch (this.currentSlot) {
                case HEAD:
                    show(this.head);
                    break;
                case CHEST:
                    show(this.body);
                    show(this.rightArm);
                    show(this.leftArm);
                    break;
                case LEGS:
                    show(this.rightLeg);
                    show(this.leftLeg);
                    break;
                case FEET:
                    show(this.rightBoot);
                    show(this.leftBoot);
            }
        }
    }

    private static void show(GeoBone b) {
        if (b != null) {
            b.setHidden(false);
        }
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
        poseStack.translate(0.0F, 1.5F, 0.0F);
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        if (!isReRender) {
            AnimationState<T> state = new AnimationState<>(animatable, 0.0F, 0.0F, partialTick, false);
            long id = this.getInstanceId(animatable);
            state.setData(DataTickets.TICK, animatable.getTick(this.currentEntity));
            state.setData(DataTickets.ENTITY, this.currentEntity);
            this.model.addAdditionalStateData(animatable, id, state);
            this.model.handleAnimations(animatable, id, state);
        }

        GeoRenderer.super.actuallyRender(
            poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha
        );
        poseStack.popPose();
    }
}
