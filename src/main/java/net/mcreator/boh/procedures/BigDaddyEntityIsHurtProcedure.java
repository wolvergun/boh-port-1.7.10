package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.LittleSisterEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class BigDaddyEntityIsHurtProcedure {
    public static void execute(World world, double x, double y, double z, Entity sourceentity) {
        if (sourceentity != null && sourceentity instanceof EntityLivingBase) {
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(10.0), e -> true)
                .stream()
                .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                .toList()) {
                if (entityiterator instanceof LittleSisterEntity) {
                    M.setShiftKeyDown(entityiterator, true);
                    if (entityiterator instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.CONFUSION, 600, 0, false, false));
                    }
                }
            }
        }
    }
}
