package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class BendrownedOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         Vec3 _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
            .toList()) {
            if (Math.random() < 0.03 && Math.random() < 0.1 && entityiterator instanceof Player) {
               entityiterator.setSecondsOnFire(45);
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
                        "/execute at @e[type=boh:bendrowned,distance=0..5] run playsound boh:ben_laughing hostile @p ~ ~ ~"
                     );
               }

               if (world.isClientSide() && world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:ben_burning")),
                        SoundSource.RECORDS,
                        2.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:ben_burning")),
                        SoundSource.RECORDS,
                        2.0F,
                        1.0F,
                        false
                     );
                  }
               }
            }
         }

         _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
            .toList()) {
            if (Math.random() < 0.03 && Math.random() < 0.1 && entityiterator instanceof Player) {
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
                        "/execute at @e[type=boh:bendrowned,distance=0..5] run playsound boh:ben_laughing hostile @p ~ ~ ~"
                     );
               }

               _ent = entity;
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
                        "/spreadplayers ~ ~ 20 30 false @p"
                     );
               }
            }
         }

         _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(50.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
            .toList()) {
            if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance((MobEffect)BohModMobEffects.DROWNING.get(), 20, 0, false, false));
            }
         }

         if (entity.isInWater()) {
            entity.getPersistentData().putBoolean("inWater", true);
         }

         if (!entity.isInWater()) {
            entity.getPersistentData().putBoolean("inWater", false);
         }
      }
   }
}
