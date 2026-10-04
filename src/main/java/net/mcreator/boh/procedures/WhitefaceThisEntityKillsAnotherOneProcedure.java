package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;

public class WhitefaceThisEntityKillsAnotherOneProcedure {
    public static void execute(Entity entity) {
        if (entity != null && Math.random() < 0.7 && entity instanceof EntityPlayer && !M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
            M.performPrefixedCommand(
                M.getCommands(M.getServer(entity)),
                new CommandSourceStack(
                    CommandSource.NULL,
                    M.position(entity),
                    M.getRotationVector(entity),
                    M.level(entity) instanceof WorldServer ? (WorldServer)M.level(entity) : null,
                    4,
                    M.getString(M.getName(entity)),
                    M.getDisplayName(entity),
                    M.getServer(M.level(entity)),
                    entity
                ),
                "/tellraw @p {\"text\":\"click here..\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.youtube.com/watch?v=5dSslXouqrs\"}}"
            );
        }
    }
}
