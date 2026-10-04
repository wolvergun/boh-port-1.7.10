package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.JamesSunderlandEntity;
import net.minecraft.world.entity.Entity;

public class JamesSunderlandEntityIsHurtProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (entity instanceof JamesSunderlandEntity) {
            ((JamesSunderlandEntity)entity).setAnimation("hurt");
         }
      }
   }
}
