package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class DemonOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            M.setDeltaMovement(entity, new Vec3(M.getLookAngle(entity).x * 0.2, M.getLookAngle(entity).y * 0.9, M.getLookAngle(entity).z * 0.2));
            if (Math.random() < 0.5) {
                if (Math.random() < 0.025) {
                    M.setDeltaMovement(entity, new Vec3(0.0, 1.0, 0.0));
                } else if (Math.random() < 0.025) {
                    M.setDeltaMovement(entity, new Vec3(0.0, -1.0, 0.0));
                }
            }

            if (M.canSeeSkyFromBelowWater(world, BlockPos.containing(x, y, z))
                && world instanceof World
                && M.isDay(world)
                && M.getRemainingFireTicks(entity) < 0
                && !M.isRaining(M.getLevelData(world))) {
                M.setSecondsOnFire(entity, 5);
            }
        }
    }
}
