package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class PyramidHeadEntityDiesProcedure {
    public static void execute(World world, double x, double y, double z) {
        Vec3 _center = new Vec3(x, y, z);

        for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(50.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
            .toList()) {
            if (entityiterator instanceof EntityLivingBase _entity) {
                M.removeEffect(_entity, BohModMobEffects.INTO_THE_FOG.get());
            }
        }
    }
}
