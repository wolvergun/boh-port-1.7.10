package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.PyramidHeadEntity;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class PyramidHeadOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         entity.getPersistentData().putDouble("radio_static", entity.getPersistentData().getDouble("radio_static") + 1.0);
         if (entity.getPersistentData().getDouble("radio_static") == 160.0) {
            entity.getPersistentData().putDouble("radio_static", 0.0);
            if (world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sh_static")),
                     SoundSource.AMBIENT,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sh_static")), SoundSource.AMBIENT, 1.0F, 1.0F, false
                  );
               }
            }
         }

         if (entity.getPersistentData().getBoolean("trap_toggle")) {
            if (!entity.getPersistentData().getBoolean("trap") && (entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player) {
               entity.getPersistentData().putBoolean("trap", true);
            }

            if (entity.getPersistentData().getBoolean("trap")) {
               entity.getPersistentData().putBoolean("trap", false);
               entity.getPersistentData().putBoolean("trap_toggle", false);
               if (entity instanceof PyramidHeadEntity) {
                  ((PyramidHeadEntity)entity).setAnimation("place_trap");
               }

               if (world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:pyramidhead_hurt")),
                        SoundSource.HOSTILE,
                        1.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:pyramidhead_hurt")),
                        SoundSource.HOSTILE,
                        1.0F,
                        1.0F,
                        false
                     );
                  }
               }

               BohMod.queueServerWork(
                  10,
                  () -> {
                     if (world instanceof Level _levelx) {
                        if (!_levelx.isClientSide()) {
                           _levelx.playSound(
                              null,
                              BlockPos.containing(x, y, z),
                              (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.land")),
                              SoundSource.HOSTILE,
                              1.0F,
                              1.0F
                           );
                        } else {
                           _levelx.playLocalSound(
                              x,
                              y,
                              z,
                              (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.land")),
                              SoundSource.HOSTILE,
                              1.0F,
                              1.0F,
                              false
                           );
                        }
                     }
                  }
               );
               if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                  _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 255, false, false));
               }

               Vec3 _center = new Vec3(x, y, z);

               for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(15.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                  .toList()) {
                  if (entityiterator instanceof Player && world instanceof ServerLevel _level) {
                     Entity entityToSpawn = ((EntityType)BohModEntities.TORMENT_PYRAMID.get())
                        .spawn(
                           _level,
                           BlockPos.containing(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ()),
                           MobSpawnType.MOB_SUMMONED
                        );
                     if (entityToSpawn != null) {
                        entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                     }
                  }
               }
            }
         }

         if (Math.random() < 0.01) {
            entity.getPersistentData().putBoolean("trap_toggle", true);
         }
      }
   }
}
