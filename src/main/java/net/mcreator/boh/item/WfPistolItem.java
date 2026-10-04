package net.mcreator.boh.item;

import java.util.Objects;
import java.util.function.Consumer;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.forge.client.extensions.common.IClientItemExtensions;
import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.mc.client.model.ArmPose;
import net.mcreator.boh.compat.mc.client.renderer.BlockEntityWithoutLevelRenderer;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.InteractionResultHolder;
import net.mcreator.boh.compat.mc.world.entity.HumanoidArm;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.mcreator.boh.geo.AnimatableInstanceCache;
import net.mcreator.boh.geo.AnimatableManager;
import net.mcreator.boh.geo.AnimationController;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.GeckoLibUtil;
import net.mcreator.boh.geo.GeoItem;
import net.mcreator.boh.geo.PlayState;
import net.mcreator.boh.geo.RawAnimation;
import net.mcreator.boh.item.renderer.WfPistolItemRenderer;
import net.mcreator.boh.procedures.WfPistolRightclickedProcedure;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class WfPistolItem extends BohItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public String animationprocedure = "empty";
    String prevAnim = "empty";

    public WfPistolItem() {
        super(new Properties().durability(1000).rarity(Rarity.EPIC));
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(
            new IClientItemExtensions() {
                private final BlockEntityWithoutLevelRenderer renderer;
                private static final ArmPose WfPistolPose = ArmPose.create("WfPistol", false, (model, entity, arm) -> {
                    if (arm == HumanoidArm.LEFT) {
                        M.set_xRot(model.leftArm, -45.0F + model.head.xRot);
                    } else {
                        M.set_xRot(model.rightArm, -45.0F + model.head.xRot);
                    }
                });

                {
                    Objects.requireNonNull(WfPistolItem.this);
                    this.renderer = new WfPistolItemRenderer();
                }

                @Override
                public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                    return this.renderer;
                }

                @Override
                public ArmPose getArmPose(EntityLivingBase entityLiving, InteractionHand hand, ItemStack itemStack) {
                    return !M.isEmpty(itemStack) && M.getUsedItemHand(entityLiving) == hand ? WfPistolPose : ArmPose.EMPTY;
                }

                public boolean applyForgeHandTransform(
                    PoseStack poseStack,
                    EntityClientPlayerMP player,
                    HumanoidArm arm,
                    ItemStack itemInHand,
                    float partialTick,
                    float equipProcess,
                    float swingProcess
                ) {
                    int i = arm == HumanoidArm.RIGHT ? 1 : -1;
                    poseStack.translate(i * 0.56F, -0.52F, -0.72F);
                    if (M.getUseItem(player) == itemInHand) {
                        poseStack.translate(0.05, 0.05, 0.05);
                    }

                    return true;
                }
            }
        );
    }

    private PlayState idlePredicate(AnimationState event) {
        if (this.animationprocedure.equals("empty")) {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("idle"));
            return PlayState.CONTINUE;
        } else {
            return PlayState.STOP;
        }
    }

    private PlayState procedurePredicate(AnimationState event) {
        if (!this.animationprocedure.equals("empty") && event.getController().getAnimationState() == AnimationController.State.STOPPED
            || !this.animationprocedure.equals(this.prevAnim) && !this.animationprocedure.equals("empty")) {
            if (!this.animationprocedure.equals(this.prevAnim)) {
                event.getController().forceAnimationReset();
            }

            event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
            if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                this.animationprocedure = "empty";
                event.getController().forceAnimationReset();
            }
        } else if (this.animationprocedure.equals("empty")) {
            this.prevAnim = "empty";
            return PlayState.STOP;
        }

        this.prevAnim = this.animationprocedure;
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        AnimationController procedureController = new AnimationController<>(this, "procedureController", 0, this::procedurePredicate);
        data.add(procedureController);
        AnimationController idleController = new AnimationController<>(this, "idleController", 0, this::idlePredicate);
        data.add(idleController);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(World world, EntityPlayer entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        ItemStack itemstack = M.getObject(ar);
        double x = M.getX(entity);
        double y = M.getY(entity);
        double z = M.getZ(entity);
        WfPistolRightclickedProcedure.execute(world, x, y, z, entity, itemstack);
        return ar;
    }
}
