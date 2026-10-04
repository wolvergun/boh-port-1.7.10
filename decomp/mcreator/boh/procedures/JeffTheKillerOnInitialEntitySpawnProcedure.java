package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.JeffTheKillerEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class JeffTheKillerOnInitialEntitySpawnProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         BohMod.queueServerWork(4, () -> {
            if (Math.random() < 0.33) {
               if (entity instanceof JeffTheKillerEntity animatable) {
                  animatable.setTexture("jeff_stalk");
               }
            } else if (Math.random() < 0.66) {
               if (entity instanceof JeffTheKillerEntity animatable) {
                  animatable.setTexture("jeff_stalk_bleed");
               }
            } else if (entity instanceof JeffTheKillerEntity animatable) {
               animatable.setTexture("jeff");
            }
         });
      }
   }
}
