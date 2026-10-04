package net.mcreator.boh.compat.mc.world.item;

import java.util.function.Supplier;

import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.item.ItemStack;

/** 1.20 CreativeModeTab.Builder. */
public class Builder {

    private Component title;
    private Supplier<ItemStack> icon;
    private CreativeModeTab.DisplayItemsGenerator generator;

    public Builder title(Component title) {
        this.title = title;
        return this;
    }

    public Builder icon(Supplier<ItemStack> icon) {
        this.icon = icon;
        return this;
    }

    public Builder displayItems(CreativeModeTab.DisplayItemsGenerator generator) {
        this.generator = generator;
        return this;
    }

    public Builder withSearchBar() {
        return this;
    }

    public Builder withSearchBar(int width) {
        return this;
    }

    public Builder withTabsBefore(Object... tabs) {
        return this;
    }

    public Builder withTabsAfter(Object... tabs) {
        return this;
    }

    public CreativeModeTab build() {
        return new CreativeModeTab(title, icon, generator);
    }
}
