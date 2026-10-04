package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.BloodwaveEntity;
import net.mcreator.boh.init.BohModParticleTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BloodwaveOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            M.setDeltaMovement(entity, new Vec3(M.getLookAngle(entity).x * 0.5, 0.0, M.getLookAngle(entity).z * 0.5));
            if (entity instanceof BloodwaveEntity _datEntSetI) {
                M.set(
                    M.getEntityData(_datEntSetI),
                    BloodwaveEntity.DATA_disappear,
                    (entity instanceof BloodwaveEntity _datEntI ? M.getEntityData(_datEntI).get(BloodwaveEntity.DATA_disappear) : 0) + 1
                );
            }

            if ((entity instanceof BloodwaveEntity _datEntI ? M.getEntityData(_datEntI).get(BloodwaveEntity.DATA_disappear) : 0) == 34
                && !M.isClientSide(M.level(entity))) {
                M.discard(entity);
            }

            if (world instanceof WorldServer _level) {
                M.sendParticles(_level, BohModParticleTypes.BLOOD_FALL.get(), M.getX(entity), M.getY(entity), M.getZ(entity), 10, 0.5, 0.1, 0.5, 0.01);
            }

            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(1.0), e -> true)
                .stream()
                .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                .toList()) {
                if (!(entityiterator instanceof BloodwaveEntity)
                    || !((entity instanceof EntityTameable _tamEnt ? M.getOwner(_tamEnt) : null) instanceof EntityPlayer)) {
                    M.hurt(
                        entityiterator,
                        M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.DROWN)),
                        10.0F
                    );
                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 200, 4, false, false));
                    }
                }
            }
        }
    }
}
