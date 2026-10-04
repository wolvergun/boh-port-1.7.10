package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

public class RakeAttackProcedure {
    public static boolean execute(Entity entity) {
        boolean var10000;
        if (entity == null) {
            var10000 = false;
        } else {
            EntityLivingBase var3 = entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null;
            var10000 = !(var3 instanceof EntityLivingBase) || !M.hasEffect(var3, MobEffects.BLINDNESS);
        }

        return var10000;
    }
}
