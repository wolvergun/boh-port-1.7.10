package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.ChestbursterEntity;
import net.mcreator.boh.entity.FacehuggerEntity;
import net.mcreator.boh.entity.XenomorphEntity;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;

public class XenomorphEntityIsHurtProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         if (Math.random() < 0.05) {
            world.setBlock(
               BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), ((Block)BohModBlocks.XENOMORPH_BLOOD.get()).defaultBlockState(), 3
            );
            if (entity instanceof XenomorphEntity) {
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
                        "/particle minecraft:block boh:xenomorph_blood ~ ~ ~ .2 1.5 .2 0 50"
                     );
               }
            }

            if (entity instanceof FacehuggerEntity) {
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
                        "/particle minecraft:block boh:xenomorph_blood ~ ~ ~ .2 .2 .2 0 50"
                     );
               }
            }

            if (entity instanceof ChestbursterEntity) {
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
                        "/particle minecraft:block boh:xenomorph_blood ~ ~ ~ .2 .4 .2 0 50"
                     );
               }
            }
         }
      }
   }
}
