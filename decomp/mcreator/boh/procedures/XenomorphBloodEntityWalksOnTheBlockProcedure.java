package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.ChestbursterEntity;
import net.mcreator.boh.entity.FacehuggerEntity;
import net.mcreator.boh.entity.GojiEntity;
import net.mcreator.boh.entity.XenomorphEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class XenomorphBloodEntityWalksOnTheBlockProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (!(entity instanceof FacehuggerEntity)
            && !(entity instanceof ChestbursterEntity)
            && !(entity instanceof GojiEntity)
            && !(entity instanceof XenomorphEntity)) {
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 1, false, false));
            }

            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 1, false, false));
            }
         }
      }
   }
}
