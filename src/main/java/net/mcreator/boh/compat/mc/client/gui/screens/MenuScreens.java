package net.mcreator.boh.compat.mc.client.gui.screens;

import java.util.HashMap;
import java.util.Map;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.world.inventory.MenuType;
import net.minecraft.entity.player.InventoryPlayer;

public final class MenuScreens {
    public static final Map<MenuType<?>, MenuScreens.ScreenConstructor<?, ?>> SCREENS = new HashMap<>();

    private MenuScreens() {
    }

    public static <M, S> void register(MenuType<? extends M> type, MenuScreens.ScreenConstructor<M, S> ctor) {
        SCREENS.put(type, ctor);
    }

    @FunctionalInterface
    public interface ScreenConstructor<M, S> {
        S create(M var1, InventoryPlayer var2, Component var3);
    }
}
