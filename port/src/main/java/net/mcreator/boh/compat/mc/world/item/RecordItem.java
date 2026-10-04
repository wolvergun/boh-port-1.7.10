package net.mcreator.boh.compat.mc.world.item;

import java.util.function.Supplier;

import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.ResourceLocation;

/** 1.20 RecordItem on top of 1.7.10 {@link ItemRecord}, playing the mod's own disc sound. */
public class RecordItem extends ItemRecord {

    private final Supplier<SoundEvent> sound;

    public RecordItem(int comparatorValue, Supplier<SoundEvent> sound, Properties props, int lengthInTicks) {
        super("boh_" + Integer.toHexString(System.identityHashCode(sound)));
        this.sound = sound;
        setMaxStackSize(1);
    }

    @Override
    public ResourceLocation getRecordResource(String name) {
        SoundEvent s = sound.get();
        return s == null ? super.getRecordResource(name) : new ResourceLocation(s.legacyName());
    }

    @Override
    public String getRecordNameLocal() {
        return net.minecraft.util.StatCollector.translateToLocal(getUnlocalizedName() + ".desc");
    }
}
