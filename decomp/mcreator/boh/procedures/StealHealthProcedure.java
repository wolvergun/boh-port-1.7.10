package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class StealHealthProcedure {
   @SubscribeEvent
   public static void onEntityAttacked(LivingHurtEvent event) {
      if (event != null && event.getEntity() != null) {
         execute(event, event.getSource().getEntity());
      }
   }

   public static void execute(Entity sourceentity) {
      execute(null, sourceentity);
   }

   private static void execute(@Nullable Event event, Entity sourceentity) {
      if (sourceentity != null) {
         if (sourceentity instanceof LivingEntity _livEnt0
            && _livEnt0.hasEffect((MobEffect)BohModMobEffects.VAMPIRISM.get())
            && sourceentity instanceof LivingEntity _entity
            && !_entity.level().isClientSide()) {
            _entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 5, 1));
         }
      }
   }
}
