package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLiving;
import net.mcreator.boh.compat.M;

public class RakeAttackProcedure {

    public static boolean execute(Entity entity) {
        return entity == null ? false : !((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityLivingBase _livEnt1 && M.hasEffect(_livEnt1, MobEffects.BLINDNESS));
    }
}
