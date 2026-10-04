package net.mcreator.boh.item;

import java.util.function.Consumer;
import net.mcreator.boh.item.renderer.GunWithOneBulletItemRenderer;
import net.mcreator.boh.procedures.GunWithOneBulletRightclickedProcedure;
import net.mcreator.boh.compat.mc.client.model.ArmPose;
import net.mcreator.boh.compat.mc.client.renderer.BlockEntityWithoutLevelRenderer;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.InteractionResultHolder;
import net.mcreator.boh.compat.mc.world.entity.HumanoidArm;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.client.extensions.common.IClientItemExtensions;
import net.mcreator.boh.geo.GeoItem;
import net.mcreator.boh.geo.AnimatableInstanceCache;
import net.mcreator.boh.geo.AnimationController;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.RawAnimation;
import net.mcreator.boh.geo.AnimatableManager.ControllerRegistrar;
import net.mcreator.boh.geo.AnimationController.State;
import net.mcreator.boh.geo.PlayState;
import net.mcreator.boh.geo.GeckoLibUtil;
import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.M;

public class GunWithOneBulletItem extends BohItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public String animationprocedure = "empty";

    String prevAnim = "empty";

    public GunWithOneBulletItem() {
        super(new Properties().durability(1).rarity(Rarity.EPIC));
    }

    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {

            private final BlockEntityWithoutLevelRenderer renderer = new GunWithOneBulletItemRenderer();

            private static final ArmPose GunWithOneBulletPose = ArmPose.create("GunWithOneBullet", false, (model, entity, arm) -> {
                if (arm == HumanoidArm.LEFT) {
                    M.set_xRot(model.leftArm, -45.0F + model.head.xRot);
                } else {
                    M.set_xRot(model.rightArm, -45.0F + model.head.xRot);
                }
            });

            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return this.renderer;
            }

            public ArmPose getArmPose(EntityLivingBase entityLiving, InteractionHand hand, ItemStack itemStack) {
                return !M.isEmpty(itemStack) && M.getUsedItemHand(entityLiving) == hand ? GunWithOneBulletPose : ArmPose.EMPTY;
            }
        });
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
        if (!this.animationprocedure.equals("empty") && event.getController().getAnimationState() == State.STOPPED || !this.animationprocedure.equals(this.prevAnim) && !this.animationprocedure.equals("empty")) {
            if (!this.animationprocedure.equals(this.prevAnim)) {
                event.getController().forceAnimationReset();
            }
            event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
            if (event.getController().getAnimationState() == State.STOPPED) {
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

    public void registerControllers(ControllerRegistrar data) {
        AnimationController procedureController = new AnimationController(this, "procedureController", 0, this::procedurePredicate);
        data.add(new AnimationController[] { procedureController });
        AnimationController idleController = new AnimationController(this, "idleController", 0, this::idlePredicate);
        data.add(new AnimationController[] { idleController });
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public InteractionResultHolder<ItemStack> use(World world, EntityPlayer entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        ItemStack itemstack = (ItemStack) M.getObject(ar);
        double x = M.getX(entity);
        double y = M.getY(entity);
        double z = M.getZ(entity);
        GunWithOneBulletRightclickedProcedure.execute(world, x, y, z, entity, itemstack);
        return ar;
    }
}
