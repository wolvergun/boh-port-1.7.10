package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class PhantomFreddyOnInitialEntitySpawnProcedure {
    public static void execute(World world, Entity entity) {
        if (entity != null) {
            BohMod.queueServerWork(200, () -> {
                if (!M.isClientSide(M.level(entity))) {
                    M.discard(entity);
                }
            });
        }
    }
}
