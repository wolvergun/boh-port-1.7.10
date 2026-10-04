package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.entity.SixEntity;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class SixTargetingProcedure {
   @SubscribeEvent
   public static void onEntitySetsAttackTarget(LivingChangeTargetEvent event) {
      execute(event, event.getEntity());
   }

   public static void execute(Entity sourceentity) {
      execute(null, sourceentity);
   }

   private static void execute(@Nullable Event event, Entity sourceentity) {
      if (sourceentity != null) {
         if (sourceentity instanceof SixEntity) {
            if (sourceentity instanceof SixEntity animatable) {
               animatable.setTexture("six_happy");
            }
         } else if (sourceentity instanceof SixEntity animatable) {
            animatable.setTexture("six");
         }
      }
   }
}
