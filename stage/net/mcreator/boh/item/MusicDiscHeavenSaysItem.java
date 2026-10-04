package net.mcreator.boh.item;

import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.mcreator.boh.compat.mc.world.item.RecordItem;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;

public class MusicDiscHeavenSaysItem extends RecordItem {

    public MusicDiscHeavenSaysItem() {
        super(0, () -> (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:music_disc_heaven_says")), new Properties().stacksTo(1).rarity(Rarity.RARE), 2418);
    }
}
