package net.mcreator.boh.procedures;

import net.mcreator.boh.network.BohModVariables;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.M;

public class LifeformOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z) {
        if (M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 100.0, 100.0, 100.0), e -> true))) {
            BohModVariables.MapVariables.get(world).spawn_lifeform = false;
            BohModVariables.MapVariables.get(world).syncData(world);
        }
    }
}
