package net.mcreator.boh.potion;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SafeAndSoundMobEffect extends MobEffect {
   public SafeAndSoundMobEffect() {
      super(MobEffectCategory.BENEFICIAL, -5278257);
   }

   public List<ItemStack> getCurativeItems() {
      ArrayList<ItemStack> cures = new ArrayList<>();
      cures.add(new ItemStack(Items.MILK_BUCKET));
      return cures;
   }

   public boolean isDurationEffectTick(int duration, int amplifier) {
      return true;
   }
}
