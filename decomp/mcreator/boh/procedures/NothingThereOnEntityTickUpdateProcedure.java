package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.network.BohModVariables;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class NothingThereOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         entity.getPersistentData().putDouble("radio_static", entity.getPersistentData().getDouble("radio_static") + 1.0);
         if (entity.getPersistentData().getDouble("radio_static") == 50.0) {
            if (world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:nothing_there_idle")),
                     SoundSource.AMBIENT,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:nothing_there_idle")),
                     SoundSource.AMBIENT,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }

            entity.getPersistentData().putDouble("radio_static", 0.0);
         }

         if (!entity.getPersistentData().getBoolean("lines_michael") && (entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player) {
            entity.getPersistentData().putBoolean("lines_michael", true);
         }

         if (!entity.getPersistentData().getBoolean("throlgular") && entity.getPersistentData().getBoolean("lines_michael")) {
            if (world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:nothing_there_aggro")),
                     SoundSource.HOSTILE,
                     2.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:nothing_there_aggro")),
                     SoundSource.HOSTILE,
                     2.0F,
                     1.0F,
                     false
                  );
               }
            }

            entity.getPersistentData().putBoolean("throlgular", true);
         }

         if (!((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player)) {
            entity.getPersistentData().putBoolean("lines_michael", false);
            entity.getPersistentData().putBoolean("throlgular", false);
         }

         if (entity instanceof Mob _mob && _mob.isAggressive()) {
            if (entity instanceof Mob _mobx && _mobx.isAggressive() && entity.getPersistentData().getDouble("timer_step") == 12.0) {
               if (!world.isClientSide() && world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:nothing_there_step")),
                        SoundSource.HOSTILE,
                        3.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:nothing_there_step")),
                        SoundSource.HOSTILE,
                        3.0F,
                        1.0F,
                        false
                     );
                  }
               }

               entity.getPersistentData().putDouble("timer_step", 0.0);
            }
         } else if (entity.getPersistentData().getDouble("timer_step") == 12.0) {
            if (!world.isClientSide() && world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:nothing_there_step")),
                     SoundSource.HOSTILE,
                     3.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:nothing_there_step")),
                     SoundSource.HOSTILE,
                     3.0F,
                     1.0F,
                     false
                  );
               }
            }

            entity.getPersistentData().putDouble("timer_step", 0.0);
         }

         if ((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player) {
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
               .toList()) {
               if (entityiterator instanceof Player) {
                  boolean _setval = true;
                  entityiterator.getCapability(BohModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                     capability.chase_nothingthere = _setval;
                     capability.syncPlayerVariables(entityiterator);
                  });
               }
            }
         }

         if (!((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player) && world instanceof ServerLevel _level) {
            _level.getServer()
               .getCommands()
               .performPrefixedCommand(
                  new CommandSourceStack(
                        CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null
                     )
                     .withSuppressedOutput(),
                  "/stopsound @a music boh:chase_nothing_there"
               );
         }

         if (entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
            entity.getPersistentData().putDouble("timer_step", entity.getPersistentData().getDouble("timer_step") + 1.0);
         } else {
            entity.getPersistentData().putDouble("timer_step", 0.0);
         }
      }
   }
}
