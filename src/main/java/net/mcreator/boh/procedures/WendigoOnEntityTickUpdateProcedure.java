package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class WendigoOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null
            && M.canSeeSkyFromBelowWater(world, BlockPos.containing(x, y, z))
            && world instanceof World
            && M.isDay(world)
            && M.getRemainingFireTicks(entity) < 0
            && !M.isRaining(M.getLevelData(world))) {
            M.setSecondsOnFire(entity, 5);
        }
    }
}
