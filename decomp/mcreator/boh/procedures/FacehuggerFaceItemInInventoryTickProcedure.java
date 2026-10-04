package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.LevelAccessor;

public class FacehuggerFaceItemInInventoryTickProcedure {
   public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BINDING_CURSE, itemstack) == 0) {
            itemstack.enchant(Enchantments.BINDING_CURSE, 1);
         }

         if (!(entity instanceof LivingEntity _livEnt4 && _livEnt4.hasEffect((MobEffect)BohModMobEffects.FACE_HUGGER_EFFECT.get()))
            && entity instanceof LivingEntity _entity
            && !_entity.level().isClientSide()) {
            _entity.addEffect(new MobEffectInstance((MobEffect)BohModMobEffects.FACE_HUGGER_EFFECT.get(), 6000, 0, false, false));
         }

         if (!(entity instanceof LivingEntity _livEnt6 && _livEnt6.hasEffect((MobEffect)BohModMobEffects.TIMER_OVERLAY.get()))
            && entity instanceof LivingEntity _entity
            && !_entity.level().isClientSide()) {
            _entity.addEffect(new MobEffectInstance((MobEffect)BohModMobEffects.TIMER_OVERLAY.get(), 1000, 0, false, false));
         }
      }
   }
}
