package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.minecraft.entity.Entity;
import net.minecraft.world.WorldServer;

public class UnicornOnEntityTickUpdateProcedure {
    public static void execute(Entity entity) {
        if (entity != null) {
            if (M.isVehicle(entity) && M.getDeltaMovement(entity).x() != 0.0 && M.getDeltaMovement(entity).z() != 0.0) {
                M.setSprinting(entity, true);
            } else if (M.getDeltaMovement(entity).x() == 0.0 && M.getDeltaMovement(entity).z() == 0.0) {
                M.setSprinting(entity, false);
            }

            if (Math.random() < 0.7 && Math.random() < 0.7 && !M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
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
                    "/particle end_rod ~ ~1 ~ 0.5 0.5 0.5 0.02 1"
                );
            }

            if (M.isSprinting(entity) && !M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
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
                    "/damage @e[distance=3..5,limit=1] 1 minecraft:generic_kill by @s"
                );
            }
        }
    }
}
