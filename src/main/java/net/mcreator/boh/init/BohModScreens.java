package net.mcreator.boh.init;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.HashMap;
import net.mcreator.boh.client.gui.ComputerGUIScreen;
import net.mcreator.boh.client.gui.ForgivenesScreenScreen;
import net.mcreator.boh.client.gui.KillscreenWFScreen;
import net.mcreator.boh.client.gui.WitnessUIScreen;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.fml.event.lifecycle.FMLClientSetupEvent;
import net.mcreator.boh.compat.mc.client.gui.components.EditBox;
import net.mcreator.boh.compat.mc.client.gui.screens.MenuScreens;
import net.minecraft.client.Minecraft;

public class BohModScreens {
    @SubscribeEvent
    public void clientLoad(FMLClientSetupEvent event) {
        M.enqueueWork(event, () -> {
            MenuScreens.register(BohModMenus.KILLSCREEN_WF.get(), KillscreenWFScreen::new);
            MenuScreens.register(BohModMenus.FORGIVENES_SCREEN.get(), ForgivenesScreenScreen::new);
            MenuScreens.register(BohModMenus.WITNESS_UI.get(), WitnessUIScreen::new);
            MenuScreens.register(BohModMenus.COMPUTER_GUI.get(), ComputerGUIScreen::new);
        });
    }

    static void handleTextBoxMessage(BohModMenus.GuiSyncMessage message) {
        String editbox = message.editbox();
        String value = message.value();
        if (M.screen(Minecraft.getMinecraft()) instanceof BohModScreens.WidgetScreen sc) {
            HashMap<String, Object> widgets = sc.getWidgets();
            if (widgets.get("text:" + editbox) instanceof EditBox box) {
                M.setValue(box, value);
            }
        }
    }

    public interface WidgetScreen {
        HashMap<String, Object> getWidgets();
    }
}
