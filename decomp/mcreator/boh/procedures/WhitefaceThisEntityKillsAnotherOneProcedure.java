package net.mcreator.boh.procedures;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class WhitefaceThisEntityKillsAnotherOneProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (Math.random() < 0.7 && entity instanceof Player) {
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
                     "/tellraw @p {\"text\":\"click here..\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.youtube.com/watch?v=5dSslXouqrs\"}}"
                  );
            }
         }
      }
   }
}
