package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class VoodooDollBlockDestroyedByPlayerProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      Vec3 _center = new Vec3(x, y, z);

      for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(12.5), e -> true)
         .stream()
         .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
         .toList()) {
         if (entityiterator instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(MobEffects.BLINDNESS)) {
            if (entityiterator instanceof LivingEntity _entity) {
               _entity.removeEffect(MobEffects.BLINDNESS);
            }
         } else if (entityiterator instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
            if (entityiterator instanceof LivingEntity _entity) {
               _entity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
            }
         } else if (entityiterator instanceof LivingEntity _livEnt4 && _livEnt4.hasEffect(MobEffects.POISON)) {
            if (entityiterator instanceof LivingEntity _entity) {
               _entity.removeEffect(MobEffects.POISON);
            }
         } else if (entityiterator instanceof LivingEntity _livEnt6 && _livEnt6.hasEffect(MobEffects.HUNGER) && entityiterator instanceof LivingEntity _entity
            )
          {
            _entity.removeEffect(MobEffects.HUNGER);
         }
      }
   }
}
