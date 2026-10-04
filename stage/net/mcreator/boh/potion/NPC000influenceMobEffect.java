package net.mcreator.boh.potion;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.mcreator.boh.procedures.NPC000influenceOnEffectActiveTickProcedure;
import net.mcreator.boh.compat.mc.client.gui.GuiGraphics;
import net.mcreator.boh.compat.mc.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.mcreator.boh.compat.mc.world.effect.MobEffectCategory;
import net.minecraft.potion.PotionEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.forge.client.extensions.common.IClientMobEffectExtensions;
import net.mcreator.boh.compat.effect.BohMobEffect;

public class NPC000influenceMobEffect extends BohMobEffect {

    public NPC000influenceMobEffect() {
        super(MobEffectCategory.HARMFUL, -1);
    }

    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }

    public void applyEffectTick(EntityLivingBase entity, int amplifier) {
        NPC000influenceOnEffectActiveTickProcedure.execute(entity);
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
