package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.network.BohModVariables;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class MichaelDaviesEntityDiesProcedure {
    public static void execute(World world, Entity entity) {
        if (entity != null) {
            BohModVariables.MapVariables.get(world).spawn_michael = 0.0;
            BohModVariables.MapVariables.get(world).syncData(world);
            BohMod.queueServerWork(
                24,
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
                            "/particle minecraft:block redstone_block ~ ~ ~ .6 .6 .6 3 300"
                        );
                    }
                }
            );
        }
    }
}
