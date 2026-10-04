package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.InkDemonEntity;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class InkDemonOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (entity instanceof Mob _mob && _mob.isAggressive()) {
            if (!(entity instanceof LivingEntity _livEnt1 && _livEnt1.hasEffect(MobEffects.SATURATION))
               && Math.random() < 0.02
               && Math.random() < 0.02
               && entity instanceof LivingEntity _entity
               && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.SATURATION, 30, 0, false, false));
            }

            if ((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 30, 0, false, false));
            }

            if (!(entity instanceof InkDemonEntity _datEntL5 && (Boolean)_datEntL5.getEntityData().get(InkDemonEntity.DATA_TeleportBendy))
               && world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 30.0, 30.0, 30.0), e -> true).isEmpty()
               && Math.random() < 0.02) {
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
                        "/particle minecraft:squid_ink ~ ~ ~ 0.2 0.5 0.2 .1 20"
                     );
               }

               if (entity instanceof InkDemonEntity _datEntSetL) {
                  _datEntSetL.getEntityData().set(InkDemonEntity.DATA_TeleportBendy, true);
               }

               if (entity instanceof InkDemonEntity) {
                  ((InkDemonEntity)entity).setAnimation("teleport_out");
               }

               Vec3 _center = new Vec3(x, y, z);

               for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(50.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                  .toList()) {
                  if (entityiterator instanceof Player) {
                     Entity _entx = entityiterator;
                     if (!_entx.level().isClientSide() && _entx.getServer() != null) {
                        _entx.getServer()
                           .getCommands()
                           .performPrefixedCommand(
                              new CommandSourceStack(
                                 CommandSource.NULL,
                                 _entx.position(),
                                 _entx.getRotationVector(),
                                 _entx.level() instanceof ServerLevel ? (ServerLevel)_entx.level() : null,
                                 4,
                                 _entx.getName().getString(),
                                 _entx.getDisplayName(),
                                 _entx.level().getServer(),
                                 _entx
                              ),
                              "/execute at @p[gamemode=survival] rotated ~ 1 run spreadplayers ~ ~ 10 12 false @e[type=boh:ink_demon,limit=1]"
                           );
                     }
                  }
               }

               if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                  _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 254, false, false));
               }

               if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                  _entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 254, false, false));
               }

               BohMod.queueServerWork(
                  20,
                  () -> {
                     if (entity instanceof InkDemonEntity) {
                        ((InkDemonEntity)entity).setAnimation("teleport_in");
                     }

                     Entity _entxx = entity;
                     if (!_entxx.level().isClientSide() && _entxx.getServer() != null) {
                        _entxx.getServer()
                           .getCommands()
                           .performPrefixedCommand(
                              new CommandSourceStack(
                                 CommandSource.NULL,
                                 _entxx.position(),
                                 _entxx.getRotationVector(),
                                 _entxx.level() instanceof ServerLevel ? (ServerLevel)_entxx.level() : null,
                                 4,
                                 _entxx.getName().getString(),
                                 _entxx.getDisplayName(),
                                 _entxx.level().getServer(),
                                 _entxx
                              ),
                              "/particle minecraft:squid_ink ~ ~ ~ 0.5 1 0.5 .15 35"
                           );
                     }
                  }
               );
               BohMod.queueServerWork(
                  35,
                  () -> {
                     if (world instanceof Level _level) {
                        if (!_level.isClientSide()) {
                           _level.playSound(
                              null,
                              BlockPos.containing(x, y, z),
                              (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:bendy_scream")),
                              SoundSource.HOSTILE,
                              1.0F,
                              1.0F
                           );
                        } else {
                           _level.playLocalSound(
                              x,
                              y,
                              z,
                              (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:bendy_scream")),
                              SoundSource.HOSTILE,
                              1.0F,
                              1.0F,
                              false
                           );
                        }
                     }
                  }
               );
               BohMod.queueServerWork(40, () -> {
                  if (entity instanceof InkDemonEntity _datEntSetL) {
                     _datEntSetL.getEntityData().set(InkDemonEntity.DATA_TeleportBendy, false);
                  }
               });
            }
         }
      }
   }
}
