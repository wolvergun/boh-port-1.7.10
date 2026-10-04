package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.TamedRatEntity;
import net.minecraft.world.entity.Entity;

public class TamedRatOnEntityTickUpdateProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (entity.getPersistentData().getDouble("skin") == 0.0) {
            if (entity instanceof TamedRatEntity animatable) {
               animatable.setTexture("rat1_tamed");
            }
         } else if (entity.getPersistentData().getDouble("skin") == 0.0) {
            if (entity instanceof TamedRatEntity animatable) {
               animatable.setTexture("rat2_tamed");
            }
         } else if (entity.getPersistentData().getDouble("skin") == 0.0 && entity instanceof TamedRatEntity animatable) {
            animatable.setTexture("rat3_tamed");
         }
      }
   }
}
