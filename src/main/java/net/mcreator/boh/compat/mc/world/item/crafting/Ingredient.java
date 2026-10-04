package net.mcreator.boh.compat.mc.world.item.crafting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Ingredient implements Predicate<ItemStack> {
    public static final Ingredient EMPTY = new Ingredient(new ArrayList<>());
    private final List<ItemStack> stacks;

    private Ingredient(List<ItemStack> stacks) {
        this.stacks = stacks;
    }

    public static Ingredient of(Object... things) {
        List<ItemStack> out = new ArrayList<>();

        for (Object o : things) {
            if (o instanceof Item) {
                out.add(new ItemStack((Item)o));
            } else if (o instanceof Block) {
                out.add(new ItemStack((Block)o));
            } else if (o instanceof ItemStack) {
                out.add((ItemStack)o);
            }
        }

        return new Ingredient(out);
    }

    public static Ingredient of(ItemStack... stacks) {
        return new Ingredient(new ArrayList<>(Arrays.asList(stacks)));
    }

    public static Ingredient of(Stream<ItemStack> stacks) {
        return new Ingredient(stacks.collect(Collectors.toList()));
    }

    public static Ingredient union(Ingredient... parts) {
        List<ItemStack> out = new ArrayList<>();

        for (Ingredient p : parts) {
            if (p != null) {
                out.addAll(p.stacks);
            }
        }

        return new Ingredient(out);
    }

    public ItemStack[] getItems() {
        return this.stacks.toArray(new ItemStack[0]);
    }

    public Item firstItem() {
        return this.stacks.isEmpty() ? null : this.stacks.get(0).getItem();
    }

    public boolean isEmpty() {
        return this.stacks.isEmpty();
    }

    public boolean test(ItemStack stack) {
        if (stack != null && stack.getItem() != null) {
            for (ItemStack s : this.stacks) {
                if (s.getItem() == stack.getItem() && (s.getItemDamage() == stack.getItemDamage() || s.getItemDamage() == 32767)) {
                    return true;
                }
            }

            return false;
        } else {
            return this.stacks.isEmpty();
        }
    }
}
