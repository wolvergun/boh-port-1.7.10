package net.mcreator.boh.procedures;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public class DeathHorseOnEntityTickUpdateProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (entity.isVehicle() && entity.getDeltaMovement().x() != 0.0 && entity.getDeltaMovement().z() != 0.0) {
            entity.setSprinting(true);
         } else if (entity.getDeltaMovement().x() == 0.0 && entity.getDeltaMovement().z() == 0.0) {
            entity.setSprinting(false);
         }

         if (entity.isSprinting()) {
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
                     "/effect give @e[distance=3..5] minecraft:wither 30 0"
                  );
            }
         }

         if (Math.random() < 0.7 && Math.random() < 0.7) {
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
                     "/particle minecraft:campfire_cosy_smoke ~ ~1 ~ 0.5 0.5 0.5 0.01 1 force"
                  );
            }
         }
      }
   }
}
