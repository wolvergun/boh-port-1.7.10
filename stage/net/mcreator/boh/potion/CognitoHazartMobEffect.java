package net.mcreator.boh.potion;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.mcreator.boh.procedures.CognitoHazartEffectExpiresProcedure;
import net.mcreator.boh.procedures.CognitoHazartEffectStartedappliedProcedure;
import net.mcreator.boh.procedures.CognitoHazartOnEffectActiveTickProcedure;
import net.mcreator.boh.compat.mc.client.gui.GuiGraphics;
import net.mcreator.boh.compat.mc.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.mcreator.boh.compat.mc.world.effect.MobEffectCategory;
import net.minecraft.potion.PotionEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.forge.client.extensions.common.IClientMobEffectExtensions;
import net.mcreator.boh.compat.effect.BohMobEffect;
import net.mcreator.boh.compat.M;

public class CognitoHazartMobEffect extends BohMobEffect {

    public CognitoHazartMobEffect() {
        super(MobEffectCategory.HARMFUL, -5169634);
    }

    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }

    public void addAttributeModifiers(EntityLivingBase entity, BaseAttributeMap attributeMap, int amplifier) {
        super.addAttributeModifiers(entity, attributeMap, amplifier);
        CognitoHazartEffectStartedappliedProcedure.execute();
    }

    public void applyEffectTick(EntityLivingBase entity, int amplifier) {
        CognitoHazartOnEffectActiveTickProcedure.execute(M.level(entity), M.getX(entity), M.getY(entity), M.getZ(entity), entity);
    }

    public void removeAttributeModifiers(EntityLivingBase entity, BaseAttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);
        CognitoHazartEffectExpiresProcedure.execute();
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
