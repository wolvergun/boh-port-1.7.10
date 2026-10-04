package net.mcreator.boh.compat.mc.world.item;

import net.mcreator.boh.compat.mc.world.food.FoodProperties;
import net.minecraft.item.Item;

public class Properties {
    public int maxStackSize = 64;
    public int maxDamage;
    public Rarity rarity = Rarity.COMMON;
    public FoodProperties food;
    public boolean fireResistant;
    public Item craftingRemainder;
    public boolean canRepair = true;

    public Properties stacksTo(int n) {
        this.maxStackSize = n;
        return this;
    }

    public Properties durability(int d) {
        this.maxDamage = d;
        this.maxStackSize = 1;
        return this;
    }

    public Properties defaultDurability(int d) {
        if (this.maxDamage == 0) {
            this.durability(d);
        }

        return this;
    }

    public Properties rarity(Rarity r) {
        this.rarity = r;
        return this;
    }

    public Properties food(FoodProperties f) {
        this.food = f;
        return this;
    }

    public Properties fireResistant() {
        this.fireResistant = true;
        return this;
    }

    public Properties craftRemainder(Item item) {
        this.craftingRemainder = item;
        return this;
    }

    public Properties setNoRepair() {
        this.canRepair = false;
        return this;
    }
}
