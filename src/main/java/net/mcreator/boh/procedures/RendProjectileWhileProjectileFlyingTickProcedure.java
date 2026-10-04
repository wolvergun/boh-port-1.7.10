package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.init.BohModParticleTypes;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class RendProjectileWhileProjectileFlyingTickProcedure {
    public static void execute(World world, Entity immediatesourceentity) {
        if (immediatesourceentity != null) {
            BohMod.queueServerWork(20, () -> {
                if (!M.isClientSide(M.level(immediatesourceentity))) {
                    M.discard(immediatesourceentity);
                }
            });
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
                    "/particle boh:rend_sweep ~ ~ ~"
                );
            }

            if (world instanceof WorldServer _level) {
                M.sendParticles(
                    _level,
                    BohModParticleTypes.BLOOD_FALL.get(),
                    M.getX(immediatesourceentity) + Mth.nextInt(RandomSource.create(), -1, 1),
                    M.getY(immediatesourceentity),
                    M.getZ(immediatesourceentity) + Mth.nextInt(RandomSource.create(), -1, 1),
                    10,
                    0.0,
                    0.0,
                    0.0,
                    0.0
                );
            }
        }
    }
}
