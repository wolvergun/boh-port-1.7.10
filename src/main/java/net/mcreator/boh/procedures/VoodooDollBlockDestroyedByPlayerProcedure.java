package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class VoodooDollBlockDestroyedByPlayerProcedure {
    public static void execute(World world, double x, double y, double z) {
        Vec3 _center = new Vec3(x, y, z);

        for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(12.5), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
            .toList()) {
            if (entityiterator instanceof EntityLivingBase _livEnt0 && M.hasEffect(_livEnt0, MobEffects.BLINDNESS)) {
                if (entityiterator instanceof EntityLivingBase _entity) {
                    M.removeEffect(_entity, MobEffects.BLINDNESS);
                }
            } else if (entityiterator instanceof EntityLivingBase _livEnt2 && M.hasEffect(_livEnt2, MobEffects.MOVEMENT_SLOWDOWN)) {
                if (entityiterator instanceof EntityLivingBase _entity) {
                    M.removeEffect(_entity, MobEffects.MOVEMENT_SLOWDOWN);
                }
            } else if (entityiterator instanceof EntityLivingBase _livEnt4 && M.hasEffect(_livEnt4, MobEffects.POISON)) {
                if (entityiterator instanceof EntityLivingBase _entity) {
                    M.removeEffect(_entity, MobEffects.POISON);
                }
            } else if (entityiterator instanceof EntityLivingBase _livEnt6
                && M.hasEffect(_livEnt6, MobEffects.HUNGER)
                && entityiterator instanceof EntityLivingBase _entity) {
                M.removeEffect(_entity, MobEffects.HUNGER);
            }
        }
    }
}
