package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.init.BohModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class AnglerOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         entity.getPersistentData().putDouble("attack_angler", entity.getPersistentData().getDouble("attack_angler") + 1.0);
         if (entity.getPersistentData().getDouble("attack_angler") >= 44.0) {
            entity.getPersistentData().putDouble("attack_angler", 0.0);
            if (!world.isClientSide() && world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:angler_attack")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:angler_attack")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }
         }

         if (world instanceof ServerLevel _level) {
            _level.sendParticles(
               (SimpleParticleType)BohModParticleTypes.ANGLER_PARTICLE.get(),
               entity.getX(),
               entity.getY() + 1.5,
               entity.getZ(),
               1,
               0.0,
               0.0,
               0.0,
               0.0
            );
         }

         if (world instanceof ServerLevel _level) {
            _level.sendParticles(ParticleTypes.SQUID_INK, entity.getX(), entity.getY() + 1.5, entity.getZ(), 10, 1.0, 1.0, 1.0, 0.05);
         }

         Vec3 _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(15.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
            .toList()) {
            if (entityiterator instanceof Player
               && !entityiterator.isShiftKeyDown()
               && !(entity instanceof Mob _mob && _mob.isAggressive())
               && entity instanceof Mob _entity
               && entityiterator instanceof LivingEntity _ent) {
               _entity.setTarget(_ent);
            }
         }

         _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
            .toList()) {
            if (entityiterator instanceof Player
               && !(entity instanceof Mob _mob && _mob.isAggressive())
               && entity instanceof Mob _entity
               && entityiterator instanceof LivingEntity _ent) {
               _entity.setTarget(_ent);
            }
         }
      }
   }
}
