package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.M;

public class HiltOfABlacksmithToolInInventoryTickProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            Entity _ent = entity;
            if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "effect give @s kurolib:aggression 3 0 false");
            }
            Entity _ent_r40 = entity;
            if (!M.isClientSide(M.level(_ent_r40)) && M.getServer(_ent_r40) != null) {
                M.performPrefixedCommand(M.getCommands(M.getServer(_ent_r40)), new CommandSourceStack(CommandSource.NULL, M.position(_ent_r40), M.getRotationVector(_ent_r40), M.level(_ent_r40) instanceof WorldServer ? (WorldServer) M.level(_ent_r40) : null, 4, M.getString(M.getName(_ent_r40)), M.getDisplayName(_ent_r40), M.getServer(M.level(_ent_r40)), _ent_r40), "effect give @s kurolib:paranoia 3 0 false");
            }
        }
    }
}
