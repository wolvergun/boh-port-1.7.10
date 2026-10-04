package net.mcreator.boh.potion;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.effect.BohMobEffect;
import net.mcreator.boh.compat.forge.client.extensions.common.IClientMobEffectExtensions;
import net.mcreator.boh.compat.mc.client.gui.GuiGraphics;
import net.mcreator.boh.compat.mc.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.mcreator.boh.compat.mc.world.effect.MobEffectCategory;
import net.mcreator.boh.procedures.CognitoHazartEffectExpiresProcedure;
import net.mcreator.boh.procedures.CognitoHazartEffectStartedappliedProcedure;
import net.mcreator.boh.procedures.CognitoHazartOnEffectActiveTickProcedure;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class CognitoHazartMobEffect extends BohMobEffect {
    public CognitoHazartMobEffect() {
        super(MobEffectCategory.HARMFUL, -5169634);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }

    @Override
    public void addAttributeModifiers(EntityLivingBase entity, BaseAttributeMap attributeMap, int amplifier) {
        super.addAttributeModifiers(entity, attributeMap, amplifier);
        CognitoHazartEffectStartedappliedProcedure.execute();
    }

    @Override
    public void applyEffectTick(EntityLivingBase entity, int amplifier) {
        CognitoHazartOnEffectActiveTickProcedure.execute(M.level(entity), M.getX(entity), M.getY(entity), M.getZ(entity), entity);
    }

    @Override
    public void removeAttributeModifiers(EntityLivingBase entity, BaseAttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);
        CognitoHazartEffectExpiresProcedure.execute();
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void initializeClient(Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(
            new IClientMobEffectExtensions() {
                {
                    Objects.requireNonNull(CognitoHazartMobEffect.this);
                }

                @Override
                public boolean isVisibleInInventory(PotionEffect effect) {
                    return false;
                }

                @Override
                public boolean renderInventoryText(
                    PotionEffect instance, EffectRenderingInventoryScreen<?> screen, GuiGraphics guiGraphics, int x, int y, int blitOffset
                ) {
                    return false;
                }

                @Override
                public boolean isVisibleInGui(PotionEffect effect) {
                    return false;
                }
            }
        );
    }
}
