package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.entity.SquidwardDoomedEntity;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class SquidwardDoomedOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (entity instanceof SquidwardDoomedEntity _datEntSetI) {
            _datEntSetI.getEntityData()
               .set(
                  SquidwardDoomedEntity.DATA_cooldown_squidward,
                  (entity instanceof SquidwardDoomedEntity _datEntI ? (Integer)_datEntI.getEntityData().get(SquidwardDoomedEntity.DATA_cooldown_squidward) : 0)
                     + 1
               );
         }

         if ((entity instanceof SquidwardDoomedEntity _datEntI ? (Integer)_datEntI.getEntityData().get(SquidwardDoomedEntity.DATA_cooldown_squidward) : 0)
               == 1
            && world instanceof Level _level) {
            if (!_level.isClientSide()) {
               _level.playSound(
                  null,
                  BlockPos.containing(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:squidward_redmist")),
                  SoundSource.HOSTILE,
                  1.0F,
                  1.0F
               );
            } else {
               _level.playLocalSound(
                  x,
                  y,
                  z,
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:squidward_redmist")),
                  SoundSource.HOSTILE,
                  1.0F,
                  1.0F,
                  false
               );
            }
         }

         if ((entity instanceof SquidwardDoomedEntity _datEntI ? (Integer)_datEntI.getEntityData().get(SquidwardDoomedEntity.DATA_cooldown_squidward) : 0)
               == 200
            && entity instanceof SquidwardDoomedEntity _datEntSetI) {
            _datEntSetI.getEntityData().set(SquidwardDoomedEntity.DATA_cooldown_squidward, 0);
         }

         Vec3 _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(10.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
            .toList()) {
            if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance((MobEffect)BohModMobEffects.RED_MIST.get(), 60, 0, false, false));
            }
         }
      }
   }
}
