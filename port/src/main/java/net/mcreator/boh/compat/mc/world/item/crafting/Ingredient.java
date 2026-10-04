package net.mcreator.boh.compat.mc.world.item.crafting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/** 1.20 Ingredient: a set of acceptable items. */
public class Ingredient implements Predicate<ItemStack> {

    public static final Ingredient EMPTY = new Ingredient(new ArrayList<>());

    private final List<ItemStack> stacks;

    private Ingredient(List<ItemStack> stacks) {
        this.stacks = stacks;
    }

    public static Ingredient of(Object... things) {
        List<ItemStack> out = new ArrayList<>();
        for (Object o : things) {
            if (o instanceof Item) out.add(new ItemStack((Item) o));
            else if (o instanceof Block) out.add(new ItemStack((Block) o));
            else if (o instanceof ItemStack) out.add((ItemStack) o);
        }
        return new Ingredient(out);
    }

    public static Ingredient of(ItemStack... stacks) {
        return new Ingredient(new ArrayList<>(Arrays.asList(stacks)));
    }

    public static Ingredient of(java.util.stream.Stream<ItemStack> stacks) {
        return new Ingredient(stacks.collect(java.util.stream.Collectors.toList()));
    }

    public static Ingredient union(Ingredient... parts) {
        List<ItemStack> out = new ArrayList<>();
        for (Ingredient p : parts) if (p != null) out.addAll(p.stacks);
        return new Ingredient(out);
    }

    public ItemStack[] getItems() {
        return stacks.toArray(new ItemStack[0]);
    }

    public Item firstItem() {
        return stacks.isEmpty() ? null : stacks.get(0).getItem();
    }

    public boolean isEmpty() {
        return stacks.isEmpty();
    }

    @Override
    public boolean test(ItemStack stack) {
        if (stack == null || stack.getItem() == null) return stacks.isEmpty();
        for (ItemStack s : stacks)
            if (s.getItem() == stack.getItem() && (s.getItemDamage() == stack.getItemDamage() || s.getItemDamage() == 32767))
                return true;
        return false;
    }
}
