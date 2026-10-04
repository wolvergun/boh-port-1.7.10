package net.mcreator.boh.compat.mc.world.item.enchantment;

/** 1.20 Enchantment.Rarity with its weight. */
public enum Rarity {

    COMMON(10),
    UNCOMMON(5),
    RARE(2),
    VERY_RARE(1);

    public final int weight;

    Rarity(int weight) {
        this.weight = weight;
    }
}
