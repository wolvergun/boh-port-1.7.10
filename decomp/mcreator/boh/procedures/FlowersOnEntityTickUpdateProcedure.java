package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.entity.FlowersEntity;
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

public class FlowersOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         Vec3 _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(50.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
            .toList()) {
            if (entityiterator instanceof Player
               && !(entityiterator instanceof LivingEntity _livEnt1 && _livEnt1.hasEffect((MobEffect)BohModMobEffects.THE_WHISLE.get()))
               && entityiterator instanceof LivingEntity _entity
               && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance((MobEffect)BohModMobEffects.THE_WHISLE.get(), 1000, 0, false, false));
            }
         }

         if (entity.tickCount % 300 == 0) {
            if (!world.isClientSide() && world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:flowers_whistle_damage")),
                     SoundSource.HOSTILE,
                     100.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:flowers_whistle_damage")),
                     SoundSource.HOSTILE,
                     100.0F,
                     1.0F,
                     false
                  );
               }
            }

            if (entity instanceof FlowersEntity _datEntSetL) {
               _datEntSetL.getEntityData().set(FlowersEntity.DATA_logic_whistle, true);
            }

            _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(50.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
               .toList()) {
               if (entityiterator instanceof Player
                  && !(entityiterator instanceof LivingEntity _livEnt9 && _livEnt9.hasEffect((MobEffect)BohModMobEffects.COVER_YOUR_EARS.get()))
                  && entityiterator instanceof LivingEntity _entity
                  && !_entity.level().isClientSide()) {
                  _entity.addEffect(new MobEffectInstance((MobEffect)BohModMobEffects.COVER_YOUR_EARS.get(), 180, 0, false, false));
               }
            }
         }

         if (entity instanceof FlowersEntity _datEntL12
            && (Boolean)_datEntL12.getEntityData().get(FlowersEntity.DATA_logic_whistle)
            && entity instanceof FlowersEntity _datEntSetI) {
            _datEntSetI.getEntityData()
               .set(
                  FlowersEntity.DATA_logic_cooldown_whisle,
                  (entity instanceof FlowersEntity _datEntI ? (Integer)_datEntI.getEntityData().get(FlowersEntity.DATA_logic_cooldown_whisle) : 0) + 1
               );
         }

         if ((entity instanceof FlowersEntity _datEntI ? (Integer)_datEntI.getEntityData().get(FlowersEntity.DATA_logic_cooldown_whisle) : 0) == 156) {
            if (entity instanceof FlowersEntity _datEntSetI) {
               _datEntSetI.getEntityData().set(FlowersEntity.DATA_logic_cooldown_whisle, 0);
            }

            if (entity instanceof FlowersEntity _datEntSetL) {
               _datEntSetL.getEntityData().set(FlowersEntity.DATA_logic_whistle, false);
            }

            Vec3 _centerx = new Vec3(x, y, z);

            for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_centerx, _centerx).inflate(50.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
               .toList()) {
               if (entityiterator instanceof Player
                  && entityiterator instanceof LivingEntity _livEnt19
                  && _livEnt19.hasEffect((MobEffect)BohModMobEffects.COVER_YOUR_EARS.get())
                  && (!(entityiterator.getXRot() > 75.0F) || !entityiterator.isShiftKeyDown())) {
                  Entity _ent = entityiterator;
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
                           "/damage @s 15 boh:whistle_damage"
                        );
                  }

                  _ent = entityiterator;
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
                           "/fill -3 78 -6 44 65 41 air destroy"
                        );
                  }
               }
            }
         }
      }
   }
}
