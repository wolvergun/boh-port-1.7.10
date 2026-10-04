package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.LittleSisterEntity;
import net.minecraft.world.World;

public class HasLSAroundProcedure {
    public static boolean execute(World world, double x, double y, double z) {
        return !M.isEmpty(M.getEntitiesOfClass(world, LittleSisterEntity.class, AABB.ofSize(new Vec3(x, y, z), 4.0, 4.0, 4.0), e -> true));
    }
}
