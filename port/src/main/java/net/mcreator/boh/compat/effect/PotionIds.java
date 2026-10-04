package net.mcreator.boh.compat.effect;

import net.minecraft.potion.Potion;

/** Hands out free potion ids above the vanilla range (EndlessIDs extends the array in the pack). */
public final class PotionIds {

    private static int next = 64;

    private PotionIds() {}

    public static synchronized int next() {
        while (next < Potion.potionTypes.length && Potion.potionTypes[next] != null) next++;
        if (next >= Potion.potionTypes.length) {
            for (int i = 32; i < Potion.potionTypes.length; i++) if (Potion.potionTypes[i] == null) return i;
            throw new IllegalStateException("No free potion ids; install a potion id extender such as EndlessIDs");
        }
        return next++;
    }
}
