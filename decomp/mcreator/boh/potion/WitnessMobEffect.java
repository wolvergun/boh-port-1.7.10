package net.mcreator.boh.potion;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;

public class WitnessMobEffect extends MobEffect {
   public WitnessMobEffect() {
      super(MobEffectCategory.HARMFUL, -4587520);
   }

   public List<ItemStack> getCurativeItems() {
      return new ArrayList<>();
   }

   public boolean isDurationEffectTick(int duration, int amplifier) {
      return true;
   }
}
