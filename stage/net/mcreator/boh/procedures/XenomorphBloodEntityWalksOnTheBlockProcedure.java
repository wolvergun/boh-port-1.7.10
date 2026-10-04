package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.ChestbursterEntity;
import net.mcreator.boh.entity.FacehuggerEntity;
import net.mcreator.boh.entity.GojiEntity;
import net.mcreator.boh.entity.XenomorphEntity;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.mcreator.boh.compat.M;

public class XenomorphBloodEntityWalksOnTheBlockProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            if (!(entity instanceof FacehuggerEntity) && !(entity instanceof ChestbursterEntity) && !(entity instanceof GojiEntity) && !(entity instanceof XenomorphEntity)) {
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.POISON, 60, 1, false, false));
                }
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.WITHER, 60, 1, false, false));
                }
            }
        }
    }
}
