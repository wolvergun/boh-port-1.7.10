package net.mcreator.boh.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.registries.ForgeRegistries;

public class MusicDiscNeutral01Item extends RecordItem {
   public MusicDiscNeutral01Item() {
      super(
         0,
         () -> (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:music_disc_neutral01")),
         new Properties().stacksTo(1).rarity(Rarity.RARE),
         4880
      );
   }
}
