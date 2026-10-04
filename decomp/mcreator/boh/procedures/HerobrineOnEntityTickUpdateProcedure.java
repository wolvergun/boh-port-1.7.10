package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.HerobrineEntity;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public class HerobrineOnEntityTickUpdateProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (entity instanceof HerobrineEntity _datEntSetI) {
            _datEntSetI.getEntityData()
               .set(
                  HerobrineEntity.DATA_timer,
                  (entity instanceof HerobrineEntity _datEntI ? (Integer)_datEntI.getEntityData().get(HerobrineEntity.DATA_timer) : 0) + 1
               );
         }

         if ((entity instanceof HerobrineEntity _datEntI ? (Integer)_datEntI.getEntityData().get(HerobrineEntity.DATA_timer) : 0) == 3) {
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
                     "/execute at @e[type=boh:herobrine] run playsound minecraft:ambient.cave hostile @a"
                  );
            }

            if (Math.random() < 0.7) {
               if (!entity.level().isClientSide()) {
                  entity.discard();
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
                        "/execute at @e[type=boh:herobrine] run playsound minecraft:entity.ghast.hurt hostile @a"
                     );
               }
            }
         }

         if ((entity instanceof HerobrineEntity _datEntI ? (Integer)_datEntI.getEntityData().get(HerobrineEntity.DATA_timer) : 0) == 20) {
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
                     "/spreadplayers ~ ~ 5 5 false @e[type=boh:herobrine,limit=1,distance=0..2]"
                  );
            }
         }

         if ((entity instanceof HerobrineEntity _datEntI ? (Integer)_datEntI.getEntityData().get(HerobrineEntity.DATA_timer) : 0) == 60) {
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
                     "/execute at @e[type=boh:herobrine] run particle minecraft:large_smoke ~ ~1 ~ 0.2 0.5 0.2 0 100 force"
                  );
            }

            if (!entity.level().isClientSide()) {
               entity.discard();
            }
         }
      }
   }
}
