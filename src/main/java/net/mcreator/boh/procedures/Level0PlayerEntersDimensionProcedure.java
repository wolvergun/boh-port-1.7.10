package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class Level0PlayerEntersDimensionProcedure {
    public static void execute(World world, double x, double z, Entity entity) {
        if (entity != null) {
            BohMod.queueServerWork(5, () -> {
                M.teleportTo(entity, x, 1.0, z);
                if (entity instanceof EntityPlayerMP _serverPlayer) {
                    M.teleport(M.connection(_serverPlayer), x, 1.0, z, M.getYRot(entity), M.getXRot(entity));
                }

                entity.fallDistance = 0.0F;
            });
        }
    }
}
