package net.mcreator.boh.init;

import java.util.HashMap;
import net.mcreator.boh.client.gui.ComputerGUIScreen;
import net.mcreator.boh.client.gui.ForgivenesScreenScreen;
import net.mcreator.boh.client.gui.KillscreenWFScreen;
import net.mcreator.boh.client.gui.WitnessUIScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(bus = Bus.MOD, value = Dist.CLIENT)
public class BohModScreens {
   @SubscribeEvent
   public static void clientLoad(FMLClientSetupEvent event) {
      event.enqueueWork(() -> {
         MenuScreens.register((MenuType)BohModMenus.KILLSCREEN_WF.get(), KillscreenWFScreen::new);
         MenuScreens.register((MenuType)BohModMenus.FORGIVENES_SCREEN.get(), ForgivenesScreenScreen::new);
         MenuScreens.register((MenuType)BohModMenus.WITNESS_UI.get(), WitnessUIScreen::new);
         MenuScreens.register((MenuType)BohModMenus.COMPUTER_GUI.get(), ComputerGUIScreen::new);
      });
   }

   static void handleTextBoxMessage(BohModMenus.GuiSyncMessage message) {
      String editbox = message.editbox();
      String value = message.value();
      if (Minecraft.getInstance().screen instanceof BohModScreens.WidgetScreen sc) {
         HashMap<String, Object> widgets = sc.getWidgets();
         if (widgets.get("text:" + editbox) instanceof EditBox box) {
            box.setValue(value);
         }
      }
   }

   public interface WidgetScreen {
      HashMap<String, Object> getWidgets();
   }
}
