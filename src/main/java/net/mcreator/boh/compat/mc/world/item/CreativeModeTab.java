package net.mcreator.boh.compat.mc.world.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeModeTab {
    final Component title;
    final Supplier<ItemStack> icon;
    final CreativeModeTab.DisplayItemsGenerator generator;
    private CreativeTabs vanilla;

    CreativeModeTab(Component title, Supplier<ItemStack> icon, CreativeModeTab.DisplayItemsGenerator generator) {
        this.title = title;
        this.icon = icon;
        this.generator = generator;
    }

    public static Builder builder() {
        return new Builder();
    }

    public CreativeTabs vanilla() {
        return this.vanilla;
    }

    public List<ItemStack> displayItems() {
        List<ItemStack> out = new ArrayList<>();
        if (this.generator != null) {
            this.generator.accept(null, o -> {
                if (o instanceof ItemStack) {
                    out.add((ItemStack)o);
                } else if (o instanceof Item) {
                    out.add(new ItemStack((Item)o));
                } else if (o instanceof Block) {
                    Item it = Item.getItemFromBlock((Block)o);
                    if (it != null) {
                        out.add(new ItemStack(it));
                    }
                }
            });
        }

        return out;
    }

    public CreativeTabs toVanilla(final String label) {
        if (this.vanilla != null) {
            return this.vanilla;
        } else {
            this.vanilla = new CreativeTabs(label) {
                {
                    Objects.requireNonNull(CreativeModeTab.this);
                }

                public Item getTabIconItem() {
                    ItemStack s = this.getIconItemStack();
                    return s == null ? net.minecraft.init.Items.book : s.getItem();
                }

                public ItemStack getIconItemStack() {
                    ItemStack s = CreativeModeTab.this.icon == null ? null : CreativeModeTab.this.icon.get();
                    return s != null && s.getItem() != null ? s : new ItemStack(net.minecraft.init.Items.book);
                }

                public String getTranslatedTabLabel() {
                    return CreativeModeTab.this.title == null ? label : CreativeModeTab.this.title.getString();
                }

                public void displayAllReleventItems(List list) {
                    for (ItemStack s : CreativeModeTab.this.displayItems()) {
                        if (s != null && s.getItem() != null) {
                            list.add(s);
                        }
                    }
                }
            };
            return this.vanilla;
        }
    }

    @FunctionalInterface
    public interface DisplayItemsGenerator {
        void accept(Object var1, CreativeModeTab.Output var2);
    }

    @FunctionalInterface
    public interface Output {
        void accept(Object var1);
    }
}
