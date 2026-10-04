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
public class VhsTapeTooltipProcedure {
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
         if (itemstack.getItem() == BohModItems.VHS_TAPE.get()) {
            tooltip.add(1, Component.literal("§7“The tape inside moves on its own.”"));
            if (!Screen.hasShiftDown()) {
               tooltip.add(2, Component.literal("§i§o[Shift]"));
            } else if (Screen.hasShiftDown()) {
               tooltip.add(2, Component.literal("- Used to summon Sadako."));
               tooltip.add(3, Component.literal("- Must be inserted into an Analog TV."));
               tooltip.add(4, Component.literal("- In case of ghosts, throw tape back in the well."));
            }
         }
      }
   }
}
