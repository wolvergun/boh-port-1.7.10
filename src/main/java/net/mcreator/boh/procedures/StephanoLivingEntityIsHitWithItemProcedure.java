package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.init.BohModParticleTypes;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class StephanoLivingEntityIsHitWithItemProcedure {
    public static void execute(World world, Entity entity) {
        if (entity != null && world instanceof WorldServer _level) {
            M.sendParticles(
                _level,
                BohModParticleTypes.GOLD_HIT.get(),
                M.getX(entity),
                M.getY(entity) + M.getBbHeight(entity) / 2.0F,
                M.getZ(entity),
                1,
                0.0,
                0.0,
                0.0,
                0.0
            );
        }
    }
}
