package net.mcreator.boh.potion;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;

public class RedMistMobEffect extends MobEffect {
   public RedMistMobEffect() {
      super(MobEffectCategory.HARMFUL, -12369085);
   }

   public List<ItemStack> getCurativeItems() {
      return new ArrayList<>();
   }

   public boolean isDurationEffectTick(int duration, int amplifier) {
      return true;
   }
}
