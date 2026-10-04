package net.mcreator.boh.item;

import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.mcreator.boh.compat.mc.world.item.RecordItem;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;

public class MusicDiscNeutral01Item extends RecordItem {

    public MusicDiscNeutral01Item() {
        super(0, () -> (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:music_disc_neutral01")), new Properties().stacksTo(1).rarity(Rarity.RARE), 4880);
    }
}
