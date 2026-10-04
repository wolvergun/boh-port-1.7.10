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

public class WhitefacesHeartTooltipProcedure {

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
            if (M.getItem(itemstack) == BohModItems.WHITEFACEHEART.get()) {
                tooltip.add(1, Component.literal("§7“Warm to the touch. Pulsing with something that is definitely not blood.”"));
                if (!GuiScreen.isShiftKeyDown()) {
                    tooltip.add(2, Component.literal("§i§o[Shift]"));
                } else if (GuiScreen.isShiftKeyDown()) {
                    tooltip.add(2, Component.literal("- Whiteface becomes enraged if you hold this."));
                    tooltip.add(3, Component.literal("- Can be used to permanently remove Whiteface."));
                    tooltip.add(4, Component.literal("- Obtained by giving Whiteface a Kindness Flower."));
                }
            }
        }
    }
}
