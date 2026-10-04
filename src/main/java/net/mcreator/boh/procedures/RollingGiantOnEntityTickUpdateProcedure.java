package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class RollingGiantOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof EntityLivingBase _livEnt0 && M.hasEffect(_livEnt0, BohModMobEffects.ROLLING_GIANT_EFFECT.get())) {
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
                }

                M.putDouble(M.getPersistentData(entity), "anger", M.getDouble(M.getPersistentData(entity), "anger") + 1.0);
            }

            if (M.getDouble(M.getPersistentData(entity), "anger") > 500.0) {
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SPEED, 20, 1, false, false));
                }

                if (entity instanceof EntityLivingBase _entity) {
                    M.removeEffect(_entity, MobEffects.MOVEMENT_SLOWDOWN);
                }
            }

            if (M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 40.0, 40.0, 40.0), e -> true))) {
                M.putDouble(M.getPersistentData(entity), "anger", 1.0);
            }
        }
    }
}
