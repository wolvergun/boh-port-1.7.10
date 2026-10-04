package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.entity.ChestbursterEntity;
import net.mcreator.boh.entity.FacehuggerEntity;
import net.mcreator.boh.entity.XenomorphEntity;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class XenomorphEntityIsHurtProcedure {
    public static void execute(World world, Entity entity) {
        if (entity != null && Math.random() < 0.05) {
            M.setBlock(world, BlockPos.containing(M.getX(entity), M.getY(entity), M.getZ(entity)), M.defaultBlockState(BohModBlocks.XENOMORPH_BLOOD.get()), 3);
            if (entity instanceof XenomorphEntity && !M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
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
                    "/particle minecraft:block boh:xenomorph_blood ~ ~ ~ .2 1.5 .2 0 50"
                );
            }

            if (entity instanceof FacehuggerEntity && !M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
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
                    "/particle minecraft:block boh:xenomorph_blood ~ ~ ~ .2 .2 .2 0 50"
                );
            }

            if (entity instanceof ChestbursterEntity && !M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
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
                    "/particle minecraft:block boh:xenomorph_blood ~ ~ ~ .2 .4 .2 0 50"
                );
            }
        }
    }
}
