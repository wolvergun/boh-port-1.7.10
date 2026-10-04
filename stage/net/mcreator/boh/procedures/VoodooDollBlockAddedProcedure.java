package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.M;

public class VoodooDollBlockAddedProcedure {

    public static void execute(World world, double x, double y, double z) {
        Vec3 _center = new Vec3(x, y, z);
        for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(12.5), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
            if (Math.random() < 0.3) {
                if (entityiterator instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.BLINDNESS, 1000, 0, true, true));
                }
            } else if (Math.random() < 0.3) {
                if (entityiterator instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 1000, 2, true, true));
                }
            } else if (Math.random() < 0.3) {
                if (entityiterator instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.POISON, 1000, 0, true, true));
                }
            } else if (entityiterator instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.HUNGER, 1000, 0, true, true));
            }
        }
    }
}
