package net.mcreator.boh.compat.mc.world.inventory;

import net.minecraft.util.ResourceLocation;

/** 1.20 MenuType: a container factory; opened through the mod's IGuiHandler in 1.7.10. */
public class MenuType<T> {

    @FunctionalInterface
    public interface MenuFactory<T> {

        T create(int id, net.minecraft.entity.player.InventoryPlayer inv,
            net.mcreator.boh.compat.mc.network.FriendlyByteBuf extraData);
    }

    private final MenuFactory<T> factory;
    private ResourceLocation id;
    private int guiId = -1;

    public MenuType(MenuFactory<T> factory) {
        this.factory = factory;
    }

    public T create(int windowId, net.minecraft.entity.player.InventoryPlayer inv,
        net.mcreator.boh.compat.mc.network.FriendlyByteBuf extraData) {
        return factory.create(windowId, inv, extraData);
    }

    public ResourceLocation getId() {
        return id;
    }

    public int guiId() {
        return guiId;
    }

    public void bind(ResourceLocation id, int guiId) {
        this.id = id;
        this.guiId = guiId;
    }
}
