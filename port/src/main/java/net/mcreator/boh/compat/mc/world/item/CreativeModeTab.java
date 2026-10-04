package net.mcreator.boh.compat.mc.world.item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/** 1.20 CreativeModeTab description; {@link #toVanilla(String)} creates the 1.7.10 CreativeTabs. */
public class CreativeModeTab {

    @FunctionalInterface
    public interface Output {

        void accept(Object itemOrStack);
    }

    @FunctionalInterface
    public interface DisplayItemsGenerator {

        void accept(Object parameters, Output output);
    }

    final Component title;
    final Supplier<ItemStack> icon;
    final DisplayItemsGenerator generator;
    private CreativeTabs vanilla;

    CreativeModeTab(Component title, Supplier<ItemStack> icon, DisplayItemsGenerator generator) {
        this.title = title;
        this.icon = icon;
        this.generator = generator;
    }

    public static Builder builder() {
        return new Builder();
    }

    public CreativeTabs vanilla() {
        return vanilla;
    }

    public List<ItemStack> displayItems() {
        List<ItemStack> out = new ArrayList<>();
        if (generator != null) generator.accept(null, o -> {
            if (o instanceof ItemStack) out.add((ItemStack) o);
            else if (o instanceof Item) out.add(new ItemStack((Item) o));
            else if (o instanceof net.minecraft.block.Block) {
                Item it = Item.getItemFromBlock((net.minecraft.block.Block) o);
                if (it != null) out.add(new ItemStack(it));
            }
        });
        return out;
    }

    public CreativeTabs toVanilla(String label) {
        if (vanilla != null) return vanilla;
        vanilla = new CreativeTabs(label) {

            @Override
            public Item getTabIconItem() {
                ItemStack s = getIconItemStack();
                return s == null ? Items.book : s.getItem();
            }

            @Override
            public ItemStack getIconItemStack() {
                ItemStack s = icon == null ? null : icon.get();
                return s == null || s.getItem() == null ? new ItemStack(Items.book) : s;
            }

            @Override
            public String getTranslatedTabLabel() {
                return title == null ? label : title.getString();
            }

            @Override
            @SuppressWarnings({ "rawtypes", "unchecked" })
            public void displayAllReleventItems(List list) {
                for (ItemStack s : displayItems()) if (s != null && s.getItem() != null) list.add(s);
            }
        };
        return vanilla;
    }
}
