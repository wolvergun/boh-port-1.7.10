package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class OverlayNPC001DisplayOverlayIngameProcedure {
   public static boolean execute(Entity entity) {
      return entity == null ? false : entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect((MobEffect)BohModMobEffects.NPC_000INFLUENCE.get());
   }
}
