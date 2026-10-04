package net.mcreator.boh.compat.mc.client.gui.screens;

import java.util.HashMap;
import java.util.Map;

import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.world.inventory.MenuType;
import net.minecraft.entity.player.InventoryPlayer;

/** 1.20 MenuScreens: menu type -> screen constructor. */
public final class MenuScreens {

    @FunctionalInterface
    public interface ScreenConstructor<M, S> {

        S create(M menu, InventoryPlayer inv, Component title);
    }

    public static final Map<MenuType<?>, ScreenConstructor<?, ?>> SCREENS = new HashMap<>();

    private MenuScreens() {}

    public static <M, S> void register(MenuType<? extends M> type, ScreenConstructor<M, S> ctor) {
        SCREENS.put(type, ctor);
    }
}
