package net.mcreator.boh.potion;

import java.util.ArrayList;
import java.util.List;
import net.mcreator.boh.procedures.LycanthropyOnEffectActiveTickProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class LycanthropyMobEffect extends MobEffect {
   public LycanthropyMobEffect() {
      super(MobEffectCategory.NEUTRAL, -9285832);
   }

   public List<ItemStack> getCurativeItems() {
      return new ArrayList<>();
   }

   public void applyEffectTick(LivingEntity entity, int amplifier) {
      LycanthropyOnEffectActiveTickProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
   }

   public boolean isDurationEffectTick(int duration, int amplifier) {
      return true;
   }
}
