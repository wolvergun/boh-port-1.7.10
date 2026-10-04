package net.mcreator.boh.item;

import java.util.List;
import java.util.function.Consumer;
import net.mcreator.boh.client.renderer.GojiHeadArmorRenderer;
import net.mcreator.boh.compat.mc.client.model.HumanoidModel;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.minecraft.entity.EntityLivingBase;
import net.mcreator.boh.compat.mc.world.item.ArmorItem;
import net.mcreator.boh.compat.mc.world.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.TooltipFlag;
import net.mcreator.boh.compat.mc.world.item.Type;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.client.extensions.common.IClientItemExtensions;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.geo.GeoItem;
import net.mcreator.boh.geo.DataTickets;
import net.mcreator.boh.geo.AnimatableInstanceCache;
import net.mcreator.boh.geo.AnimationController;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.RawAnimation;
import net.mcreator.boh.geo.AnimatableManager.ControllerRegistrar;
import net.mcreator.boh.geo.AnimationController.State;
import net.mcreator.boh.geo.PlayState;
import net.mcreator.boh.geo.GeoArmorRenderer;
import net.mcreator.boh.geo.GeckoLibUtil;
import net.mcreator.boh.compat.M;

public class GojiHeadItem extends ArmorItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public String animationprocedure = "empty";

    String prevAnim = "empty";

    public GojiHeadItem(Type type, Properties properties) {
        super(new ArmorMaterial() {

            public int getDurabilityForType(Type type) {
                return new int[] { 13, 15, 16, 11 }[M.getIndex(M.getSlot(type))] * 0;
            }

            public int getDefenseForType(Type type) {
                return new int[] { 0, 0, 0, 4 }[M.getIndex(M.getSlot(type))];
            }

            public int getEnchantmentValue() {
                return 9;
            }

            public SoundEvent getEquipSound() {
                return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_leather"));
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }

            public String getName() {
                return "goji_head";
            }

            public float getToughness() {
                return 0.0F;
            }

            public float getKnockbackResistance() {
                return 0.0F;
            }
        }, type, properties);
    }

    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {

            private GeoArmorRenderer<?> renderer;

            public HumanoidModel<?> getHumanoidArmorModel(EntityLivingBase livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null) {
                    this.renderer = new GojiHeadArmorRenderer();
                }
                M.prepForRender(this.renderer, livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }

    public void appendHoverText(ItemStack itemstack, World world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
    }

    private PlayState predicate(AnimationState event) {
        if (this.animationprocedure.equals("empty")) {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("idle"));
            Entity entity = (Entity) event.getData(DataTickets.ENTITY);
            return entity instanceof Entity ? PlayState.CONTINUE : PlayState.CONTINUE;
        } else {
            return PlayState.STOP;
        }
    }

    private PlayState procedurePredicate(AnimationState event) {
        if ((this.animationprocedure.equals("empty") || event.getController().getAnimationState() != State.STOPPED) && (this.animationprocedure.equals(this.prevAnim) || this.animationprocedure.equals("empty"))) {
            if (this.animationprocedure.equals("empty")) {
                this.prevAnim = "empty";
                return PlayState.STOP;
            } else {
                this.prevAnim = this.animationprocedure;
                return PlayState.CONTINUE;
            }
        } else {
            if (!this.animationprocedure.equals(this.prevAnim)) {
                event.getController().forceAnimationReset();
            }
            event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
            if (event.getController().getAnimationState() == State.STOPPED) {
                this.animationprocedure = "empty";
                event.getController().forceAnimationReset();
            }
            Entity entity = (Entity) event.getData(DataTickets.ENTITY);
            return entity instanceof Entity ? PlayState.CONTINUE : PlayState.CONTINUE;
        }
    }

    public void registerControllers(ControllerRegistrar data) {
        data.add(new AnimationController[] { new AnimationController(this, "controller", 5, this::predicate) });
        data.add(new AnimationController[] { new AnimationController(this, "procedureController", 5, this::procedurePredicate) });
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
