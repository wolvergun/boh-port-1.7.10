package net.mcreator.boh.compat.mc.world.item;

import java.util.function.Supplier;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class RecordItem extends ItemRecord {
    private final Supplier<SoundEvent> sound;

    public RecordItem(int comparatorValue, Supplier<SoundEvent> sound, Properties props, int lengthInTicks) {
        super("boh_" + Integer.toHexString(System.identityHashCode(sound)));
        this.sound = sound;
        this.setMaxStackSize(1);
    }

    public ResourceLocation getRecordResource(String name) {
        SoundEvent s = this.sound.get();
        return s == null ? super.getRecordResource(name) : new ResourceLocation(s.legacyName());
    }

    public String getRecordNameLocal() {
        return StatCollector.translateToLocal(this.getUnlocalizedName() + ".desc");
    }
}
