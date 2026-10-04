package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.M;

public class FamineHorseOnEntityTickUpdateProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            if (M.isVehicle(entity) && M.getDeltaMovement(entity).x() != 0.0 && M.getDeltaMovement(entity).z() != 0.0) {
                M.setSprinting(entity, true);
            } else if (M.getDeltaMovement(entity).x() == 0.0 && M.getDeltaMovement(entity).z() == 0.0) {
                M.setSprinting(entity, false);
            }
            if (M.isSprinting(entity)) {
                Entity _ent = entity;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/effect give @e[distance=3..5] minecraft:slowness 30 3");
                }
                Entity _ent_r23 = entity;
                if (!M.isClientSide(M.level(_ent_r23)) && M.getServer(_ent_r23) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent_r23)), new CommandSourceStack(CommandSource.NULL, M.position(_ent_r23), M.getRotationVector(_ent_r23), M.level(_ent_r23) instanceof WorldServer ? (WorldServer) M.level(_ent_r23) : null, 4, M.getString(M.getName(_ent_r23)), M.getDisplayName(_ent_r23), M.getServer(M.level(_ent_r23)), _ent_r23), "/effect give @e[distance=3..5] minecraft:hunger 30 10");
                }
            }
            if (Math.random() < 0.7 && Math.random() < 0.7) {
                Entity _ent = entity;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/particle dust 0.4 0.4 0.4 0.2 ~ ~1 ~ .5 .5 .5 3 3");
                }
            }
        }
    }
}
