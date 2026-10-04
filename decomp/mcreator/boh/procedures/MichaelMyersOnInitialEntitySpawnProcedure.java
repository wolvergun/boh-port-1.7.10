package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.MichaelMyersEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class MichaelMyersOnInitialEntitySpawnProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         BohMod.queueServerWork(5, () -> {
            if (Math.random() < 0.25) {
               if (entity instanceof MichaelMyersEntity animatable) {
                  animatable.setTexture("myers_black");
               }

               if (entity instanceof MichaelMyersEntity _datEntSetI) {
                  _datEntSetI.getEntityData().set(MichaelMyersEntity.DATA_variant, 1);
               }
            } else if (Math.random() < 0.25) {
               if (entity instanceof MichaelMyersEntity animatable) {
                  animatable.setTexture("myers_green");
               }

               if (entity instanceof MichaelMyersEntity _datEntSetI) {
                  _datEntSetI.getEntityData().set(MichaelMyersEntity.DATA_variant, 4);
               }
            } else if (Math.random() < 0.25) {
               if (entity instanceof MichaelMyersEntity animatable) {
                  animatable.setTexture("myers_strick");
               }

               if (entity instanceof MichaelMyersEntity _datEntSetI) {
                  _datEntSetI.getEntityData().set(MichaelMyersEntity.DATA_variant, 3);
               }
            } else if (Math.random() < 0.05) {
               if (entity instanceof MichaelMyersEntity animatable) {
                  animatable.setTexture("myers_bob");
               }

               if (entity instanceof MichaelMyersEntity _datEntSetI) {
                  _datEntSetI.getEntityData().set(MichaelMyersEntity.DATA_variant, 2);
               }
            } else if (entity instanceof MichaelMyersEntity _datEntSetI) {
               _datEntSetI.getEntityData().set(MichaelMyersEntity.DATA_variant, 0);
            }
         });
      }
   }
}
