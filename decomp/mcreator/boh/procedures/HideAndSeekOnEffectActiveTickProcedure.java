package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class HideAndSeekOnEffectActiveTickProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (Math.random() < 0.25) {
            if (Math.random() < 0.02) {
               entity.getPersistentData().putDouble("exe_static", 1.0);
               if (world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:exe_static")),
                        SoundSource.AMBIENT,
                        1.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:exe_static")),
                        SoundSource.AMBIENT,
                        1.0F,
                        1.0F,
                        false
                     );
                  }
               }
            } else if (Math.random() < 0.02) {
               entity.getPersistentData().putDouble("exe_static", 2.0);
            } else if (Math.random() < 0.02) {
               entity.getPersistentData().putDouble("exe_static", 3.0);
            } else if (Math.random() < 0.02) {
               entity.getPersistentData().putDouble("exe_static", 4.0);
            } else if (Math.random() < 0.02) {
               entity.getPersistentData().putDouble("exe_static", 5.0);
            } else {
               entity.getPersistentData().putDouble("exe_static", 0.0);
            }
         } else {
            entity.getPersistentData().putDouble("exe_apparison", 0.0);
         }

         if (Math.random() < 0.5) {
            if (Math.random() < 0.024) {
               entity.getPersistentData().putDouble("exe_apparison", 1.0);
            } else if (Math.random() < 0.024) {
               entity.getPersistentData().putDouble("exe_apparison", 2.0);
            } else if (Math.random() < 0.024) {
               entity.getPersistentData().putDouble("exe_apparison", 3.0);
            } else if (Math.random() < 0.024) {
               entity.getPersistentData().putDouble("exe_apparison", 4.0);
            } else {
               entity.getPersistentData().putDouble("exe_apparison", 0.0);
            }
         } else {
            entity.getPersistentData().putDouble("exe_apparison", 0.0);
         }

         if (entity.getPersistentData().getDouble("monitor_spawn") == 0.0 && Math.random() < 0.0015) {
            if (world instanceof ServerLevel _level) {
               Entity entityToSpawn = ((EntityType)BohModEntities.EXE_MONITOR.get()).spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
               if (entityToSpawn != null) {
                  entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
               }
            }

            entity.getPersistentData().putDouble("monitor_spawn", 1.0);
         }
      }
   }
}
