package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.mcreator.boh.compat.M;

public class WhitefaceThisEntityKillsAnotherOneProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            if (Math.random() < 0.7 && entity instanceof EntityPlayer) {
                Entity _ent = entity;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/tellraw @p {\"text\":\"click here..\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.youtube.com/watch?v=5dSslXouqrs\"}}");
                }
            }
        }
    }
}
