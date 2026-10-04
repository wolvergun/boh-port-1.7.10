package net.mcreator.boh.compat.mc.world.item.alchemy;

public final class Potions {

    public static final BrewPotion WATER = new BrewPotion();
    public static final BrewPotion EMPTY = WATER;
    public static final BrewPotion AWKWARD = new BrewPotion();
    public static final BrewPotion MUNDANE = new BrewPotion();
    public static final BrewPotion THICK = new BrewPotion();
    /** Any other 1.7.10 vanilla potion (its effects live in the metadata, not in a registry). */
    public static final BrewPotion VANILLA_EFFECT = new BrewPotion();

    private Potions() {}
}
