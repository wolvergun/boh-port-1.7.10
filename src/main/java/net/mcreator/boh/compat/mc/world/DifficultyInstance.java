package net.mcreator.boh.compat.mc.world;

import net.minecraft.world.World;

public class DifficultyInstance {
    private final Difficulty base;
    private final float effective;

    public DifficultyInstance(World world) {
        this.base = world == null ? Difficulty.NORMAL : Difficulty.of(world.difficultySetting);
        float days = world == null ? 0.0F : Math.min(1.0F, (float)world.getWorldTime() / 24000.0F / 63.0F);
        this.effective = this.base == Difficulty.PEACEFUL ? 0.0F : 0.75F + days + (this.base == Difficulty.HARD ? 0.5F : 0.0F);
    }

    public Difficulty getDifficulty() {
        return this.base;
    }

    public float getEffectiveDifficulty() {
        return this.effective;
    }

    public boolean isHard() {
        return this.base == Difficulty.HARD;
    }

    public boolean isHarderThan(float f) {
        return this.effective > f;
    }

    public float getSpecialMultiplier() {
        return this.effective < 2.0F ? 0.0F : (this.effective > 4.0F ? 1.0F : (this.effective - 2.0F) / 2.0F);
    }
}
