package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.SonicExeEntity;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.M;

public class TailsOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (M.isEmpty(M.getEntitiesOfClass(world, SonicExeEntity.class, AABB.ofSize(new Vec3(x, y, z), 32.0, 32.0, 32.0), e -> true)) && !M.isClientSide(M.level(entity))) {
                M.discard(entity);
            }
        }
    }
}
