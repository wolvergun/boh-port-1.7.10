package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.SlenderManEntity;
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
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class SlenderOnTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         double player_count = 0.0;
         entity.getPersistentData().putDouble("ambinceslender", entity.getPersistentData().getDouble("ambinceslender") + 1.0);
         if (entity.getPersistentData().getDouble("ambinceslender") == 150.0) {
            if (Math.random() < 0.55) {
               if (world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:slender_ambient")),
                        SoundSource.HOSTILE,
                        1.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:slender_ambient")),
                        SoundSource.HOSTILE,
                        1.0F,
                        1.0F,
                        false
                     );
                  }
               }

               if (Math.random() < 0.3) {
                  Vec3 _center = new Vec3(x, y, z);

                  for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(50.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                     .toList()) {
                     if (entityiterator instanceof Player) {
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
                                 "execute as @s spreadplayers ~ ~ 10 10 false @e[type=boh:slender_man,limit=1,sort=nearest]"
                              );
                        }
                     }
                  }

                  if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                     _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
                  }

                  if (entity instanceof SlenderManEntity) {
                     ((SlenderManEntity)entity).setAnimation("teleport_out");
                  }

                  BohMod.queueServerWork(5, () -> {
                     if (entity instanceof SlenderManEntity) {
                        ((SlenderManEntity)entity).setAnimation("teleport_in");
                     }
                  });
               }
            }

            entity.getPersistentData().putDouble("ambinceslender", 0.0);
         }

         Vec3 _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(500.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
            .toList()) {
            if (entityiterator instanceof LivingEntity _livEnt12
               && _livEnt12.hasEffect((MobEffect)BohModMobEffects.ENGAGED.get())
               && entityiterator instanceof Player) {
               player_count++;
            }
         }

         _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(500.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
            .toList()) {
            if ((
                     entityiterator instanceof LivingEntity _livEnt && _livEnt.hasEffect((MobEffect)BohModMobEffects.ENGAGED.get())
                        ? _livEnt.getEffect((MobEffect)BohModMobEffects.ENGAGED.get()).getAmplifier()
                        : 0
                  )
                  < 3
               && entityiterator instanceof Player
               && entity instanceof LivingEntity _entity
               && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
            }
         }

         if (player_count == 0.0 && !entity.level().isClientSide()) {
            entity.discard();
         }
      }
   }
}
