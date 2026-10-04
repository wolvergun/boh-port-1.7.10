package net.mcreator.boh.procedures;

import java.util.List;
import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class HemotorrentTooltipProcedure {
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
         if (itemstack.getItem() == BohModItems.HEMOTORRENT.get()) {
            tooltip.add(1, Component.literal("§7“The tide of blood answers his call.”"));
            if (!Screen.hasShiftDown()) {
               tooltip.add(2, Component.literal("§i§o[Shift]"));
            } else if (Screen.hasShiftDown()) {
               tooltip.add(2, Component.literal("§e- Right-click: §fSends a blood wave forward."));
               tooltip.add(3, Component.literal("- The wave damages enemies it passes through."));
               tooltip.add(4, Component.literal("- Dropped by The Boiled One."));
            }
         }
      }
   }
}
