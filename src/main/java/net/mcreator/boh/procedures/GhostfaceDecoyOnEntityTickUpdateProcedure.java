package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.GhostfaceEntity;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class GhostfaceDecoyOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null
            && (
                M.isEmpty(M.getEntitiesOfClass(world, GhostfaceEntity.class, AABB.ofSize(new Vec3(x, y, z), 20.0, 20.0, 20.0), e -> true))
                    || M.isEmpty(M.getEntitiesOfClass(world, GhostfaceEntity.class, AABB.ofSize(new Vec3(x, y, z), 100.0, 100.0, 100.0), e -> true))
            )
            && !M.isClientSide(M.level(entity))) {
            M.discard(entity);
        }
    }
}
