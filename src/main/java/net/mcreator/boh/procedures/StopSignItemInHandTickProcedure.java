package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class StopSignItemInHandTickProcedure {
    public static void execute(Entity entity) {
        if (entity != null && entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
            M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 0, false, false));
        }
    }
}
