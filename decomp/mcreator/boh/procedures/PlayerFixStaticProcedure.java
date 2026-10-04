package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class PlayerFixStaticProcedure {
   @SubscribeEvent
   public static void onPlayerTick(PlayerTickEvent event) {
      if (event.phase == Phase.END) {
         execute(event, event.player);
      }
   }

   public static void execute(Entity entity) {
      execute(null, entity);
   }

   private static void execute(@Nullable Event event, Entity entity) {
      if (entity != null) {
         if (!(entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect((MobEffect)BohModMobEffects.HIDE_AND_SEEK.get()))) {
            entity.getPersistentData().putDouble("exe_static", 0.0);
            entity.getPersistentData().putDouble("exe_apparison", 0.0);
         }

         if (!(entity instanceof LivingEntity _livEnt3 && _livEnt3.hasEffect((MobEffect)BohModMobEffects.ENGAGED.get()))) {
            entity.getPersistentData().putDouble("static_slender", 0.0);
         }
      }
   }
}
