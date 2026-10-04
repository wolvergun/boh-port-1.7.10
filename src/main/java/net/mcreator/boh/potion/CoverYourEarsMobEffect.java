package net.mcreator.boh.potion;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import net.mcreator.boh.compat.effect.BohMobEffect;
import net.mcreator.boh.compat.forge.client.extensions.common.IClientMobEffectExtensions;
import net.mcreator.boh.compat.mc.client.gui.GuiGraphics;
import net.mcreator.boh.compat.mc.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.mcreator.boh.compat.mc.world.effect.MobEffectCategory;
import net.mcreator.boh.procedures.CoverYourEarsOnEffectActiveTickProcedure;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class CoverYourEarsMobEffect extends BohMobEffect {
    public CoverYourEarsMobEffect() {
        super(MobEffectCategory.HARMFUL, -1);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }

    @Override
    public void applyEffectTick(EntityLivingBase entity, int amplifier) {
        CoverYourEarsOnEffectActiveTickProcedure.execute(entity);
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
                    Objects.requireNonNull(CoverYourEarsMobEffect.this);
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
