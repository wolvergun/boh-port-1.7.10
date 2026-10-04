package net.mcreator.boh.potion;

import java.util.ArrayList;
import java.util.List;
import net.mcreator.boh.procedures.IntoTheFogEffectStartedappliedProcedure;
import net.mcreator.boh.procedures.IntoTheFogOnEffectActiveTickProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.item.ItemStack;

public class IntoTheFogMobEffect extends MobEffect {
   public IntoTheFogMobEffect() {
      super(MobEffectCategory.HARMFUL, -12369085);
   }

   public List<ItemStack> getCurativeItems() {
      return new ArrayList<>();
   }

   public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
      super.addAttributeModifiers(entity, attributeMap, amplifier);
      IntoTheFogEffectStartedappliedProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ());
   }

   public void applyEffectTick(LivingEntity entity, int amplifier) {
      IntoTheFogOnEffectActiveTickProcedure.execute(entity.level(), entity);
   }

   public boolean isDurationEffectTick(int duration, int amplifier) {
      return true;
   }
}
