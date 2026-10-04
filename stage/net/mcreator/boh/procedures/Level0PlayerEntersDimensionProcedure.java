package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class Level0PlayerEntersDimensionProcedure {

    public static void execute(World world, double x, double z, Entity entity) {
        if (entity != null) {
            BohMod.queueServerWork(5, () -> {
                Entity _ent = entity;
                M.teleportTo(_ent, x, 1.0,z);
                if (_ent instanceof EntityPlayerMP _serverPlayer) {
                    M.teleport(M.connection(_serverPlayer), x, 1.0,z, M.getYRot(_ent), M.getXRot(_ent));
                }
                entity.fallDistance = 0.0F;
            });
        }
    }
}
