package net.mcreator.boh.compat.mc.sounds;

import net.minecraft.util.ResourceLocation;

public final class SoundEvents {

    public static final SoundEvent EMPTY = new SoundEvent(new ResourceLocation("minecraft", "intentionally_empty"));
    public static final SoundEvent GENERIC_HURT = new SoundEvent(new ResourceLocation("minecraft", "entity.generic.hurt"));
    public static final SoundEvent GENERIC_DEATH = new SoundEvent(new ResourceLocation("minecraft", "entity.generic.death"));
    public static final SoundEvent ARROW_SHOOT = new SoundEvent(new ResourceLocation("minecraft", "entity.arrow.shoot"));
    public static final SoundEvent ARROW_HIT = new SoundEvent(new ResourceLocation("minecraft", "entity.arrow.hit_player"));

    private SoundEvents() {}
}
