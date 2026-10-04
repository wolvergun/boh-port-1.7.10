package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.entity.SquidwardEntity;
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

public class SquidwardOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (!(entity instanceof SquidwardEntity _datEntL0 && (Boolean)_datEntL0.getEntityData().get(SquidwardEntity.DATA_start))) {
            if (!(entity instanceof SquidwardEntity _datEntL1 && (Boolean)_datEntL1.getEntityData().get(SquidwardEntity.DATA_variant))
               && entity instanceof SquidwardEntity animatable) {
               animatable.setTexture("squidward");
            }

            if (entity instanceof SquidwardEntity _datEntL3
               && (Boolean)_datEntL3.getEntityData().get(SquidwardEntity.DATA_variant)
               && entity instanceof SquidwardEntity animatable) {
               animatable.setTexture("squidward_mist");
            }

            if (entity instanceof SquidwardEntity _datEntL5 && (Boolean)_datEntL5.getEntityData().get(SquidwardEntity.DATA_variant)) {
               if (entity instanceof SquidwardEntity _datEntSetI) {
                  _datEntSetI.getEntityData()
                     .set(
                        SquidwardEntity.DATA_cooldown_squidward,
                        (entity instanceof SquidwardEntity _datEntI ? (Integer)_datEntI.getEntityData().get(SquidwardEntity.DATA_cooldown_squidward) : 0) + 1
                     );
               }

               if ((entity instanceof SquidwardEntity _datEntI ? (Integer)_datEntI.getEntityData().get(SquidwardEntity.DATA_cooldown_squidward) : 0) == 1
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

               if ((entity instanceof SquidwardEntity _datEntI ? (Integer)_datEntI.getEntityData().get(SquidwardEntity.DATA_cooldown_squidward) : 0) == 200
                  && entity instanceof SquidwardEntity _datEntSetI) {
                  _datEntSetI.getEntityData().set(SquidwardEntity.DATA_cooldown_squidward, 0);
               }
            }

            if (!(entity instanceof SquidwardEntity _datEntL12 && (Boolean)_datEntL12.getEntityData().get(SquidwardEntity.DATA_variant))
               && Math.random() < 0.005
               && entity instanceof SquidwardEntity _datEntSetL) {
               _datEntSetL.getEntityData().set(SquidwardEntity.DATA_variant, true);
            }

            if (entity instanceof SquidwardEntity _datEntL14
               && (Boolean)_datEntL14.getEntityData().get(SquidwardEntity.DATA_variant)
               && Math.random() < 0.005
               && entity instanceof SquidwardEntity _datEntSetL) {
               _datEntSetL.getEntityData().set(SquidwardEntity.DATA_variant, false);
            }

            if (entity instanceof SquidwardEntity _datEntL16 && (Boolean)_datEntL16.getEntityData().get(SquidwardEntity.DATA_variant)) {
               Vec3 _center = new Vec3(x, y, z);

               for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8.0), e -> true)
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
   }
}
