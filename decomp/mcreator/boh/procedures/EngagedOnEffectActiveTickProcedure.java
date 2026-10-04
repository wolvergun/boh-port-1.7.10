package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.entity.SlenderManEntity;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EngagedOnEffectActiveTickProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         Vec3 _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(300.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
            .toList()) {
            if (entityiterator instanceof SlenderManEntity) {
               if ((
                        entity instanceof LivingEntity _livEnt && _livEnt.hasEffect((MobEffect)BohModMobEffects.ENGAGED.get())
                           ? _livEnt.getEffect((MobEffect)BohModMobEffects.ENGAGED.get()).getAmplifier()
                           : 0
                     )
                     == 2
                  && entityiterator instanceof LivingEntity _entity
                  && !_entity.level().isClientSide()) {
                  _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, 0, false, false));
               }

               if ((
                        entity instanceof LivingEntity _livEnt && _livEnt.hasEffect((MobEffect)BohModMobEffects.ENGAGED.get())
                           ? _livEnt.getEffect((MobEffect)BohModMobEffects.ENGAGED.get()).getAmplifier()
                           : 0
                     )
                     == 4
                  && entityiterator instanceof LivingEntity _entity
                  && !_entity.level().isClientSide()) {
                  _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, 1, false, false));
               }

               if ((
                        entity instanceof LivingEntity _livEnt && _livEnt.hasEffect((MobEffect)BohModMobEffects.ENGAGED.get())
                           ? _livEnt.getEffect((MobEffect)BohModMobEffects.ENGAGED.get()).getAmplifier()
                           : 0
                     )
                     == 6
                  && entityiterator instanceof LivingEntity _entity
                  && !_entity.level().isClientSide()) {
                  _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, 2, false, false));
               }
            }
         }

         if (!world.getEntitiesOfClass(SlenderManEntity.class, AABB.ofSize(new Vec3(x, y, z), 32.0, 32.0, 32.0), e -> true).isEmpty()
            && world.getEntitiesOfClass(SlenderManEntity.class, AABB.ofSize(new Vec3(x, y, z), 16.0, 16.0, 16.0), e -> true).isEmpty()) {
            if (Math.random() < 0.1) {
               entity.getPersistentData().putDouble("static_slender", 1.0);
            } else if (Math.random() < 0.1) {
               entity.getPersistentData().putDouble("static_slender", 2.0);
            } else if (Math.random() < 0.1) {
               entity.getPersistentData().putDouble("static_slender", 3.0);
            } else if (Math.random() < 0.1) {
               entity.getPersistentData().putDouble("static_slender", 4.0);
            } else if (Math.random() < 0.1) {
               entity.getPersistentData().putDouble("static_slender", 5.0);
            }
         } else if (!world.getEntitiesOfClass(SlenderManEntity.class, AABB.ofSize(new Vec3(x, y, z), 16.0, 16.0, 16.0), e -> true).isEmpty()) {
            if (Math.random() < 0.1) {
               entity.getPersistentData().putDouble("static_slender", 6.0);
            } else if (Math.random() < 0.1) {
               entity.getPersistentData().putDouble("static_slender", 7.0);
            } else if (Math.random() < 0.1) {
               entity.getPersistentData().putDouble("static_slender", 8.0);
            } else if (Math.random() < 0.1) {
               entity.getPersistentData().putDouble("static_slender", 9.0);
            } else if (Math.random() < 0.1) {
               entity.getPersistentData().putDouble("static_slender", 10.0);
            }
         } else {
            entity.getPersistentData().putDouble("static_slender", 0.0);
         }

         if (world.getEntitiesOfClass(SlenderManEntity.class, AABB.ofSize(new Vec3(x, y, z), 32.0, 32.0, 32.0), e -> true).isEmpty()) {
            entity.getPersistentData().putDouble("static_slender", 0.0);
            Entity _ent = entity;
            if (!_ent.level().isClientSide() && _ent.getServer() != null) {
               _ent.getServer()
                  .getCommands()
                  .performPrefixedCommand(
                     new CommandSourceStack(
                        CommandSource.NULL,
                        _ent.position(),
                        _ent.getRotationVector(),
                        _ent.level() instanceof ServerLevel ? (ServerLevel)_ent.level() : null,
                        4,
                        _ent.getName().getString(),
                        _ent.getDisplayName(),
                        _ent.level().getServer(),
                        _ent
                     ),
                     "/spreadplayers ~ ~ 30 16 true @e[type=boh:slender_man]"
                  );
            }
         }
      }
   }
}
