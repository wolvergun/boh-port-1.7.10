package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class PredatorSightOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(2.0), e -> true)
                .stream()
                .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                .toList()) {
                if (entityiterator instanceof EntityLivingBase _livEnt0
                    && M.hasEffect(_livEnt0, BohModMobEffects.SIGHT_OF_THE_PREDATOR.get())
                    && entityiterator instanceof EntityLivingBase) {
                    M.setYRot(entity, M.getYRot(entityiterator));
                    M.setXRot(entity, M.getXRot(entityiterator));
                    M.setYBodyRot(entity, M.getYRot(entity));
                    M.setYHeadRot(entity, M.getYRot(entity));
                    M.set_yRotO(entity, M.getYRot(entity));
                    M.set_xRotO(entity, M.getXRot(entity));
                    if (entity instanceof EntityLivingBase _entity) {
                        M.set_yBodyRotO(_entity, M.getYRot(_entity));
                        M.set_yHeadRotO(_entity, M.getYRot(_entity));
                    }

                    M.teleportTo(entity, M.getX(entityiterator), M.getY(entityiterator), M.getZ(entityiterator));
                    if (entity instanceof EntityPlayerMP _serverPlayer) {
                        M.teleport(
                            M.connection(_serverPlayer),
                            M.getX(entityiterator),
                            M.getY(entityiterator),
                            M.getZ(entityiterator),
                            M.getYRot(entity),
                            M.getXRot(entity)
                        );
                    }
                }
            }

            Entity var16 = M.getEntitiesOfClass(world, EntityLivingBase.class, AABB.ofSize(new Vec3(x, y, z), 2.0, 2.0, 2.0), e -> true)
                .stream()
                .sorted((new Object() {
                    Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                        return Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _x, _y, _z));
                    }
                }).compareDistOf(x, y, z))
                .findFirst()
                .orElse(null);
            if (!(var16 instanceof EntityLivingBase _livEnt11 && M.hasEffect(_livEnt11, BohModMobEffects.SIGHT_OF_THE_PREDATOR.get()))
                && !M.isClientSide(M.level(entity))) {
                M.discard(entity);
            }
        }
    }
}
