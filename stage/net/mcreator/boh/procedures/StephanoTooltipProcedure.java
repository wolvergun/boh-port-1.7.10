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

public class StephanoTooltipProcedure {

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
            if (M.getItem(itemstack) == BohModItems.STEPHANO.get()) {
                tooltip.add(1, Component.literal("§7“A hero never dies. But you might.”"));
                if (!GuiScreen.isShiftKeyDown()) {
                    tooltip.add(2, Component.literal("§i§o[Shift]"));
                } else if (GuiScreen.isShiftKeyDown()) {
                    tooltip.add(2, Component.literal("- Killing an enemy has a chance to drop a Gold Nugget."));
                    tooltip.add(3, Component.literal("- IT SPEAKS."));
                    tooltip.add(4, Component.literal("- PewDiePie reference."));
                }
            }
        }
    }
}
