package net.mcreator.boh.compat.mc.world.item;

import net.mcreator.boh.compat.mc.world.food.FoodProperties;
import net.minecraft.item.Item;

/** 1.20 Item.Properties. */
public class Properties {

    public int maxStackSize = 64;
    public int maxDamage;
    public Rarity rarity = Rarity.COMMON;
    public FoodProperties food;
    public boolean fireResistant;
    public Item craftingRemainder;
    public boolean canRepair = true;

    public Properties stacksTo(int n) {
        maxStackSize = n;
        return this;
    }

    public Properties durability(int d) {
        maxDamage = d;
        maxStackSize = 1;
        return this;
    }

    public Properties defaultDurability(int d) {
        if (maxDamage == 0) durability(d);
        return this;
    }

    public Properties rarity(Rarity r) {
        rarity = r;
        return this;
    }

    public Properties food(FoodProperties f) {
        food = f;
        return this;
    }

    public Properties fireResistant() {
        fireResistant = true;
        return this;
    }

    public Properties craftRemainder(Item item) {
        craftingRemainder = item;
        return this;
    }

    public Properties setNoRepair() {
        canRepair = false;
        return this;
    }
}
