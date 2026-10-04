package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.minecraft.entity.Entity;
import net.minecraft.world.WorldServer;

public class FamineHorseOnEntityTickUpdateProcedure {
    public static void execute(Entity entity) {
        if (entity != null) {
            if (M.isVehicle(entity) && M.getDeltaMovement(entity).x() != 0.0 && M.getDeltaMovement(entity).z() != 0.0) {
                M.setSprinting(entity, true);
            } else if (M.getDeltaMovement(entity).x() == 0.0 && M.getDeltaMovement(entity).z() == 0.0) {
                M.setSprinting(entity, false);
            }

            if (M.isSprinting(entity)) {
                if (!M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
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
                        "/effect give @e[distance=3..5] minecraft:slowness 30 3"
                    );
                }

                if (!M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
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
                        "/effect give @e[distance=3..5] minecraft:hunger 30 10"
                    );
                }
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
                    "/particle dust 0.4 0.4 0.4 0.2 ~ ~1 ~ .5 .5 .5 3 3"
                );
            }
        }
    }
}
