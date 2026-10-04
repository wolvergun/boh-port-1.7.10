package net.mcreator.boh.compat.client;

import net.mcreator.boh.compat.forge.client.extensions.common.IClientItemExtensions;
import net.mcreator.boh.compat.mc.client.model.ArmPose;
import net.mcreator.boh.compat.mc.client.model.HumanoidModel;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.entity.HumanoidArm;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.ReflectionHelper;

/**
 * Custom third-person arm poses from 1.20 IClientItemExtensions.getArmPose (guns held up to aim, the crucifix, the
 * drill...). The player renderer's biped models are replaced by {@link PosedBiped}, which applies the held item's pose
 * after vanilla has set the angles, as 1.20's HumanoidModel.setupAnim does.
 */
public final class ArmPoses {

    private ArmPoses() {}

    public static void install() {
        Render r = (Render) RenderManager.instance.entityRenderMap.get(EntityPlayer.class);
        if (!(r instanceof RenderPlayer)) {
            net.mcreator.boh.BohMod.LOGGER.warn("Arm poses: player renderer is {}, not replaced", r == null ? null : r.getClass().getName());
            return;
        }
        RenderPlayer rp = (RenderPlayer) r;
        if (rp.modelBipedMain.getClass() != ModelBiped.class) {
            // another mod already replaced the player model; leave it alone
            net.mcreator.boh.BohMod.LOGGER.warn("Arm poses: player model is {}, not replaced", rp.modelBipedMain.getClass().getName());
            return;
        }
        rp.modelBipedMain = new PosedBiped(0.0F);
        if (rp.modelArmorChestplate.getClass() == ModelBiped.class) rp.modelArmorChestplate = new PosedBiped(1.0F);
        if (rp.modelArmor.getClass() == ModelBiped.class) rp.modelArmor = new PosedBiped(0.5F);
        ReflectionHelper.setPrivateValue(RendererLivingEntity.class, rp, rp.modelBipedMain, "mainModel", "field_77045_g");
    }

    /** The pose of the item in the main hand, or null. */
    static ArmPose poseOf(EntityLivingBase e) {
        ItemStack stack = e.getHeldItem();
        if (stack == null) return null;
        IClientItemExtensions ext = ItemExtensions.of(stack.getItem());
        if (ext == IClientItemExtensions.DEFAULT) return null;
        try {
            ArmPose p = ext.getArmPose(e, InteractionHand.MAIN_HAND, stack);
            return p == null || p.transform == null ? null : p;
        } catch (RuntimeException ex) {
            return null;
        }
    }

    public static final class PosedBiped extends ModelBiped {

        private final HumanoidModel<?> view = new HumanoidModel<>();

        public PosedBiped(float inflate) {
            super(inflate);
        }

        @Override
        public void setRotationAngles(float limbSwing, float limbAmount, float age, float yaw, float pitch, float scale, Entity entity) {
            super.setRotationAngles(limbSwing, limbAmount, age, yaw, pitch, scale, entity);
            if (!(entity instanceof EntityLivingBase)) return;
            ArmPose pose = poseOf((EntityLivingBase) entity);
            if (pose == null) return;
            copy(bipedHead, view.head);
            copy(bipedRightArm, view.rightArm);
            copy(bipedLeftArm, view.leftArm);
            pose.transform.apply(view, (EntityLivingBase) entity, HumanoidArm.RIGHT);
            bipedRightArm.rotateAngleX = view.rightArm.xRot;
            bipedRightArm.rotateAngleY = view.rightArm.yRot;
            bipedRightArm.rotateAngleZ = view.rightArm.zRot;
        }

        private static void copy(ModelRenderer r, HumanoidModel.Part p) {
            p.xRot = r.rotateAngleX;
            p.yRot = r.rotateAngleY;
            p.zRot = r.rotateAngleZ;
        }
    }
}
