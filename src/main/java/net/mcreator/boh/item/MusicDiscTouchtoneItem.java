package net.mcreator.boh.item;

import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.mcreator.boh.compat.mc.world.item.RecordItem;
import net.minecraft.util.ResourceLocation;

public class MusicDiscTouchtoneItem extends RecordItem {
    public MusicDiscTouchtoneItem() {
        super(
            0,
            () -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:music_disc_touchtone")),
            new Properties().stacksTo(1).rarity(Rarity.RARE),
            5660
        );
    }
}
