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
public class SoulStealerTooltipProcedure {
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
         if (itemstack.getItem() == BohModItems.SOUL_STEALER.get()) {
            tooltip.add(1, Component.literal("§7“Silent. Fragile. Hungry.”"));
            if (!Screen.hasShiftDown()) {
               tooltip.add(2, Component.literal("§i§o[Shift]"));
            } else if (Screen.hasShiftDown()) {
               tooltip.add(2, Component.literal("- Low damage."));
               tooltip.add(3, Component.literal("- Breaks very quickly."));
               tooltip.add(4, Component.literal("- Killing any entity drops a random Soul type."));
               tooltip.add(5, Component.literal("- Inspired by a certain backstabbing spy."));
            }
         }
      }
   }
}
