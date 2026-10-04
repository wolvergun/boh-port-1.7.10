package net.mcreator.boh.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.Objects;
import java.util.function.Consumer;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.forge.client.extensions.common.IClientItemExtensions;
import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.mc.client.renderer.BlockEntityWithoutLevelRenderer;
import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.mcreator.boh.compat.mc.world.entity.HumanoidArm;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Attributes;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Operation;
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
import net.mcreator.boh.item.renderer.GiantScissorItemRenderer;
import net.mcreator.boh.procedures.GiantScissorEntitySwingsItemProcedure;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.ItemStack;

public class GiantScissorItem extends BohItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public String animationprocedure = "empty";
    String prevAnim = "empty";

    public GiantScissorItem() {
        super(new Properties().durability(666).rarity(Rarity.RARE));
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

                {
                    Objects.requireNonNull(GiantScissorItem.this);
                    this.renderer = new GiantScissorItemRenderer();
                }

                @Override
                public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                    return this.renderer;
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
    public Multimap<IAttribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            Builder<IAttribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
            builder.put(Attributes.ATTACK_DAMAGE, M.new_AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", -2.0, Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, M.new_AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -2.4, Operation.ADDITION));
            return builder.build();
        } else {
            return super.getDefaultAttributeModifiers(equipmentSlot);
        }
    }

    @Override
    public boolean onEntitySwing(ItemStack itemstack, EntityLivingBase entity) {
        boolean retval = super.onEntitySwing(itemstack, entity);
        GiantScissorEntitySwingsItemProcedure.execute(M.level(entity), M.getX(entity), M.getY(entity), M.getZ(entity), entity, itemstack);
        return retval;
    }
}
