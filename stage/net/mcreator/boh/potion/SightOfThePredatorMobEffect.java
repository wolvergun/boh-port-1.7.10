package net.mcreator.boh.potion;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.mcreator.boh.procedures.SightOfThePredatorEffectExpiresProcedure;
import net.mcreator.boh.procedures.SightOfThePredatorOnEffectActiveTickProcedure;
import net.mcreator.boh.compat.mc.client.gui.GuiGraphics;
import net.mcreator.boh.compat.mc.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.mcreator.boh.compat.mc.world.effect.MobEffectCategory;
import net.minecraft.potion.PotionEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.forge.client.extensions.common.IClientMobEffectExtensions;
import net.mcreator.boh.compat.effect.BohMobEffect;

public class SightOfThePredatorMobEffect extends BohMobEffect {

    public SightOfThePredatorMobEffect() {
        super(MobEffectCategory.HARMFUL, -65536);
    }

    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }

    public void applyEffectTick(EntityLivingBase entity, int amplifier) {
        SightOfThePredatorOnEffectActiveTickProcedure.execute(entity);
    }

    public void removeAttributeModifiers(EntityLivingBase entity, BaseAttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);
        SightOfThePredatorEffectExpiresProcedure.execute(entity);
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    public void initializeClient(Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(new IClientMobEffectExtensions() {

            public boolean isVisibleInInventory(PotionEffect effect) {
                return false;
            }

            public boolean renderInventoryText(PotionEffect instance, EffectRenderingInventoryScreen<?> screen, GuiGraphics guiGraphics, int x, int y, int blitOffset) {
                return false;
            }

            public boolean isVisibleInGui(PotionEffect effect) {
                return false;
            }
        });
    }
}
