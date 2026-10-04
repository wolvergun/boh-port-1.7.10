package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.DeerEntity;
import net.minecraft.world.entity.Entity;

public class DeerEntityVisualScaleProcedure {
   public static double execute(Entity entity) {
      if (entity == null) {
         return 0.0;
      } else {
         return entity instanceof DeerEntity _datEntI ? ((Integer)_datEntI.getEntityData().get(DeerEntity.DATA_scale)).intValue() : 0.0;
      }
   }
}
