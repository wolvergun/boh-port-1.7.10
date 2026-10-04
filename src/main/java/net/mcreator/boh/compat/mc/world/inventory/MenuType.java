package net.mcreator.boh.compat.mc.world.inventory;

import net.mcreator.boh.compat.mc.network.FriendlyByteBuf;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class MenuType<T> {
    private final MenuType.MenuFactory<T> factory;
    private ResourceLocation id;
    private int guiId = -1;

    public MenuType(MenuType.MenuFactory<T> factory) {
        this.factory = factory;
    }

    public T create(int windowId, InventoryPlayer inv, FriendlyByteBuf extraData) {
        return this.factory.create(windowId, inv, extraData);
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public int guiId() {
        return this.guiId;
    }

    public void bind(ResourceLocation id, int guiId) {
        this.id = id;
        this.guiId = guiId;
    }

    @FunctionalInterface
    public interface MenuFactory<T> {
        T create(int var1, InventoryPlayer var2, FriendlyByteBuf var3);
    }
}
