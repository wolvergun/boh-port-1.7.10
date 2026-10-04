package net.mcreator.boh.procedures;

import java.util.List;
import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.client.gui.GuiScreen;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class RiftStabilizerTooltipProcedure {

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
            if (M.getItem(itemstack) == M.asItem(((Block) BohModBlocks.RIFT_STABILIZER.get()))) {
                tooltip.add(1, Component.literal("§7“Without anchors, reality collapses inward.”"));
                if (!GuiScreen.isShiftKeyDown()) {
                    tooltip.add(2, Component.literal("§i§o[Shift]"));
                } else if (GuiScreen.isShiftKeyDown()) {
                    tooltip.add(2, Component.literal("- Rift Stabilizers are required to contain and focus dimensional breaches."));
                    tooltip.add(3, Component.literal("- They must be placed in a specific formation to function."));
                    tooltip.add(4, Component.literal("- Once placed, a Spawn Trinket can be used at the center to summon the entity safely."));
                }
            }
        }
    }
}
