package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class RayGunProjectileWhileProjectileFlyingTickProcedure {
    public static void execute(World world, Entity immediatesourceentity) {
        if (immediatesourceentity != null) {
            M.setNoGravity(immediatesourceentity, true);
            if (!M.isClientSide(M.level(immediatesourceentity)) && M.getServer(immediatesourceentity) != null) {
                M.performPrefixedCommand(
                    M.getCommands(M.getServer(immediatesourceentity)),
                    new CommandSourceStack(
                        CommandSource.NULL,
                        M.position(immediatesourceentity),
                        M.getRotationVector(immediatesourceentity),
                        M.level(immediatesourceentity) instanceof WorldServer ? (WorldServer)M.level(immediatesourceentity) : null,
                        4,
                        M.getString(M.getName(immediatesourceentity)),
                        M.getDisplayName(immediatesourceentity),
                        M.getServer(M.level(immediatesourceentity)),
                        immediatesourceentity
                    ),
                    "/particle boh:gray_particle ~ ~ ~ 0 0 0 0 1"
                );
            }

            BohMod.queueServerWork(40, () -> {
                if (!M.isClientSide(M.level(immediatesourceentity))) {
                    M.discard(immediatesourceentity);
                }
            });
        }
    }
}
