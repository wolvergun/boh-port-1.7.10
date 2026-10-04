package net.mcreator.boh.compat.mc.world;

import net.minecraft.world.World;

/** 1.20 DifficultyInstance (regional difficulty), from the world difficulty and day count. */
public class DifficultyInstance {

    private final Difficulty base;
    private final float effective;

    public DifficultyInstance(World world) {
        base = world == null ? Difficulty.NORMAL : Difficulty.of(world.difficultySetting);
        float days = world == null ? 0 : Math.min(1f, world.getWorldTime() / 24000f / 63f);
        effective = base == Difficulty.PEACEFUL ? 0 : 0.75f + days + (base == Difficulty.HARD ? 0.5f : 0);
    }

    public Difficulty getDifficulty() {
        return base;
    }

    public float getEffectiveDifficulty() {
        return effective;
    }

    public boolean isHard() {
        return base == Difficulty.HARD;
    }

    public boolean isHarderThan(float f) {
        return effective > f;
    }

    public float getSpecialMultiplier() {
        return effective < 2.0F ? 0.0F : effective > 4.0F ? 1.0F : (effective - 2.0F) / 2.0F;
    }
}
