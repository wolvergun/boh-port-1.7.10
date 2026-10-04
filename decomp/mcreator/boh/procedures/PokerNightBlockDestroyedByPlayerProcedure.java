package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModEntities;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class PokerNightBlockDestroyedByPlayerProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (world instanceof ServerLevel _level) {
         _level.getServer()
            .getCommands()
            .performPrefixedCommand(
               new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null)
                  .withSuppressedOutput(),
               "/stopsound @a block boh:pasta_night"
            );
      }

      if (Math.random() < 0.5) {
         if (world instanceof ServerLevel _level) {
            Entity entityToSpawn = ((EntityType)BohModEntities.HYPNO.get()).spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
            if (entityToSpawn != null) {
            }
         }
      } else if (Math.random() < 0.5) {
         if (world instanceof ServerLevel _level) {
            Entity entityToSpawn = ((EntityType)BohModEntities.MX.get()).spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
            if (entityToSpawn != null) {
            }
         }
      } else if (world instanceof ServerLevel _level) {
         Entity entityToSpawn = ((EntityType)BohModEntities.SONIC_EXE.get()).spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
         if (entityToSpawn != null) {
         }
      }
   }
}
