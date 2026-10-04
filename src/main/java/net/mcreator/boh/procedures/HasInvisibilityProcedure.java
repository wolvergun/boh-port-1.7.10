package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class HasInvisibilityProcedure {
    public static boolean execute(Entity entity) {
        return entity == null ? false : entity instanceof EntityLivingBase _livEnt0 && M.hasEffect(_livEnt0, MobEffects.INVISIBILITY);
    }
}
