package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.mc.core.particles.ParticleTypes;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class IntoTheFogOnEffectActiveTickProcedure {

    public static void execute(World world, Entity entity) {
        if (entity != null) {
            if (M.isClientSide(world)) {
                M.addParticle(world, ParticleTypes.ASH, M.getX(entity) + Mth.nextDouble(RandomSource.create(), -20.0, 20.0), M.getY(entity) + 5.0, M.getZ(entity) + Mth.nextDouble(RandomSource.create(), -20.0, 20.0), 20.0, 20.0, 20.0);
                M.addParticle(world, ParticleTypes.ASH, M.getX(entity) + Mth.nextDouble(RandomSource.create(), -20.0, 20.0), M.getY(entity) + 5.0, M.getZ(entity) + Mth.nextDouble(RandomSource.create(), -20.0, 20.0), 20.0, 20.0, 20.0);
                M.addParticle(world, ParticleTypes.ASH, M.getX(entity) + Mth.nextDouble(RandomSource.create(), -20.0, 20.0), M.getY(entity) + 5.0, M.getZ(entity) + Mth.nextDouble(RandomSource.create(), -20.0, 20.0), 20.0, 20.0, 20.0);
                M.addParticle(world, ParticleTypes.ASH, M.getX(entity) + Mth.nextDouble(RandomSource.create(), -20.0, 20.0), M.getY(entity) + 5.0, M.getZ(entity) + Mth.nextDouble(RandomSource.create(), -20.0, 20.0), 20.0, 20.0, 20.0);
                M.addParticle(world, ParticleTypes.ASH, M.getX(entity) + Mth.nextDouble(RandomSource.create(), -20.0, 20.0), M.getY(entity) + 5.0, M.getZ(entity) + Mth.nextDouble(RandomSource.create(), -20.0, 20.0), 20.0, 20.0, 20.0);
                M.addParticle(world, ParticleTypes.ASH, M.getX(entity) + Mth.nextDouble(RandomSource.create(), -20.0, 20.0), M.getY(entity) + 5.0, M.getZ(entity) + Mth.nextDouble(RandomSource.create(), -20.0, 20.0), 20.0, 20.0, 20.0);
            }
        }
    }
}
