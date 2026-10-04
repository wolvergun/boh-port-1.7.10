package net.mcreator.boh.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.client.extensions.common.IClientItemExtensions;
import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.mc.client.renderer.BlockEntityWithoutLevelRenderer;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Attributes;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Operation;
import net.mcreator.boh.compat.mc.world.item.ItemDisplayContext;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.mcreator.boh.compat.mc.world.item.TooltipFlag;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.geo.AnimatableInstanceCache;
import net.mcreator.boh.geo.AnimatableManager;
import net.mcreator.boh.geo.AnimationController;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.GeckoLibUtil;
import net.mcreator.boh.geo.GeoItem;
import net.mcreator.boh.geo.PlayState;
import net.mcreator.boh.geo.RawAnimation;
import net.mcreator.boh.item.renderer.TheGreatKnifeItemRenderer;
import net.mcreator.boh.procedures.TheGreatKnifeEntitySwingsItemProcedure;
import net.mcreator.boh.procedures.TheGreatKnifeItemInHandTickProcedure;
import net.mcreator.boh.procedures.TheGreatKnifeLivingEntityIsHitWithItemProcedure;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TheGreatKnifeItem extends BohItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public String animationprocedure = "empty";
    public static ItemDisplayContext transformType;

    public TheGreatKnifeItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.COMMON));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer;

            {
                Objects.requireNonNull(TheGreatKnifeItem.this);
                this.renderer = new TheGreatKnifeItemRenderer();
            }

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return this.renderer;
            }
        });
    }

    public void getTransformType(ItemDisplayContext type) {
        transformType = type;
    }

    private PlayState idlePredicate(AnimationState event) {
        if (transformType != null && this.animationprocedure.equals("empty")) {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("0"));
            return PlayState.CONTINUE;
        } else {
            return PlayState.STOP;
        }
    }

    private PlayState procedurePredicate(AnimationState event) {
        if (transformType != null && !this.animationprocedure.equals("empty") && event.getController().getAnimationState() == AnimationController.State.STOPPED
            )
         {
            event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
            if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                this.animationprocedure = "empty";
                event.getController().forceAnimationReset();
            }
        }

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
        return 22;
    }

    @Override
    public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
        return 0.3F;
    }

    @Override
    public Multimap<IAttribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            Builder<IAttribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
            builder.put(Attributes.ATTACK_DAMAGE, M.new_AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 11.0, Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, M.new_AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -3.6, Operation.ADDITION));
            return builder.build();
        } else {
            return super.getDefaultAttributeModifiers(equipmentSlot);
        }
    }

    @Override
    public void appendHoverText(ItemStack itemstack, World world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.literal(""));
        list.add(Component.literal(""));
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, EntityLivingBase entity, EntityLivingBase sourceentity) {
        boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
        TheGreatKnifeLivingEntityIsHitWithItemProcedure.execute(entity);
        return retval;
    }

    @Override
    public boolean onEntitySwing(ItemStack itemstack, EntityLivingBase entity) {
        boolean retval = super.onEntitySwing(itemstack, entity);
        TheGreatKnifeEntitySwingsItemProcedure.execute(entity, itemstack);
        return retval;
    }

    @Override
    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (selected) {
            TheGreatKnifeItemInHandTickProcedure.execute(entity);
        }
    }
}
