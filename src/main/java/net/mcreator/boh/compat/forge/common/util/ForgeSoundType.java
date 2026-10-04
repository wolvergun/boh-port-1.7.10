package net.mcreator.boh.compat.forge.common.util;

import java.util.function.Supplier;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;

public class ForgeSoundType extends SoundType {
    public ForgeSoundType(
        float volume,
        float pitch,
        Supplier<SoundEvent> breakSound,
        Supplier<SoundEvent> stepSound,
        Supplier<SoundEvent> placeSound,
        Supplier<SoundEvent> hitSound,
        Supplier<SoundEvent> fallSound
    ) {
        super(volume, pitch, breakSound, stepSound, placeSound, hitSound, fallSound);
    }
}
