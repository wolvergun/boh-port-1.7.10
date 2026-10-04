package net.mcreator.boh.potion;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.mcreator.boh.compat.mc.client.gui.GuiGraphics;
import net.mcreator.boh.compat.mc.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.mcreator.boh.compat.mc.world.effect.MobEffectCategory;
import net.minecraft.potion.PotionEffect;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.forge.client.extensions.common.IClientMobEffectExtensions;
import net.mcreator.boh.compat.effect.BohMobEffect;

public class BlackHoleSunEffectMobEffect extends BohMobEffect {

    public BlackHoleSunEffectMobEffect() {
        super(MobEffectCategory.HARMFUL, -10998247);
    }

    public boolean isInstantenous() {
        return true;
    }

    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
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
