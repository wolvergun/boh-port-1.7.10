package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.SadakoEntity;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class SadakoOnInitialEntitySpawnProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
            _entity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 19, 0, false, false));
         }

         if (world.getBlockState(BlockPos.containing(x + 1.0, y, z)).getBlock() == BohModBlocks.ANALOG_TV_SADAKO.get()) {
            Entity _ent = entity;
            _ent.teleportTo(x, y, z);
            if (_ent instanceof ServerPlayer _serverPlayer) {
               _serverPlayer.connection.teleport(x, y, z, _ent.getYRot(), _ent.getXRot());
            }

            entity.lookAt(Anchor.EYES, new Vec3(x - 1.0, y + 1.0, z));
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 254, false, false));
            }

            BohMod.queueServerWork(20, () -> {
               if (entity instanceof SadakoEntity) {
                  ((SadakoEntity)entity).setAnimation("crawl");
               }
            });
         } else if (world.getBlockState(BlockPos.containing(x - 1.0, y, z)).getBlock() == BohModBlocks.ANALOG_TV_SADAKO.get()) {
            Entity _ent = entity;
            _ent.teleportTo(x, y, z);
            if (_ent instanceof ServerPlayer _serverPlayer) {
               _serverPlayer.connection.teleport(x, y, z, _ent.getYRot(), _ent.getXRot());
            }

            entity.lookAt(Anchor.EYES, new Vec3(x + 1.0, y + 1.0, z));
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 254, false, false));
            }

            BohMod.queueServerWork(20, () -> {
               if (entity instanceof SadakoEntity) {
                  ((SadakoEntity)entity).setAnimation("crawl");
               }
            });
         } else if (world.getBlockState(BlockPos.containing(x, y, z + 1.0)).getBlock() == BohModBlocks.ANALOG_TV_SADAKO.get()) {
            Entity _ent = entity;
            _ent.teleportTo(x, y, z);
            if (_ent instanceof ServerPlayer _serverPlayer) {
               _serverPlayer.connection.teleport(x, y, z, _ent.getYRot(), _ent.getXRot());
            }

            entity.lookAt(Anchor.EYES, new Vec3(x, y + 1.0, z - 1.0));
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 254, false, false));
            }

            BohMod.queueServerWork(20, () -> {
               if (entity instanceof SadakoEntity) {
                  ((SadakoEntity)entity).setAnimation("crawl");
               }
            });
         } else if (world.getBlockState(BlockPos.containing(x, y, z - 1.0)).getBlock() == BohModBlocks.ANALOG_TV_SADAKO.get()) {
            Entity _ent = entity;
            _ent.teleportTo(x, y, z);
            if (_ent instanceof ServerPlayer _serverPlayer) {
               _serverPlayer.connection.teleport(x, y, z, _ent.getYRot(), _ent.getXRot());
            }

            entity.lookAt(Anchor.EYES, new Vec3(x, y + 1.0, z + 1.0));
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 254, false, false));
            }

            BohMod.queueServerWork(
               20,
               () -> {
                  if (entity instanceof SadakoEntity) {
                     ((SadakoEntity)entity).setAnimation("crawl");
                  }

                  if (world instanceof Level _level) {
                     if (!_level.isClientSide()) {
                        _level.playSound(
                           null,
                           BlockPos.containing(x, y, z),
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sadako_tv")),
                           SoundSource.HOSTILE,
                           1.0F,
                           1.0F
                        );
                     } else {
                        _level.playLocalSound(
                           x,
                           y,
                           z,
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sadako_tv")),
                           SoundSource.HOSTILE,
                           1.0F,
                           1.0F,
                           false
                        );
                     }
                  }

                  if (world instanceof Level _level) {
                     if (!_level.isClientSide()) {
                        _level.playSound(
                           null,
                           BlockPos.containing(x, y, z),
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sadako_idle")),
                           SoundSource.HOSTILE,
                           1.0F,
                           1.0F
                        );
                     } else {
                        _level.playLocalSound(
                           x,
                           y,
                           z,
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sadako_idle")),
                           SoundSource.HOSTILE,
                           1.0F,
                           1.0F,
                           false
                        );
                     }
                  }
               }
            );
         }
      }
   }
}
