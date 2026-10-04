package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLiving;
import net.mcreator.boh.compat.M;

public class AttackMyersProcedure {

    public static boolean execute(Entity entity) {
        return entity == null ? false : (entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityLivingBase _livEnt1 && M.hasEffect(_livEnt1, (Potion) BohModMobEffects.SHAPES_GAZE.get());
    }
}
