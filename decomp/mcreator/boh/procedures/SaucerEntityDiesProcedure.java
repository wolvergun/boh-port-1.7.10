package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.network.BohModVariables;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level.ExplosionInteraction;

public class SaucerEntityDiesProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (world instanceof Level _level && !_level.isClientSide()) {
            _level.explode(
               null,
               x + Mth.nextInt(RandomSource.create(), -20, 20),
               y,
               z + Mth.nextInt(RandomSource.create(), -20, 20),
               6.0F,
               ExplosionInteraction.NONE
            );
         }

         if (world instanceof Level _level && !_level.isClientSide()) {
            _level.explode(
               null,
               x + Mth.nextInt(RandomSource.create(), -20, 20),
               y,
               z + Mth.nextInt(RandomSource.create(), -20, 20),
               6.0F,
               ExplosionInteraction.NONE
            );
         }

         if (world instanceof Level _level && !_level.isClientSide()) {
            _level.explode(
               null,
               x + Mth.nextInt(RandomSource.create(), -20, 20),
               y,
               z + Mth.nextInt(RandomSource.create(), -20, 20),
               6.0F,
               ExplosionInteraction.NONE
            );
         }

         if (world instanceof Level _level && !_level.isClientSide()) {
            _level.explode(
               null,
               x + Mth.nextInt(RandomSource.create(), -20, 20),
               y,
               z + Mth.nextInt(RandomSource.create(), -20, 20),
               6.0F,
               ExplosionInteraction.NONE
            );
         }

         BohMod.queueServerWork(
            10,
            () -> {
               if (world instanceof Level _level && !_level.isClientSide()) {
                  _level.explode(
                     null,
                     x + Mth.nextInt(RandomSource.create(), -20, 20),
                     y,
                     z + Mth.nextInt(RandomSource.create(), -20, 20),
                     6.0F,
                     ExplosionInteraction.NONE
                  );
               }

               if (world instanceof Level _level && !_level.isClientSide()) {
                  _level.explode(
                     null,
                     x + Mth.nextInt(RandomSource.create(), -20, 20),
                     y,
                     z + Mth.nextInt(RandomSource.create(), -20, 20),
                     6.0F,
                     ExplosionInteraction.NONE
                  );
               }

               if (world instanceof Level _level && !_level.isClientSide()) {
                  _level.explode(
                     null,
                     x + Mth.nextInt(RandomSource.create(), -20, 20),
                     y,
                     z + Mth.nextInt(RandomSource.create(), -20, 20),
                     6.0F,
                     ExplosionInteraction.NONE
                  );
               }

               if (world instanceof Level _level && !_level.isClientSide()) {
                  _level.explode(
                     null,
                     x + Mth.nextInt(RandomSource.create(), -20, 20),
                     y,
                     z + Mth.nextInt(RandomSource.create(), -20, 20),
                     6.0F,
                     ExplosionInteraction.NONE
                  );
               }

               BohMod.queueServerWork(
                  10,
                  () -> {
                     if (world instanceof Level _levelxxxx && !_levelxxxx.isClientSide()) {
                        _levelxxxx.explode(
                           null,
                           x + Mth.nextInt(RandomSource.create(), -20, 20),
                           y,
                           z + Mth.nextInt(RandomSource.create(), -20, 20),
                           6.0F,
                           ExplosionInteraction.NONE
                        );
                     }

                     if (world instanceof Level _levelxxx && !_levelxxx.isClientSide()) {
                        _levelxxx.explode(
                           null,
                           x + Mth.nextInt(RandomSource.create(), -20, 20),
                           y,
                           z + Mth.nextInt(RandomSource.create(), -20, 20),
                           6.0F,
                           ExplosionInteraction.NONE
                        );
                     }

                     if (world instanceof Level _levelxx && !_levelxx.isClientSide()) {
                        _levelxx.explode(
                           null,
                           x + Mth.nextInt(RandomSource.create(), -20, 20),
                           y,
                           z + Mth.nextInt(RandomSource.create(), -20, 20),
                           6.0F,
                           ExplosionInteraction.NONE
                        );
                     }

                     if (world instanceof Level _levelx && !_levelx.isClientSide()) {
                        _levelx.explode(
                           null,
                           x + Mth.nextInt(RandomSource.create(), -20, 20),
                           y,
                           z + Mth.nextInt(RandomSource.create(), -20, 20),
                           6.0F,
                           ExplosionInteraction.NONE
                        );
                     }
                  }
               );
            }
         );
         if (Math.random() < 0.33 && world instanceof ServerLevel _level) {
            Entity entityToSpawn = EntityType.COW.spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
            if (entityToSpawn != null) {
               entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
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
                  "/stopsound @a music boh:gray_ost"
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
                  "/stopsound @a master boh:mother_ship_idle"
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
                  "/stopsound @a master boh:mother_ship_loop"
               );
         }

         BohModVariables.MapVariables.get(world).spawn_saucer = 0.0;
         BohModVariables.MapVariables.get(world).syncData(world);
      }
   }
}
