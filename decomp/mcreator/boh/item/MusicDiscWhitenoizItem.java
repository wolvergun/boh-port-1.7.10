package net.mcreator.boh.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.registries.ForgeRegistries;

public class MusicDiscWhitenoizItem extends RecordItem {
   public MusicDiscWhitenoizItem() {
      super(
         0,
         () -> (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:music_disc_whitenoiz")),
         new Properties().stacksTo(1).rarity(Rarity.RARE),
         1680
      );
   }
}
