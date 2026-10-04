package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModParticleTypes;
import net.mcreator.boh.compat.mc.core.particles.SimpleParticleType;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class StephanoLivingEntityIsHitWithItemProcedure {

    public static void execute(World world, Entity entity) {
        if (entity != null) {
            if (world instanceof WorldServer _level) {
                M.sendParticles(_level, (SimpleParticleType) BohModParticleTypes.GOLD_HIT.get(), M.getX(entity), M.getY(entity) + M.getBbHeight(entity) / 2.0F, M.getZ(entity), 1, 0.0, 0.0, 0.0, 0.0);
            }
        }
    }
}
