package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.RatEntity;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class RatOnInitialEntitySpawnProcedure {

    public static void execute(World world, Entity entity) {
        if (entity != null) {
            if (entity instanceof RatEntity) {
                ((RatEntity) entity).setAnimation("spawn");
            }
            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 60, 254, false, false));
            }
            BohMod.queueServerWork(2, () -> {
                if (Math.random() < 0.25) {
                    M.putDouble(M.getPersistentData(entity), "skin", 0.0);
                    if (entity instanceof RatEntity animatable) {
                        animatable.setTexture("rat1");
                    }
                } else if (Math.random() < 0.25) {
                    M.putDouble(M.getPersistentData(entity), "skin", 1.0);
                    if (entity instanceof RatEntity animatable) {
                        animatable.setTexture("rat2");
                    }
                } else {
                    M.putDouble(M.getPersistentData(entity), "skin", 2.0);
                    if (entity instanceof RatEntity animatable) {
                        animatable.setTexture("rat3");
                    }
                }
            });
        }
    }
}
