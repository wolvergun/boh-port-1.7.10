package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public class AttackMyersProcedure {
   public static boolean execute(Entity entity) {
      return entity == null
         ? false
         : (entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof LivingEntity _livEnt1
            && _livEnt1.hasEffect((MobEffect)BohModMobEffects.SHAPES_GAZE.get());
   }
}
