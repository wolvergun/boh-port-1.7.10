package net.mcreator.boh.compat.client;

import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.client.gui.screens.MenuScreens;
import net.mcreator.boh.compat.mc.network.FriendlyByteBuf;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.world.inventory.AbstractContainerMenu;
import net.mcreator.boh.compat.mc.world.inventory.MenuType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public final class MenuScreensImpl {
    private MenuScreensImpl() {
    }

    public static void open(String typeId, int windowId, String title, FriendlyByteBuf data) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer != null && !typeId.isEmpty()) {
            MenuType<?> type = ForgeRegistries.MENU_TYPES.getValue(new ResourceLocation(typeId));
            if (type != null) {
                if (type.create(windowId, mc.thePlayer.inventory, data) instanceof AbstractContainerMenu m) {
                    m.windowId = windowId;
                    m.containerId = windowId;
                    MenuScreens.ScreenConstructor ctor = MenuScreens.SCREENS.get(type);
                    if (ctor != null) {
                        Object screen = ctor.create(m, mc.thePlayer.inventory, Component.literal(title));
                        if (screen instanceof GuiScreen) {
                            mc.displayGuiScreen((GuiScreen)screen);
                            mc.thePlayer.openContainer = m;
                        }
                    }
                }
            }
        }
    }
}
