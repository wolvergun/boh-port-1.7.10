package net.mcreator.boh.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.Objects;
import java.util.function.Consumer;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.client.extensions.common.IClientItemExtensions;
import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.mc.client.model.ArmPose;
import net.mcreator.boh.compat.mc.client.renderer.BlockEntityWithoutLevelRenderer;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.InteractionResultHolder;
import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.mcreator.boh.compat.mc.world.entity.HumanoidArm;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Attributes;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Operation;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.geo.AnimatableInstanceCache;
import net.mcreator.boh.geo.AnimatableManager;
import net.mcreator.boh.geo.AnimationController;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.GeckoLibUtil;
import net.mcreator.boh.geo.GeoItem;
import net.mcreator.boh.geo.PlayState;
import net.mcreator.boh.geo.RawAnimation;
import net.mcreator.boh.item.renderer.BigDaddyDrillItemRenderer;
import net.mcreator.boh.procedures.BigDaddyDrillItemInHandTickProcedure;
import net.mcreator.boh.procedures.BigDaddyDrillRightclickedProcedure;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BigDaddyDrillItem extends BohItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public String animationprocedure = "empty";
    String prevAnim = "empty";

    public BigDaddyDrillItem() {
        super(new Properties().durability(250).rarity(Rarity.RARE));
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer;
            private static final ArmPose BigDaddyDrillPose = ArmPose.create("BigDaddyDrill", false, (model, entity, arm) -> {
                if (arm == HumanoidArm.LEFT) {
                    M.set_xRot(model.leftArm, -45.0F + model.head.xRot);
                } else {
                    M.set_xRot(model.rightArm, -45.0F + model.head.xRot);
                }
            });

            {
                Objects.requireNonNull(BigDaddyDrillItem.this);
                this.renderer = new BigDaddyDrillItemRenderer();
            }

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return this.renderer;
            }

            @Override
            public ArmPose getArmPose(EntityLivingBase entityLiving, InteractionHand hand, ItemStack itemStack) {
                return !M.isEmpty(itemStack) && M.getUsedItemHand(entityLiving) == hand ? BigDaddyDrillPose : ArmPose.EMPTY;
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
    public int getEnchantmentValue() {
        return 5;
    }

    @Override
    public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
        return 3.0F;
    }

    @Override
    public Multimap<IAttribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            Builder<IAttribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
            builder.put(Attributes.ATTACK_DAMAGE, M.new_AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 5.5, Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, M.new_AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -2.4, Operation.ADDITION));
            return builder.build();
        } else {
            return super.getDefaultAttributeModifiers(equipmentSlot);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(World world, EntityPlayer entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        ItemStack itemstack = M.getObject(ar);
        double x = M.getX(entity);
        double y = M.getY(entity);
        double z = M.getZ(entity);
        BigDaddyDrillRightclickedProcedure.execute(world, x, y, z, entity, itemstack);
        return ar;
    }

    @Override
    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (selected) {
            BigDaddyDrillItemInHandTickProcedure.execute(world, M.getX(entity), M.getY(entity), M.getZ(entity), entity, itemstack);
        }
    }
}
