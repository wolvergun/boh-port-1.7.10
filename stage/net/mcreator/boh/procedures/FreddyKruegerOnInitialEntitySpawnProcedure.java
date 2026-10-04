package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.entity.FreddyKruegerEntity;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.M;

public class FreddyKruegerOnInitialEntitySpawnProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (M.dimension(M.level(entity)) == net.mcreator.boh.compat.world.Dimensions.dimensionKey( new ResourceLocation("boh:boiler_room_dimension"))) {
                Vec3 _center = new Vec3(x, y, z);
                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(500.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                    if (entityiterator instanceof FreddyKruegerEntity && !M.isClientSide(M.level(entityiterator))) {
                        M.discard(entityiterator);
                    }
                }
            }
        }
    }
}
