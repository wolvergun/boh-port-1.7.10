package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.SquidwardEntity;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class SquidwardRightClickedOnEntityProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (entity instanceof SquidwardEntity _datEntL0
            && (Boolean)_datEntL0.getEntityData().get(SquidwardEntity.DATA_variant)
            && !(entity instanceof SquidwardEntity _datEntL1 && (Boolean)_datEntL1.getEntityData().get(SquidwardEntity.DATA_start))) {
            if (entity instanceof SquidwardEntity _datEntSetL) {
               _datEntSetL.getEntityData().set(SquidwardEntity.DATA_start, true);
            }

            if (entity instanceof SquidwardEntity) {
               ((SquidwardEntity)entity).setAnimation("shoot");
            }

            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 900, 255, false, false));
            }

            BohMod.queueServerWork(
               48,
               () -> {
                  if (world instanceof Level _level) {
                     if (!_level.isClientSide()) {
                        _level.playSound(
                           null,
                           BlockPos.containing(x, y, z),
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:shotgun_cocking")),
                           SoundSource.HOSTILE,
                           1.0F,
                           1.0F
                        );
                     } else {
                        _level.playLocalSound(
                           x,
                           y,
                           z,
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:shotgun_cocking")),
                           SoundSource.HOSTILE,
                           1.0F,
                           1.0F,
                           false
                        );
                     }
                  }
               }
            );
            BohMod.queueServerWork(
               60,
               () -> {
                  if (world instanceof Level _level) {
                     if (!_level.isClientSide()) {
                        _level.playSound(
                           null,
                           BlockPos.containing(x, y, z),
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:shotgun_shoot")),
                           SoundSource.HOSTILE,
                           1.0F,
                           1.0F
                        );
                     } else {
                        _level.playLocalSound(
                           x,
                           y,
                           z,
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:shotgun_shoot")),
                           SoundSource.HOSTILE,
                           1.0F,
                           1.0F,
                           false
                        );
                     }
                  }

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
                           "/particle boh:blood_fall ~ ~1.5 ~ 0.1 0.1 0.1 .15 50"
                        );
                  }
               }
            );
            BohMod.queueServerWork(
               75, () -> entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC)), 900.0F)
            );
            BohMod.queueServerWork(
               80,
               () -> {
                  if (world instanceof ServerLevel _level) {
                     Entity entityToSpawn = ((EntityType)BohModEntities.SQUIDWARD_DOOMED.get())
                        .spawn(_level, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), MobSpawnType.MOB_SUMMONED);
                     if (entityToSpawn != null) {
                        entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                     }
                  }
               }
            );
         }
      }
   }
}
