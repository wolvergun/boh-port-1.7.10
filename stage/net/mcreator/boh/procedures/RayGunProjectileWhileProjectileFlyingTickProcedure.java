package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class RayGunProjectileWhileProjectileFlyingTickProcedure {

    public static void execute(World world, Entity immediatesourceentity) {
        if (immediatesourceentity != null) {
            M.setNoGravity(immediatesourceentity, true);
            Entity _ent = immediatesourceentity;
            if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/particle boh:gray_particle ~ ~ ~ 0 0 0 0 1");
            }
            BohMod.queueServerWork(40, () -> {
                if (!M.isClientSide(M.level(immediatesourceentity))) {
                    M.discard(immediatesourceentity);
                }
            });
        }
    }
}
