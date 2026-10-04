package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.ChestbursterEntity;
import net.mcreator.boh.entity.FacehuggerEntity;
import net.mcreator.boh.entity.XenomorphEntity;
import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.mcreator.boh.compat.M;

public class XenomorphEntityIsHurtProcedure {

    public static void execute(World world, Entity entity) {
        if (entity != null) {
            if (Math.random() < 0.05) {
                M.setBlock(world, BlockPos.containing(M.getX(entity), M.getY(entity), M.getZ(entity)), M.defaultBlockState(((Block) BohModBlocks.XENOMORPH_BLOOD.get())), 3);
                if (entity instanceof XenomorphEntity) {
                    Entity _ent = entity;
                    if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                        M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/particle minecraft:block boh:xenomorph_blood ~ ~ ~ .2 1.5 .2 0 50");
                    }
                }
                if (entity instanceof FacehuggerEntity) {
                    Entity _ent = entity;
                    if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                        M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/particle minecraft:block boh:xenomorph_blood ~ ~ ~ .2 .2 .2 0 50");
                    }
                }
                if (entity instanceof ChestbursterEntity) {
                    Entity _ent = entity;
                    if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                        M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/particle minecraft:block boh:xenomorph_blood ~ ~ ~ .2 .4 .2 0 50");
                    }
                }
            }
        }
    }
}
