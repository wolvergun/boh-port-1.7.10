package net.mcreator.boh.compat.forge.common.extensions;

import net.mcreator.boh.compat.mc.world.inventory.MenuType;

public final class IForgeMenuType {

    private IForgeMenuType() {}

    public static <T> MenuType<T> create(MenuType.MenuFactory<T> factory) {
        return new MenuType<>(factory);
    }
}
