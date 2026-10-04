package net.mcreator.boh.procedures;

import java.util.List;
import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.client.gui.GuiScreen;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class LifeformEffigyTooltipProcedure {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        execute(event, M.getItemStack(event), M.getToolTip(event));
    }

    public static void execute(ItemStack itemstack, List<Component> tooltip) {
        execute(null, itemstack, tooltip);
    }

    private static void execute(@Nullable Event event, ItemStack itemstack, List<Component> tooltip) {
        if (tooltip != null) {
            if (M.getItem(itemstack) == BohModItems.LIFEFORM_EFFIGY.get()) {
                tooltip.add(1, Component.literal("§7“A crude copy of you. Good enough for emergencies.”"));
                if (!GuiScreen.isShiftKeyDown()) {
                    tooltip.add(2, Component.literal("§i§o[Shift]"));
                } else if (GuiScreen.isShiftKeyDown()) {
                    tooltip.add(2, Component.literal("- Works like a Totem of Undying, but…"));
                    tooltip.add(3, Component.literal("- Instead of reviving you in place, it teleports you to your spawn point."));
                    tooltip.add(3, Component.literal("- Dropped by Lifeform Fellow."));
                }
            }
        }
    }
}
