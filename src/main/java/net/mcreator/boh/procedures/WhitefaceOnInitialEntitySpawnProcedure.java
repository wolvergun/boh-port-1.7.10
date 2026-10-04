package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class WhitefaceOnInitialEntitySpawnProcedure {
    public static void execute(World world, Entity entity) {
        if (entity != null) {
            BohMod.queueServerWork(
                5,
                () -> {
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
                            "/attribute @e[type=boh:whiteface,limit=1,sort=nearest] minecraft:generic.flying_speed base set 900000000000000000000000"
                        );
                    }
                }
            );
        }
    }
}
