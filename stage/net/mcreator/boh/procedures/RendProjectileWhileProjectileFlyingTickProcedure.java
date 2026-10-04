package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.init.BohModParticleTypes;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.particles.SimpleParticleType;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class RendProjectileWhileProjectileFlyingTickProcedure {

    public static void execute(World world, Entity immediatesourceentity) {
        if (immediatesourceentity != null) {
            BohMod.queueServerWork(20, () -> {
                if (!M.isClientSide(M.level(immediatesourceentity))) {
                    M.discard(immediatesourceentity);
                }
            });
            Entity _ent = immediatesourceentity;
            if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/particle boh:rend_sweep ~ ~ ~");
            }
            if (world instanceof WorldServer _level) {
                M.sendParticles(_level, (SimpleParticleType) BohModParticleTypes.BLOOD_FALL.get(), M.getX(immediatesourceentity) + Mth.nextInt(RandomSource.create(), -1, 1), M.getY(immediatesourceentity), M.getZ(immediatesourceentity) + Mth.nextInt(RandomSource.create(), -1, 1), 10, 0.0, 0.0, 0.0, 0.0);
            }
        }
    }
}
