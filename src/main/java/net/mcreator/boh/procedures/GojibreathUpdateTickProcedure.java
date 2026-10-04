package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.particles.ParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class GojibreathUpdateTickProcedure {
    public static void execute(World world, double x, double y, double z) {
        if (Math.random() < 0.01 && world instanceof WorldServer _level) {
            M.sendParticles(_level, ParticleTypes.LARGE_SMOKE, x + 0.5, y, z + 0.5, 1, 0.1, 0.1, 0.1, 0.0);
        }
    }
}
