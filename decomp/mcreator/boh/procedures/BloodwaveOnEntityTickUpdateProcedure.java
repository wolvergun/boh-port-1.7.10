package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.entity.BloodwaveEntity;
import net.mcreator.boh.init.BohModParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BloodwaveOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * 0.5, 0.0, entity.getLookAngle().z * 0.5));
         if (entity instanceof BloodwaveEntity _datEntSetI) {
            _datEntSetI.getEntityData()
               .set(
                  BloodwaveEntity.DATA_disappear,
                  (entity instanceof BloodwaveEntity _datEntI ? (Integer)_datEntI.getEntityData().get(BloodwaveEntity.DATA_disappear) : 0) + 1
               );
         }

         if ((entity instanceof BloodwaveEntity _datEntI ? (Integer)_datEntI.getEntityData().get(BloodwaveEntity.DATA_disappear) : 0) == 34
            && !entity.level().isClientSide()) {
            entity.discard();
         }

         if (world instanceof ServerLevel _level) {
            _level.sendParticles(
               (SimpleParticleType)BohModParticleTypes.BLOOD_FALL.get(), entity.getX(), entity.getY(), entity.getZ(), 10, 0.5, 0.1, 0.5, 0.01
            );
         }

         Vec3 _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(1.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
            .toList()) {
            if (!(entityiterator instanceof BloodwaveEntity) || !((entity instanceof TamableAnimal _tamEnt ? _tamEnt.getOwner() : null) instanceof Player)) {
               entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.DROWN)), 10.0F);
               if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                  _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 4, false, false));
               }
            }
         }
      }
   }
}
