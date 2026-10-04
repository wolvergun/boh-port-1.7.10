package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class TooltipSirenphoneProcedure {
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        execute(event, M.getItemStack(event), M.getToolTip(event));
    }

    public static void execute(ItemStack itemstack, List<Component> tooltip) {
        execute(null, itemstack, tooltip);
    }

    private static void execute(@Nullable Event event, ItemStack itemstack, List<Component> tooltip) {
        if (tooltip != null && M.getItem(itemstack) == BohModItems.SIRENPHONE.get()) {
            tooltip.add(1, Component.literal("§7“Sirenhead’s least lethal weapon.”"));
            if (!GuiScreen.isShiftKeyDown()) {
                tooltip.add(2, Component.literal("§i§o[Shift]"));
            } else if (GuiScreen.isShiftKeyDown()) {
                tooltip.add(2, Component.literal("- Dropped by Sirenhead."));
                tooltip.add(3, Component.literal("- Plays meme sounds on right-click."));
                tooltip.add(4, Component.literal("- Zero actual combat value… probably."));
            }
        }
    }
}
