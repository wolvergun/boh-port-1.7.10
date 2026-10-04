package net.mcreator.boh.procedures;

import java.util.List;
import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class GateComputerTooltipProcedure {
   @OnlyIn(Dist.CLIENT)
   @SubscribeEvent
   public static void onItemTooltip(ItemTooltipEvent event) {
      execute(event, event.getItemStack(), event.getToolTip());
   }

   public static void execute(ItemStack itemstack, List<Component> tooltip) {
      execute(null, itemstack, tooltip);
   }

   private static void execute(@Nullable Event event, ItemStack itemstack, List<Component> tooltip) {
      if (tooltip != null) {
         if (itemstack.getItem() == ((Block)BohModBlocks.COMPUTER.get()).asItem()) {
            tooltip.add(
               1, Component.literal("§7“A forbidden terminal designed to breach realities. The GATE Computer does not create life — it downloads it.”")
            );
            if (!Screen.hasShiftDown()) {
               tooltip.add(2, Component.literal("§i§o[Shift]"));
            } else if (Screen.hasShiftDown()) {
               tooltip.add(2, Component.literal("- The only machine capable of printing Spawn Trinkets."));
               tooltip.add(3, Component.literal("- Insert Black Dye and Haunted Paper to begin the download."));
               tooltip.add(4, Component.literal("- Each completed download produces a Trinket tied to another reality."));
               tooltip.add(5, Component.literal("- Named “GATE” for its role in opening pathways to other worlds."));
            }
         }
      }
   }
}
