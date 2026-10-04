package net.mcreator.boh.compat.mc.world.item.enchantment;

public enum Rarity {
    COMMON(10),
    UNCOMMON(5),
    RARE(2),
    VERY_RARE(1);

    public final int weight;

    private Rarity(int weight) {
        this.weight = weight;
    }
}
